package `in`.psbakre.ktor.schema

@kotlinx.serialization.Serializable
data class UserResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePicture: String
)
