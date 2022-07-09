package `in`.psbakre.ktor.routes.profile

import `in`.psbakre.ktor.crud.user.getAddressById
import `in`.psbakre.ktor.crud.user.getAddresses
import `in`.psbakre.ktor.crud.user.insertAddress
import `in`.psbakre.ktor.models.user.Addresses
import `in`.psbakre.ktor.models.user.User
import `in`.psbakre.ktor.schema.ErrorDetail
import `in`.psbakre.ktor.schema.ErrorResponse
import `in`.psbakre.ktor.schema.user.Address
import `in`.psbakre.ktor.schema.user.InsertAddressSchema
import `in`.psbakre.ktor.schema.user.addressValidator
import io.konform.validation.Invalid
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import `in`.psbakre.ktor.models.user.Address as AddressModel

fun Route.addressRoutes() {
    route("/addresses") {
        get("/{id}") {
            val addressId = call.parameters["id"]
            val user = call.principal<User>()!!
            val address = getAddressById(addressId!!.toLong(),user.id)
            if(address != null){
                call.respond(GetAddressByIdResponse(address))
            } else {
                call.response.status(HttpStatusCode.NotFound)
                call.respond(ErrorResponse(message = "Address not found"))
            }

        }
        get {
            val user = call.principal<User>()!!
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 5
            val offset = call.request.queryParameters["offset"]?.toLongOrNull() ?: 0
            val addresses = getAddresses(limit, offset, user.id)

            call.respond(GetAddressListResponse(data = addresses))
        }
        post {
            val user = call.principal<User>()!!
            val body = call.receive<InsertAddressSchema>()
            val validateResult = addressValidator(body)
            if (validateResult is Invalid<InsertAddressSchema>) {
                val errors = validateResult.errors.map { error ->
                    ErrorDetail(error.message, error.dataPath)
                }
                val message = "Unable to add address. Errors found"
                call.response.status(HttpStatusCode.BadRequest)
                call.respond(ErrorResponse(message = message, errorDetails = errors))
                return@post
            }

            val id = insertAddress(
                    body.line1,
                body.line2,
                body.city,
                body.state,
                body.postalCode,
                    user
            ).id

            call.respond(InsertAddressResponse(id.value))
        }
    }
}


@kotlinx.serialization.Serializable
data class InsertAddressResponse(
    val addressId: Long,
    val message: String = "Successfully added address",
    val success: Boolean = true
)

@kotlinx.serialization.Serializable
data class GetAddressListResponse(
    val data: List<Address>,
    val message: String = "Successfully retrieved addresses",
    val success: Boolean = true
)

@kotlinx.serialization.Serializable
data class GetAddressByIdResponse(
    val data: Address,
    val message: String = "Successfully retrieved address",
    val success: Boolean = true
)