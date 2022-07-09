package `in`.psbakre.ktor.crud.user

import `in`.psbakre.ktor.models.user.Address
import `in`.psbakre.ktor.schema.user.Address as AddressSchema
import `in`.psbakre.ktor.models.user.Addresses
import `in`.psbakre.ktor.models.user.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

fun getAddressById(addressId: Long, userId:  EntityID<Long>): AddressSchema? {
    val address = transaction {
        Address.find((Addresses.id eq addressId!!.toLong()) and (Addresses.user eq userId))
            .firstOrNull()
            ?.let{
                `in`.psbakre.ktor.schema.user.Address(it.id.value,it.line1,it.line2,it.city,it.state, it.postalCode)
            }
    }
    return address
}

fun getAddresses(limit: Int, offset: Long, userId:  EntityID<Long>): List<AddressSchema> {
    return transaction {
        Address.find(Addresses.user eq userId).limit(limit, offset).map { address ->
            `in`.psbakre.ktor.schema.user.Address(
                id = address.id.value,
                line1 = address.line1,
                line2 = address.line2,
                city = address.city,
                state = address.state,
                postalCode = address.postalCode
            )
        }
    }
}

fun insertAddress(line1: String, line2: String, city: String, state: String, postalCode: String, user: User): Address {
    return Address.new {
        this.line1 = line1
        this.line2 = line2
        this.city = city
        this.state = state
        this.postalCode = postalCode
        this.user = user
    }
}