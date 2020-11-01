package com.ktor.assignment

import models.Addresses
import models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


fun main(){
    var dbUrl=System.getenv("DATABASE_URL")
    var conn= Database.connect(dbUrl.substring(0,dbUrl.lastIndexOf('/')),
            "com.mysql.cj.jdbc.Driver",System.getenv("MYSQL_USER"),System.getenv("MYSQL_PASSWORD"))


    transaction() {
        SchemaUtils.dropDatabase(dbUrl.substring(dbUrl.lastIndexOf('/')+1))
        SchemaUtils.createDatabase(dbUrl.substring(dbUrl.lastIndexOf('/')+1))
    }
    Database.connect(System.getenv("DATABASE_URL"),
            "com.mysql.cj.jdbc.Driver",System.getenv("MYSQL_USER"),System.getenv("MYSQL_PASSWORD"))
    transaction {
        SchemaUtils.create(Users)
        SchemaUtils.create(Addresses)
    }


}
