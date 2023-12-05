package model.managers

import model.Book

expect interface BookManager {
    suspend fun getAllBooks(): List<Book>
    suspend fun getBookById(id: String): Book?
    suspend fun addBook(book: Book): Boolean
    suspend fun removeBook(id: String): Boolean
    suspend fun updateBook(book: Book): Boolean
    suspend fun updateBookRating(book: Book): Boolean
    suspend fun createBookBlockchainTransaction(book: Book): Boolean
    suspend fun checkBookBlockchainTransaction(book: Book): Boolean
    suspend fun viewUIBook(book: Book): Any
    suspend fun updateUIViewBook(book: Book): Boolean


}