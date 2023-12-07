package repository.managers

import model.Book

actual interface BookManager {
    actual suspend fun getAllBooks(): List<Book>
    actual suspend fun getBookById(id: String): Book?
    actual suspend fun addBook(book: Book): Boolean
    actual suspend fun removeBook(id: String): Boolean
    actual suspend fun updateBook(book: Book): Boolean
    actual suspend fun updateBookRating(book: Book): Boolean
    actual suspend fun createBookBlockchainTransaction(book: Book): Boolean
    actual suspend fun checkBookBlockchainTransaction(book: Book): Boolean
    actual suspend fun viewUIBook(book: Book): Any
    actual suspend fun updateUIViewBook(book: Book): Boolean


}