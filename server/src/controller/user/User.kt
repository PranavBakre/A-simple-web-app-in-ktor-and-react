package controller.user

import models.UserResponse
import controller.user.address.address
import controller.user.login.login
import controller.user.profile.profile
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.principal
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import models.User
import models.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

fun Application.userModule () {
    routing{
        authenticate {
            route("/user"){
                get {
                    var email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    val user= org.jetbrains.exposed.sql.transactions.transaction {
                        User.find(Users.email eq email).let {
                            if (!it.empty()) {
                                it.iterator().next().let {
                                    UserResponse(
                                        it.id.toString(),
                                        it.email,
                                        it.name,
                                        it.profilePicture
                                    )
                                }
                            } else {
                                null
                            }
                        }

                    }
                    if (user != null) {
                        call.respond(user)
                    }
                }
            }

            login()

            profile()

            address()
        }
    }
}
