package `in`.psbakre.ktor.schema.googleapis

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class UserInfo(
    @SerialName("family_name") val lastName: String,
    @SerialName("given_name") val firstName: String,
    @SerialName("picture") val profilePicture: String,
    @SerialName("email_verified") val isEmailVerified: Boolean,
    @SerialName("sub") val googleId: String,
    @SerialName("email") val email: String
)
