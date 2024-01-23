package org.example.project.DAL.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate.now

object Users : Table(){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val email = varchar("email", 50)
    val password = varchar("password", 50)
    val role = varchar("role", 50)
    val status = varchar("status", 50)
    val created_at = date("created_at").default(now())
    val updated_at = date("updated_at").default(now())
}