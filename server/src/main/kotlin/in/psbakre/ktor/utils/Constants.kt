package `in`.psbakre.ktor.utils

import io.github.cdimascio.dotenv.dotenv

val dotenv = dotenv{
    systemProperties = true
}