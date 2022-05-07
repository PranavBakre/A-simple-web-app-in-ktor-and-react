package `in`.psbakre.ktor.routes.profile

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.profileRoutes() {
    authenticate {
        route("/profile") {
            addressRoutes()
        }
    }

}