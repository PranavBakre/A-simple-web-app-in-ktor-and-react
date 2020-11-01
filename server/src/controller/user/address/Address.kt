package controller.user.address

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.principal
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import models.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

fun Application.address () {
    routing{
        authenticate {
            route("/address") {
                get {
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    val addresses= org.jetbrains.exposed.sql.transactions.transaction {
                        User.find(Users.email eq email).let {
                            if (!it.empty()) {
                                Address.find(
                                    Addresses.user eq it.iterator().next().id
                                ).let { it2 ->
                                    if (!it2.empty()) {
                                        var addressList = ArrayList<AddressDto>()
                                        val iterator = it2.iterator();
                                        while (iterator.hasNext()) {
                                            val addr = iterator.next()
                                            addressList.add(
                                                AddressDto(
                                                    addr.id.value,
                                                    addr.title,
                                                    addr.line1,
                                                    addr.line2,
                                                    addr.locality,
                                                    addr.pincode,
                                                    addr.city,
                                                    addr.state,
                                                    addr.active
                                                )
                                            )
                                        }
                                        return@transaction addressList
                                    } else null

                                }
                            } else null
                        }
                    }
                    if (addresses!=null) {
                        call.respond(addresses)
                    } else {
                        call.respond("[]")
                    }
                }
                post {
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()
                    var dbUser: User? = null
                    val params = call.receive<AddressDto>()
                    var addresses= org.jetbrains.exposed.sql.transactions.transaction {
                        User.find(Users.email eq email).let {
                            if (!it.empty()) {
                                Address.new {
                                    title = params.title
                                    line1 = params.line1
                                    line2 = params.line2
                                    locality = params.locality
                                    city = params.city
                                    state = params.state
                                    pincode = params.pincode.toInt()
                                    active = true
                                    this.user = it.iterator().next()

                                }
                                Address.find(
                                    Addresses.user eq it.iterator().next().id
                                ).let { it2 ->
                                    var addressList = ArrayList<AddressDto>()
                                    val iterator = it2.iterator();
                                    while (iterator.hasNext()) {
                                        val addr = iterator.next()
                                        addressList.add(
                                            AddressDto(
                                                addr.id.value,
                                                addr.title,
                                                addr.line1,
                                                addr.line2,
                                                addr.locality,
                                                addr.pincode,
                                                addr.city,
                                                addr.state,
                                                addr.active
                                            )
                                        )
                                    }
                                    addressList
                                }
                            } else null
                        }


                    }
                    if (addresses!=null) {
                        call.respond(addresses)
                    } else {
                        call.respond("[]")
                    }
                }

                delete("/{id}") {
                    val id = call.parameters["id"]
                    val email=call.principal<JWTPrincipal>()!!.payload.getClaim("email").asString()

                    val addresses=
                        org.jetbrains.exposed.sql.transactions.transaction {
                            User.find(Users.email eq email).let {
                                if (!it.empty()) {
                                    Address.find(
                                        (Addresses.id eq id!!.toLong()) and (Addresses.user eq it.iterator()
                                            .next().id)
                                    ).iterator().let {
                                        if (it.hasNext()) {
                                            it.next().delete()
                                        }
                                    }
                                    Address.find(
                                        Addresses.user eq it.iterator().next().id
                                    ).let { it2 ->
                                        var addressList = ArrayList<AddressDto>()
                                        val iterator = it2.iterator();
                                        while (iterator.hasNext()) {
                                            val addr = iterator.next()
                                            addressList.add(
                                                AddressDto(
                                                    addr.id.value,
                                                    addr.title,
                                                    addr.line1,
                                                    addr.line2,
                                                    addr.locality,
                                                    addr.pincode,
                                                    addr.city,
                                                    addr.state,
                                                    addr.active
                                                )
                                            )
                                        }
                                        addressList
                                    }
                                } else null


                            }


                        }
                    if (addresses!=null) {
                        call.respond(addresses)
                    } else {
                        call.respond("[]")
                    }

                }

            }
        }
    }
}