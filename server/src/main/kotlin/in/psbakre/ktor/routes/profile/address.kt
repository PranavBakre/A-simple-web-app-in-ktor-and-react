package `in`.psbakre.ktor.routes.profile

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
import org.jetbrains.exposed.sql.transactions.transaction
import `in`.psbakre.ktor.models.user.Address as AddressModel

fun Route.addressRoutes() {
    route("/address") {
        get {
            val user = call.principal<User>()!!
            val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 5
            val offset = call.request.queryParameters["offset"]?.toLongOrNull() ?: 0
            val addresses = transaction {
                AddressModel.find(Addresses.user eq user.id).limit(limit, offset).map { address ->
                    Address(
                        line1 = address.line1,
                        line2 = address.line2,
                        city = address.city,
                        state = address.state,
                        postalCode = address.postalCode
                    )
                }
            }
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

            val id = transaction {
                AddressModel.new {
                    line1 = body.line1
                    line2 = body.line2
                    city = body.city
                    state = body.state
                    postalCode = body.postalCode
                    this.user = user
                }.id
            }

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