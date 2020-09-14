package com.ktor.Assignment

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


fun main(){
    var conn= Database.connect(System.getenv("DATABASE_URL"),
            "org.mariadb.jdbc.Driver",System.getenv("MYSQL_USER"),System.getenv("MYSQL_PASSWORD"))

//    transaction{
//        SchemaUtils.createDatabase("KTOR_TEST")
//    }
    transaction(){
        SchemaUtils.dropDatabase("KTOR")
        SchemaUtils.createDatabase("KTOR")

    }
    Database.connect("jdbc:mysql://localhost:3306/KTOR",
            "org.mariadb.jdbc.Driver","pranav","Praan123")
    transaction {
        SchemaUtils.create(Users)
        SchemaUtils.create(Addresses)
    }


}
