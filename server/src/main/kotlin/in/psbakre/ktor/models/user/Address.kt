package `in`.psbakre.ktor.models.user

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Addresses: LongIdTable() {
    val line1 = varchar("line1", 150)
    val line2 = varchar("line2", 150)
    val city =  varchar("city", 50)
    val state = varchar("state", 50)
    val postalCode = varchar("postal_code", 6)
    val user = reference("user", Users)
}

class Address(id: EntityID<Long>): LongEntity(id) {
    companion object: LongEntityClass<Address>(Addresses)
    var line1 by Addresses.line1
    var line2 by Addresses.line2
    var city by Addresses.city
    var state by Addresses.state
    var postalCode by Addresses.postalCode
    var user by User referencedOn Addresses.user
}