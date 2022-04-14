package `in`.psbakre.ktor

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


fun Application.api() {
    routing {
        route("/api/v1") {
            get {
                call.respondText { "Hi" }
            }
        }
    }
}