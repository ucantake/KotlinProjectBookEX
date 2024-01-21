package org.example.project.DAL.tables

import org.jetbrains.exposed.sql.Table

object Transactions : Table() {
    val transactionId = integer("transaction_id").autoIncrement()
    val bookId = integer("book_id").references(Books.bookId)
    val userSenderId = integer("user_sender_id").references(Users.id)
    val userReceiverId = integer("user_receiver_id")
}