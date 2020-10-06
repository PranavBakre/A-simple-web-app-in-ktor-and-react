package com.ktor.assignment

import com.google.gson.FieldNamingPolicy
import com.ktor.assignment.controller.homeModule
import com.ktor.assignment.controller.profileModule
import com.ktor.assignment.models.User
import com.ktor.assignment.models.Users
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level
import com.ktor.assignment.utils.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

val httpClient = HttpClient(Jetty)
val dbUrl=System.getenv("DATABASE_URL")
val mysqlUser=System.getenv("MYSQL_USER")
val mysqlPassword=System.getenv("MYSQL_PASSWORD")
val googleClientId=System.getenv("CLIENT_ID")
val googleClientSecret=System.getenv("CLIENT_SECRET")
var googleOAuthProvider= OAuthServerSettings.OAuth2ServerSettings(
        name = "google",
        authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
        accessTokenUrl = "https://www.googleapis.com/oauth2/v3/token",
        requestMethod = HttpMethod.Post,

        clientId = googleClientId,
        clientSecret = googleClientSecret,
        defaultScopes = listOf("profile","email")
)

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.base() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Sessions) {
        cookie<AuthSession>("Auth") {
            cookie.extensions["SameSite"] = "lax"

        }
//        header<AuthSession>("Auth") {
//
//        }
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
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
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
    install(StatusPages) {
        exception<AuthenticationException> { cause ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { cause ->
            call.respond(HttpStatusCode.Forbidden)
        }

    }
    install(Authentication) {
        oauth ("GoogleAuth"){
            client= httpClient
            providerLookup= { googleOAuthProvider }
            urlProvider={redirectUrl("/login")}

        }

        jwt {
            verifier(JWTConfig.verifier)
            realm="com.ktor_assignment"

            validate{credential ->

                val email=credential.payload.getClaim("email").asString()
                log.error(email)
                if(email!=null)
                    JWTPrincipal(credential.payload)
                else
                    null

            }
        }
    }
    Database.connect(dbUrl,
            "com.mysql.cj.jdbc.Driver", mysqlUser, mysqlPassword)




    homeModule()
    profileModule()



    routing {
        static("/") {
            resources("static")
        }
    }



}
private fun ApplicationCall.redirectUrl(path:String):String {
    val defaultPort=  if (request.origin.scheme=="http") 80 else 443
    val hostPort=request.host()+request.port().let{ port -> if (port == defaultPort) "" else ":$port"}
    val protocol = request.origin.scheme
    return "$protocol://$hostPort$path"
}


class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()


data class AuthSession(val userId: String)