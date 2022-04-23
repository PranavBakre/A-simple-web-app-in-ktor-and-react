package `in`.psbakre.ktor

import `in`.psbakre.ktor.schema.ErrorResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.slf4j.event.Level
import java.util.*


fun Application.configuration() {
    install(CORS) {
        allowCredentials = true
        allowHost("localhost:3000", listOf("http","https"))
        allowHeader(HttpHeaders.ContentType)
        HttpMethod.DefaultMethods.forEach{
            method -> allowMethod(method)
        }

    }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
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
        exception<Exception> { call: ApplicationCall, cause ->
            call.application.log.debug(cause.toString())
            var response = ErrorResponse(500,cause.message!!)
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(response)
        }
    }
}