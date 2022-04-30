package `in`.psbakre.ktor.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import `in`.psbakre.ktor.models.user.User
import java.util.*

const val jwtIssuer = "in.psbakre.ktor"
val jwtSecret: String = dotenv["JWT_SECRET"]
const val accessTokenValidity: Int = 3600 * 1000
const val refreshTokenValidity: Int = 24 * 3600 * 1000
val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret)

val verifier = JWT.require(algorithm)
    .withIssuer(jwtIssuer)
    .build()

fun getExpiry(tokenType: String): Date =
    Date(System.currentTimeMillis() +
            if (tokenType == "accessToken") accessTokenValidity else refreshTokenValidity)


fun generateToken(user: User, tokenType: String) = JWT.create()
    .withSubject(tokenType)
    .withIssuer(jwtIssuer)
    .withExpiresAt(getExpiry(tokenType))
    .withClaim("userId",user.id.value)
    .sign(algorithm)