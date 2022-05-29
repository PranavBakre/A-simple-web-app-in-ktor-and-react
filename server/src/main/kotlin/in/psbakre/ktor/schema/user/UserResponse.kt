package `in`.psbakre.ktor.schema.user

import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern

@kotlinx.serialization.Serializable
data class UserResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePicture: String
)

@kotlinx.serialization.Serializable
data class InsertAddressSchema (
    val line1: String,
    val line2: String,
    val city: String,
    val state: String,
    val postalCode: String
)

@kotlinx.serialization.Serializable
data class Address(
    val id: Long,
    val line1: String,
    val line2: String,
    val city: String,
    val state: String,
    val postalCode: String
)

val addressValidator = Validation<InsertAddressSchema> {
    InsertAddressSchema::line1 {
        minLength(1)
        maxLength(150)

    }
    InsertAddressSchema::line2 {
        minLength(1)
        maxLength(150)
    }
    InsertAddressSchema::city  {
        minLength(1)
        maxLength(50)
    }
    InsertAddressSchema::state {
        minLength(1)
        maxLength(50)
    }

    InsertAddressSchema::postalCode {
        maxLength(6)
        pattern("^[1-9]{1}[0-9]{5}$") hint "postalCode should contain 6 numbers"

    }
}