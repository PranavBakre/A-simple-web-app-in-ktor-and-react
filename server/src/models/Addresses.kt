package models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Addresses: LongIdTable(){
    val title=varchar("title",50)
    val line1=varchar("line1",100)
    val line2=varchar("line2",100)
    val locality=varchar("locality",100)
    val pincode=integer("pincode")
    val city=varchar("city",100)
    val state = varchar("state",100)
    val active=bool("active").default(false)
    val user=reference("user", Users)
}
class Address(id: EntityID<Long>) : LongEntity(id){
    companion object : LongEntityClass<Address>(Addresses)
    var title by Addresses.title
    var line1 by Addresses.line1
    var line2 by Addresses.line2
    var locality by Addresses.locality
    var pincode by Addresses.pincode
    var city by Addresses.city
    var state by Addresses.state
    var active by Addresses.active
    var user by User referencedOn Addresses.user
}

data class AddressDto(
    var id:Long,
    var title: String,
    var line1: String,
    var line2 : String,
    var locality : String,
    var pincode: Int,
    var city : String,
    var state : String,
    var active : Boolean
)