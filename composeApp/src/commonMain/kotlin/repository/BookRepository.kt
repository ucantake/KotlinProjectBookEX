 /*
    общий интерфейс для репозитория книг
    нужен для сокрытия реализации доступа к данным
 */
package repository

import model.BookItem

expect interface BookRepository {
    suspend fun getBooks(): List<BookItem>
    suspend fun getBook(id: Int): BookItem
    suspend fun addBook(bookItem: BookItem): BookItem
    suspend fun removeBook(id: Int): Boolean
    suspend fun updateBook(bookItem: BookItem): BookItem
    suspend fun searchBooksByTitle(title: String): List<BookItem>
    suspend fun searchBooksByAuthor(author: String): List<BookItem>
    suspend fun searchBooksByGenre(genre: String): List<BookItem>
    suspend fun searchBooksByYear(year: Int): List<BookItem>
    suspend fun searchBooksByRating(rating: Int): List<BookItem>
    suspend fun searchBooksByPrice(price: Int): List<BookItem>
    suspend fun searchBooksByOwner(owner: String): List<BookItem>

}