package `in`.psbakre.ktor.models.user

import io.ktor.server.auth.*
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Users: LongIdTable() {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name",50)
    val email = varchar("email", 100)
    val googleUserId = varchar("google_user_id", 100).uniqueIndex()
}

class User(id: EntityID<Long>): LongEntity(id), Principal {
    companion object: LongEntityClass<User>(Users)
    var firstName by Users.firstName
    var lastName by Users.lastName
    var email by Users.email
    var googleUserId by Users.googleUserId
}
