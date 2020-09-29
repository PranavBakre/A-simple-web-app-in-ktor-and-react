package com.ktor.assignment.controller

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.ktor.assignment.models.User
import com.ktor.assignment.models.Users
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import freemarker.cache.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.sessions.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Sessions) {
        cookie<AuthSession>("Auth") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ConditionalHeaders)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }


    install(ContentNegotiation) {
        gson {
            setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
        }
    }



    routing {
        get("/") {
            var login=false
            val auth=call.sessions.get<AuthSession>();
            if(auth!=null)
            {

                call.respondRedirect("/home")
            }
            else
                call.respond(FreeMarkerContent("index.ftl", mapOf("login" to login),""))
        }
        get("/home") {
            val auth=call.sessions.get<AuthSession>();
            if(auth!=null) {
                var user = Gson().fromJson(auth.userId, UserResponse::class.java)
                var dbUserList: SizedIterable<User>?=null
                var user1: User?=null
                transaction {
                    dbUserList= User.find((Users.email eq user.email) and (Users.name eq user.name))
                    user1=if(dbUserList!=null && !dbUserList!!.empty()) dbUserList!!.iterator().next() else null
                }

                call.respond(FreeMarkerContent("home.ftl", mapOf("user" to (user1)),""))
            }

        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }

        }

    }
    authenticationModule()
}


data class AuthSession(val userId: String)

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

