package org.example.project.DAL.tables

import org.jetbrains.exposed.sql.Table

object Users : Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val role = varchar("role", 50)
    val status = varchar("status", 50)
    val created_at = varchar("created_at", 50)
    val updated_at = varchar("updated_at", 50)
}