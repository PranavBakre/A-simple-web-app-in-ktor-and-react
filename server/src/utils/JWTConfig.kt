package com.ktor.assignment.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import models.User
import java.util.*

object JWTConfig {
private const val validityInMs = 36_000_00 * 24
private const val secret="secrets"
private val algorithm=Algorithm.HMAC512(secret)
private const val issuer="com.ktor_assignment"
val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()
fun generateToken(user:User): String=
    JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("name",user.name)
            .withClaim("email",user.email)
            .withExpiresAt(getExpiration())
            .sign(algorithm)

private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
