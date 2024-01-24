package org.example.project.DAL.tables

import io.ktor.utils.io.core.*
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

object Books : Table() {
    val bookId = integer("book_id").autoIncrement()
    val title = varchar("title", 100)
    val author = varchar("author", 100)
    val isbn = varchar("isbn", 20)
    val udc = varchar("udc", 20)
    val bbk = varchar("bbk", 20)
    val userId = integer("user_id").references(Users.id)
    val price = long("price")
    val genre = varchar("genre", 20)
    val datePublished = integer("date_published")
}