package org.example.project.DAL.tables


import org.jetbrains.exposed.sql.Table

object Ganache : Table() {
    val id = integer("id").autoIncrement()
    val customerId = integer("user_id").references(Users.id)
    val account = varchar("account", 42)
    val key = varchar("key", 66)
}