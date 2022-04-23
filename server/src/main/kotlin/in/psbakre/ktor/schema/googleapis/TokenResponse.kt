package `in`.psbakre.ktor.schema.googleapis

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TokenResponse (
    @SerialName("id_token") val idToken: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("expires_in") val expiry: Int,
    @SerialName("token_type") val tokenType: String,
    @SerialName("scope") val scope: String
)

@kotlinx.serialization.Serializable
data class TokenError(
    @SerialName("error") val errorType: String,
    @SerialName("error_description") val description: String
)