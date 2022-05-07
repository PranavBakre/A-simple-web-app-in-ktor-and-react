package `in`.psbakre.ktor

import com.auth0.jwt.exceptions.TokenExpiredException
import `in`.psbakre.ktor.models.user.User
import `in`.psbakre.ktor.schema.ErrorResponse
import `in`.psbakre.ktor.schema.cookies.AccessTokenCookie
import `in`.psbakre.ktor.schema.cookies.RefreshTokenCookie
import `in`.psbakre.ktor.utils.accessTokenValidity
import `in`.psbakre.ktor.utils.refreshTokenValidity
import `in`.psbakre.ktor.utils.verifier
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level
import java.util.*

@Suppress("unused")
fun Application.configuration() {
    install(CORS) {
        allowCredentials = true
        allowHost("localhost:3000", listOf("http","https"))
        allowHost("localhost:8000", listOf("http","https"))
        allowHeader(HttpHeaders.ContentType)
        HttpMethod.DefaultMethods.forEach{
            method -> allowMethod(method)
        }

    }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
        )
    }
    install(CallLogging) {
        level = Level.INFO
        filter { call ->
            call.request.path().startsWith("/api/v1")
        }
        callIdMdc("call-id")
    }

    install(CallId) {
        header(HttpHeaders.XCorrelationId)
        generate {
            UUID.randomUUID().toString()
        }
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }

    install(StatusPages) {
        exception<kotlinx.serialization.SerializationException>{call: ApplicationCall, cause ->
            val message = cause.message!!.replace("'(.*\\.)*".toRegex(),"'").replace("with serial name","")
            call.application.log.debug(message)
            val response = ErrorResponse(message)
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(response)
        }
//        exception<TokenExpiredException> {call: ApplicationCall, cause ->
//            call.sessions.clear<AccessTokenCookie>()
//            call.respond(ErrorResponse(statusCode = 401, message="Access Token has expired"))
//        }
        exception<Exception> { call: ApplicationCall, cause ->
            call.application.log.debug(cause.toString())
            val response = ErrorResponse(cause.message!!)
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(response)
        }
    }

    install(Sessions) {
        cookie<AccessTokenCookie>("accessToken") {
            cookie.maxAgeInSeconds = accessTokenValidity
            cookie.httpOnly = true
            cookie.extensions["SameSite"]= "strict"
            cookie.domain = "localhost"
        }

        cookie<RefreshTokenCookie>("refreshToken"){
            cookie.maxAgeInSeconds = refreshTokenValidity
            cookie.httpOnly = true
            cookie.extensions["SameSite"]= "strict"
            cookie.domain = "localhost"
            cookie.path = "/auth/refresh"
        }

    }

    install(Authentication){
        session<AccessTokenCookie> {
            validate { session ->
               try {
                   val jwt = verifier.verify(session.value)
                   val claim = jwt.getClaim("userId")
                   if (!claim.isNull) {
                       transaction { User[claim.asLong()] }
                   } else null
               } catch (e: Exception) {
                   this.sessions.clear<AccessTokenCookie>()
                   this.sessions.clear<RefreshTokenCookie>()
                   null
               }
            }
        }
//        session<RefreshTokenCookie> ("refresh") {  }
    }
}