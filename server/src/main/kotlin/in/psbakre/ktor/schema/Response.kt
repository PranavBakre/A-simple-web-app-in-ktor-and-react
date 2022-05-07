package `in`.psbakre.ktor.schema

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val message: String,
    val success:Boolean = false,
    val errorDetails: List<ErrorDetail>? = null
)

@kotlinx.serialization.Serializable
data class ErrorDetail (
    val message: String,
    val dataPath: String
        )