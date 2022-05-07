package `in`.psbakre.ktor

import `in`.psbakre.ktor.models.user.Addresses
import `in`.psbakre.ktor.models.user.Users
import `in`.psbakre.ktor.utils.datasource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun main() {
    Database.connect(datasource)
    transaction {
        SchemaUtils.createMissingTablesAndColumns(Users, Addresses)
    }
}