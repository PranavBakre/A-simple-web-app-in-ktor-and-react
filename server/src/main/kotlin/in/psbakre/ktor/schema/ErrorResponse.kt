package `in`.psbakre.ktor.schema

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val statusCode:Int,
    val message: String
)
