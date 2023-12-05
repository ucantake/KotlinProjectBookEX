/*
   общий интерфейс для менеджера книг
   взаимодействие с БД - getAllBooks, getBookById, addBook, removeBook, updateBook, updateBookRating
   взаимодействие с транзакциями - createBookTransaction, checkBookTransaction
   взаимодействие с UI - viewBook, updateViewBook

*/
package repository.managers

import model.Book

expect interface BookManager {
    suspend fun getAllBooks(): List<Book>
    suspend fun getBookById(id: String): Book?
    suspend fun addBook(book: Book): Boolean
    suspend fun removeBook(id: String): Boolean
    suspend fun updateBook(book: Book): Boolean
    suspend fun updateBookRating(book: Book): Boolean
    suspend fun createBookBlockchainTransaction(book: Book): Boolean//транзакция создает смарт контракт
    suspend fun checkBookBlockchainTransaction(book: Book): Boolean
    suspend fun viewUIBook(book: Book): Any//TODO посмотреть как в compose нужно отображать значения книги
    suspend fun updateUIViewBook(book: Book): Boolean


}