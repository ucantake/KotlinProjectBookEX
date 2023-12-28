package org.example.project.DAL.tables

import org.jetbrains.exposed.sql.Table

object Books : Table() {
    val bookId = integer("book_id").autoIncrement()
    val title = varchar("title", 100)
    val author = varchar("author", 100)
    val publicationYear = integer("publication_year")
    val isbn = varchar("isbn", 20)
    val udc = varchar("udc", 20)
    val bbk = varchar("bbk", 20)
    val userId = integer("user_id").references(Users.id)
}