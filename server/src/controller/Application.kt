package com.ktor.assignment.controller

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.ktor.assignment.AuthSession
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


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.homeModule(testing: Boolean = false) {
    routing {
//        get("/") {
//            val auth=call.sessions.get<AuthSession>();
//            if(auth!=null)
//            {
//
//                call.respondRedirect("/home")
//            }
//            else
//                call.respond(FreeMarkerContent("index.ftl", null,""))
//            }


        get("/home") {
            val auth=call.sessions.get<AuthSession>();
            if(auth!=null) {
                var user = Gson().fromJson(auth.userId, UserResponse::class.java)
                var dbUser= transaction {
                    User.find(Users.email eq user.email).let {
                        if(!it.empty())
                            it.iterator().next()
                        else null
                    }

                }

                call.respond(FreeMarkerContent("home.ftl", mapOf("user" to (dbUser)),""))
            }

        }
    }
}





