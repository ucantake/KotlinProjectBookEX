/*
   общий интерфейс для менеджера книг
   взаимодействие с БД - getAllBooks, getBookById, addBook, removeBook, updateBook, updateBookRating
   взаимодействие с транзакциями - createBookTransaction, checkBookTransaction
   взаимодействие с UI - viewBook, updateViewBook

*/
package repository.managers

import model.BookItem

expect interface BookManager {
    suspend fun getAllBooks(): List<BookItem>
    suspend fun getBookById(id: String): BookItem?
    suspend fun addBook(bookItem: BookItem): Boolean
    suspend fun removeBook(id: String): Boolean
    suspend fun updateBook(bookItem: BookItem): Boolean
    suspend fun updateBookRating(bookItem: BookItem): Boolean
    suspend fun createBookBlockchainTransaction(bookItem: BookItem): Boolean//транзакция создает смарт контракт
    suspend fun checkBookBlockchainTransaction(bookItem: BookItem): Boolean
    suspend fun viewUIBook(bookItem: BookItem): Any//TODO посмотреть как в compose нужно отображать значения книги
    suspend fun updateUIViewBook(bookItem: BookItem): Boolean


}