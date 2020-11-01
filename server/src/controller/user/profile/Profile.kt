package controller.user.profile

import models.Address
import models.User
import models.UserDto
import models.Users
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.principal
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.profile() {
    routing{
        authenticate {
            route("/profile" ) {
                get {
                    var email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()

                    var profileLock=false
                    var addresses:List<Address>?=null
                    var user= transaction{
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
                                        User.find(Users.username eq params.username).let { userList->
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
        }
    }
}

data class ProfileRequest(val username: String, val mobileNumber : String)