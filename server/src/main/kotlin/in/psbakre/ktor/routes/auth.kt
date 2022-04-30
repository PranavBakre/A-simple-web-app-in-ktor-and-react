package `in`.psbakre.ktor.routes

import `in`.psbakre.ktor.models.user.User
import `in`.psbakre.ktor.models.user.Users
import `in`.psbakre.ktor.schema.ErrorResponse
import `in`.psbakre.ktor.schema.UserResponse
import `in`.psbakre.ktor.schema.googleapis.TokenError
import `in`.psbakre.ktor.schema.googleapis.TokenResponse
import `in`.psbakre.ktor.schema.googleapis.UserInfo
import `in`.psbakre.ktor.utils.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.util.date.*
import org.apache.commons.validator.routines.UrlValidator
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

val urlValidator = UrlValidator(arrayOf<String>("http","https"),UrlValidator.ALLOW_LOCAL_URLS)

fun Route.authRoutes() {
    route("/auth"){
        get {
            call.respondText { "Hi" }
            println("test")
            call.respondText { "Hi Again" }
        }
        auth()
    }
}

fun Route.auth() {
    get("/login") {
        call.respondText { "Hi" }
        println("test")
        call.respondText { "Hi Again" }
    }
    post("/login"){
        val requestBody = call.receive<LoginRequest>()
        call.application.environment.log.info(requestBody.code)

        if (!urlValidator.isValid(requestBody.url)){
            val error = ErrorResponse(400, "Invalid Url")
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(error)
            return@post
        }

        call.application.log.info("test")
        val response = httpClient.post("https://oauth2.googleapis.com/token") {
            parameter("code",requestBody.code)
            parameter("redirect_uri", requestBody.url)
            parameter("client_id",dotenv["GOOGLE_CLIENT_ID"])
            parameter("client_secret",dotenv["GOOGLE_CLIENT_SECRET"])
            parameter("grant_type", "authorization_code")
        }
        call.application.log.info("test2")
        val status = response.status


        if (status.value !in 200..299) {
            val json: TokenError = response.body()
            call.response.status(status)
            call.respond(ErrorResponse(statusCode = status.value, message = json.description))
            return@post
        }

        val json: TokenResponse = response.body()
        val userinfoResponse = httpClient.get("https://openidconnect.googleapis.com/v1/userinfo") {
            header("Authorization", "Bearer ${json.accessToken}")
        }
        val userinfo: UserInfo = userinfoResponse.body()
        var user: User = transaction {
            var listOfUsers = User.find {
                Users.googleUserId eq userinfo.googleId
            }
            if (listOfUsers.empty()) {

                User.new {
                    firstName = userinfo.firstName
                    lastName = userinfo.lastName
                    email = userinfo.email
                    googleUserId = userinfo.googleId
                }
            } else {
                listOfUsers.first()
            }
        }
        call.response.cookies.append(
            Cookie(
                name = "accessToken",
                value = generateToken(user,"accessToken"),
                expires = GMTDate().plus((accessTokenValidity).toLong()),
                httpOnly = true,
                domain = "localhost",
                extensions = mapOf("SameSite" to "Strict")
            )
        )
        call.response.cookies.append(
            Cookie(
                name = "refreshToken",
                value = generateToken(user,"refreshToken"),
                expires = GMTDate().plus((refreshTokenValidity).toLong()),
                httpOnly = true,
                domain = "localhost",
                path = "/auth/refresh",
                extensions = mapOf("SameSite" to "Strict")
            )
        )
        call.respond(UserResponse(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            profilePicture = userinfo.profilePicture
        ))

    }

}
@kotlinx.serialization.Serializable
data class LoginRequest(val code: String, val url: String)