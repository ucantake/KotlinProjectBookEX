package org.example.project.DAL.tables

import org.jetbrains.exposed.sql.Table

object Transactions : Table() {
    val transactionId = integer("transaction_id").autoIncrement()
    val bookId = integer("book_id").references(Books.bookId)
    val userId = integer("user_id").references(Users.id)
}