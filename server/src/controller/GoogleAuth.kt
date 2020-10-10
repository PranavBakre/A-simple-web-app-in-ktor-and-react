package com.ktor.assignment.controller

import com.auth0.jwt.interfaces.Payload
import com.google.gson.Gson
import com.ktor.assignment.AuthSession
import com.ktor.assignment.httpClient
import com.ktor.assignment.models.*
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
                            return@handle



                        }
                        call.respondRedirect("/")
                    }
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

                    var profileLock=false
                    var addresses:List<Address>?=null
                    var user=transaction{
                        User.find(Users.email eq email).let{
                            if(!it.empty()){
                                it.iterator().next()
                                //addresses= Address.find(Addresses.user eq dbUser!!.id).iterator().asSequence().toList()
                                //profileLock=dbUser!!.profileLock
                            } else
                                null
                        }
                    }
                    if (user!=null) {
                        call.respond(UserDto(user.name, user.email, user.username, user.mobileNumber, user.profilePicture, user.profileLock))
                    }
                    //call.respond(FreeMarkerContent("profile.ftl",mapOf("user" to dbUser,"locked" to profileLock,"Addresses" to addresses),""))
                }
                post {

                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    val params=call.receive<ProfileRequest>()

                    var user = transaction{
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
                                    return@transaction UserDto(this.name, this.email, this.username, this.mobileNumber, this.profilePicture, this.profileLock)
                                }

                            }
                        }
                        return@transaction null


                    }
                    if (user!=null) {
                        call.respond(user)
                    }




                }
            }
            route("/address") {
                get {
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    val addresses=transaction {
                        User.find(Users.email eq email) .let {
                            if(!it.empty())
                            {
                                Address.find(Addresses.user eq it.iterator().next().id).let {it2 ->
                                    if(!it2.empty()){
                                        var addressList=ArrayList<AddressDto>()
                                        val iterator=it2.iterator();
                                        while(iterator.hasNext()){
                                            val addr=iterator.next()
                                            addressList.add(
                                                    AddressDto(
                                                            addr.id.value,
                                                            addr.title,
                                                            addr.line1,
                                                            addr.line2,
                                                            addr.locality,
                                                            addr.pincode,
                                                            addr.city,
                                                            addr.state,
                                                            addr.active))
                                        }
                                        return@transaction addressList
                                    }
                                    else null

                                }
                            }
                            else null
                        }
                    }
                    if (addresses!=null) {
                        call.respond(addresses)
                    } else {
                        call.respond("[]")
                    }
                }
                post {
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    var dbUser: User? = null
                    val params = call.receive<AddressDto>()
                    var addresses=transaction {
                        User.find(Users.email eq email).let {
                            if (!it.empty()) {
                                Address.new {
                                    title = params.title
                                    line1 = params.line1
                                    line2 = params.line2
                                    locality = params.locality
                                    city = params.city
                                    state = params.state
                                    pincode = params.pincode.toInt()
                                    active = true
                                    this.user = it.iterator().next()

                                }
                                Address.find(Addresses.user eq it.iterator().next().id).let{ it2->
                                    var addressList=ArrayList<AddressDto>()
                                    val iterator=it2.iterator();
                                    while(iterator.hasNext()){
                                        val addr=iterator.next()
                                        addressList.add(
                                                AddressDto(
                                                        addr.id.value,
                                                        addr.title,
                                                        addr.line1,
                                                        addr.line2,
                                                        addr.locality,
                                                        addr.pincode,
                                                        addr.city,
                                                        addr.state,
                                                        addr.active))
                                    }
                                    addressList
                                }
                            }
                            else null
                        }



                    }
                    if (addresses!=null) {
                        call.respond(addresses)
                    } else {
                        call.respond("[]")
                    }
                }

                delete("/{id}") {
                    val id = call.parameters["id"]
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()

                    val addresses=
                    transaction {
                        User.find(Users.email eq email).let{
                            if (!it.empty()) {
                                    Address.find((Addresses.id eq id!!.toLong()) and (Addresses.user eq it.iterator().next().id)).iterator().let{
                                    if (it.hasNext()) {
                                        it.next().delete()
                                    }
                                }
                                Address.find(Addresses.user eq it.iterator().next().id).let{ it2->
                                    var addressList=ArrayList<AddressDto>()
                                    val iterator=it2.iterator();
                                    while(iterator.hasNext()){
                                        val addr=iterator.next()
                                        addressList.add(
                                                AddressDto(
                                                        addr.id.value,
                                                        addr.title,
                                                        addr.line1,
                                                        addr.line2,
                                                        addr.locality,
                                                        addr.pincode,
                                                        addr.city,
                                                        addr.state,
                                                        addr.active))
                                    }
                                    addressList
                                }
                            }
                            else null


                        }


                    }
                    if (addresses!=null) {
                        call.respond(addresses)
                    } else {
                        call.respond("[]")
                    }

                }

            }
        }




    }

}





data class UserResponse(val id:String,val email:String,val name:String,val picture:String)

data class ProfileRequest(val username: String, val mobileNumber : String)
data class LoginRequest (val accessToken:String)
data class AddressDto(
        var id:Long,
        var title: String,
        var line1: String,
        var line2 : String,
        var locality : String,
        var pincode: Int,
        var city : String,
        var state : String,
        var active : Boolean
)