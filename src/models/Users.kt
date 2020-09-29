package com.ktor.assignment.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Users: LongIdTable(){
    val name= varchar("name",50).default("")
    val email=varchar("email", 50).uniqueIndex().default("")
    val username=varchar("username",50).uniqueIndex().default("")
    val mobileNumber=varchar("mobileNumber",15).default("")
    val googleAccessToken=varchar("accessToken",1000)
    val profileLock=bool("profileLocked").default(false)
    val profilePicture=varchar("profilePicture",1000).default("")
}
class User(id: EntityID<Long>)  : LongEntity(id){
    companion object : LongEntityClass<User>(Users)
    var name by Users.name
    var email by Users.email
    var username by Users.username
    var mobileNumber by Users.mobileNumber
    var accessToken by Users.googleAccessToken
    var profilePicture by Users.profilePicture
    var profileLock by Users.profileLock
}
