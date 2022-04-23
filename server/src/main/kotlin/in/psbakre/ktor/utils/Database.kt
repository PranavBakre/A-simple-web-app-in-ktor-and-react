package `in`.psbakre.ktor.utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

private val hikariConfig = HikariConfig().apply {
    jdbcUrl = dotenv["DATABASE_URL"]
    username = dotenv["DATABASE_USERNAME"]
    password = dotenv["DATABASE_PASSWORD"]
    driverClassName = "com.mysql.cj.jdbc.Driver"
    maximumPoolSize = 5
}

val datasource = HikariDataSource(hikariConfig);

