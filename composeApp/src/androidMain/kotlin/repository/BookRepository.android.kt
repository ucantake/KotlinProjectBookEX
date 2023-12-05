package repository

import model.Book

actual interface BookRepository {
    actual suspend fun getBooks(): List<Book>
    actual suspend fun getBook(id: Int): Book
    actual suspend fun addBook(book: Book): Book
    actual suspend fun removeBook(id: Int): Boolean
    actual suspend fun updateBook(book: Book): Book
    actual suspend fun searchBooksByTitle(title: String): List<Book>
    actual suspend fun searchBooksByAuthor(author: String): List<Book>
    actual suspend fun searchBooksByGenre(genre: String): List<Book>
    actual suspend fun searchBooksByYear(year: Int): List<Book>
    actual suspend fun searchBooksByRating(rating: Int): List<Book>
    actual suspend fun searchBooksByPrice(price: Int): List<Book>
    actual suspend fun searchBooksByOwner(owner: String): List<Book>

}