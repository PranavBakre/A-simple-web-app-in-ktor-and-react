package `in`.psbakre.ktor

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import `in`.psbakre.ktor.routes.*
import `in`.psbakre.ktor.utils.datasource
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


fun Application.api() {

    Database.connect(datasource)
    routing {
        route("/api/v1") {
            authRoutes()
        }
    }
}