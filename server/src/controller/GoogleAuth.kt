package com.ktor.assignment.controller

import com.google.gson.Gson
import com.ktor.assignment.AuthSession
import com.ktor.assignment.httpClient
import com.ktor.assignment.models.Address
import com.ktor.assignment.models.Addresses
import com.ktor.assignment.models.User
import com.ktor.assignment.models.Users
import com.ktor.assignment.utils.JWTConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.client.request.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception



fun Application.profileModule(){



    routing {
        authenticate {
            route("/test") {
                handle{
                    call.respondText("hi +${call.principal<JWTPrincipal>()?.payload?.getClaim("name")?.asString()}")
                }

            }
        }
        //authenticate("GoogleAuth") {
            route("/login") {
                handle{

                    val principal = call.receive<LoginRequest>()//call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                    if (principal!=null) {
                        val json = httpClient.get<String>("https://www.googleapis.com/userinfo/v2/me") {
                            header("Authorization", "Bearer ${principal.accessToken}")
                        }

                        val user=Gson().fromJson(json, UserResponse::class.java)

                        if (user.id != null) {
                            val dbUser=transaction {
                                User.find(Users.email eq user.email).let {
                                    if (it.empty())
                                        User.new{
                                            name=user.name
                                            email=user.email
                                            username=user.email
                                            accessToken=principal.accessToken
                                            profilePicture=user.picture
                                        }
                                    else it.iterator().next()
                                }
                            }

                            call.respond(LoginRequest(JWTConfig.generateToken(dbUser)))
//                            call.sessions.set<AuthSession>(AuthSession(json))
//                            call.respondRedirect("/profile")
                            return@handle



                        }
                        call.respondRedirect("/")
                    }
                }
            }
       // }

        route ("/logout") {
            handle {
                call.sessions.clear<AuthSession>()
                call.respondRedirect("/")
            }
        }

        authenticate {
            route("/user"){
                get {
                    var email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    val user=transaction {
                        User.find(Users.email eq email).let {
                        if (!it.empty()) {
                            it.iterator().next().let {
                                UserResponse(it.id.toString(), it.email, it.name, it.profilePicture)
                            }
                        }
                        else {
                            null
                        }
                    }

                    }
                    if (user != null) {
                        call.respond(user)
                    }
                }
            }
            route("/profile" ) {
                get {
                    var email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    var dbUser: User?=null
                    var profileLock=false
                    var addresses:List<Address>?=null
                    transaction{
                        User.find(Users.email eq email).let{
                            if(!it.empty()){
                                dbUser=it.iterator().next()
                                addresses= Address.find(Addresses.user eq dbUser!!.id).iterator().asSequence().toList()
                                profileLock=dbUser!!.profileLock
                            }
                        }
                    }
                    call.respond(FreeMarkerContent("profile.ftl",mapOf("user" to dbUser,"locked" to profileLock,"Addresses" to addresses),""))
                }
                post {

                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    val params=call.receive<ProfileRequest>()

                    transaction{
                        User.find(Users.email eq email).let{
                            if(!it.empty()){
                                it.iterator().next().apply {
                                    if (params.mobileNumber.matches(Regex("^[7-9][0-9]{9}$")))
                                        this.mobileNumber=params.mobileNumber

                                    if (!this.profileLock && params.username!="")
                                    {
                                            User.find(Users.username eq params.username).let {userList->
                                            if (userList.empty()){
                                                this.username=params.username
                                                this.profileLock=true
                                            }

                                        }

                                    }
                                }

                            }
                        }


                    }
                    call.respond (mapOf("success" to true,"message" to "Operation Successful"))





                }
            }
            route("/address") {
                post {
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    var dbUser: User? = null
                    val params = call.receive<AddressDto>()
                    transaction {
                        val dbUserList = User.find(Users.email eq email)
                        if (!dbUserList.empty()) {
                            dbUser = dbUserList.iterator().next()
                        }
                        Address.new {
                            title = params.title
                            line1 = params.line1
                            line2 = params.line2
                            locality = params.locality
                            city = params.city
                            state = params.state
                            pincode = params.pincode.toInt()
                            active = true
                            this.user = dbUser!!

                        }
                    }
                    call.respond(mapOf("success" to true, "message" to "Operation Successful"))
                }

                delete("/{id}") {
                    val id = call.parameters["id"]
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()

                    var dbUser: User? = null
                    transaction {
                        val dbUserList = User.find(Users.email eq email)
                        if (!dbUserList.empty()) {
                            dbUser = dbUserList.iterator().next()
                        }
                        val addressList = Address.find((Addresses.id eq id!!.toLong()) and (Addresses.user eq dbUser!!.id)).iterator()
                        if (addressList.hasNext()) {
                            addressList.next().delete()
                        }
                    }

                    call.respond(mapOf("success" to true, "message" to "Operation Successful"))


                }

            }
        }




    }

}





data class UserResponse(val id:String,val email:String,val name:String,val picture:String)

data class ProfileRequest(val username: String, val mobileNumber : String)
data class LoginRequest (val accessToken:String)
data class AddressDto(var title: String,
                      var line1: String,
                      var line2 : String,
                      var locality : String,
                      var pincode: String,
                      var city : String,
                      var state : String,
                      var active : String
)