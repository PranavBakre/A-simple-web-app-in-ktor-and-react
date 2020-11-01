package controller.user.login

import com.google.gson.Gson
import models.UserResponse
import com.ktor.assignment.httpClient
import models.User
import models.Users
import com.ktor.assignment.utils.JWTConfig
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.route
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.login() {
    routing {
        route("/login") {
            handle{

                val principal = call.receive<LoginRequest>()//call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                if (principal!=null) {
                    val json = httpClient.get<String>("https://www.googleapis.com/userinfo/v2/me") {
                        header("Authorization", "Bearer ${principal.accessToken}")
                    }

                    val user= Gson().fromJson(json, UserResponse::class.java)

                    if (user.id != null) {
                        val dbUser= transaction {
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

    }
}

data class LoginRequest (val accessToken:String)