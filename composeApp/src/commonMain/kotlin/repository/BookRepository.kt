 /*
    общий интерфейс для репозитория книг
    нужен для сокрытия реализации доступа к данным
 */
package repository

import model.Book

expect interface BookRepository {
    suspend fun getBooks(): List<Book>
    suspend fun getBook(id: Int): Book
    suspend fun addBook(book: Book): Book
    suspend fun removeBook(id: Int): Boolean
    suspend fun updateBook(book: Book): Book
    suspend fun searchBooksByTitle(title: String): List<Book>
    suspend fun searchBooksByAuthor(author: String): List<Book>
    suspend fun searchBooksByGenre(genre: String): List<Book>
    suspend fun searchBooksByYear(year: Int): List<Book>
    suspend fun searchBooksByRating(rating: Int): List<Book>
    suspend fun searchBooksByPrice(price: Int): List<Book>
    suspend fun searchBooksByOwner(owner: String): List<Book>

}