package repository.managers

import model.BookItem

actual interface BookManager {
    actual suspend fun getAllBooks(): List<BookItem>
    actual suspend fun getBookById(id: String): BookItem?
    actual suspend fun addBook(bookItem: BookItem): Boolean
    actual suspend fun removeBook(id: String): Boolean
    actual suspend fun updateBook(bookItem: BookItem): Boolean
    actual suspend fun updateBookRating(bookItem: BookItem): Boolean
    actual suspend fun createBookBlockchainTransaction(bookItem: BookItem): Boolean
    actual suspend fun checkBookBlockchainTransaction(bookItem: BookItem): Boolean
    actual suspend fun viewUIBook(bookItem: BookItem): Any
    actual suspend fun updateUIViewBook(bookItem: BookItem): Boolean


}