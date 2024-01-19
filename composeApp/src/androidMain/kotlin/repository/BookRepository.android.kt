package repository

import model.BookItem

actual interface BookRepository {
    actual suspend fun getBooks(): List<BookItem>
    actual suspend fun getBook(id: Int): BookItem
    actual suspend fun addBook(bookItem: BookItem): BookItem
    actual suspend fun removeBook(id: Int): Boolean
    actual suspend fun updateBook(bookItem: BookItem): BookItem
    actual suspend fun searchBooksByTitle(title: String): List<BookItem>
    actual suspend fun searchBooksByAuthor(author: String): List<BookItem>
    actual suspend fun searchBooksByGenre(genre: String): List<BookItem>
    actual suspend fun searchBooksByYear(year: Int): List<BookItem>
    actual suspend fun searchBooksByRating(rating: Int): List<BookItem>
    actual suspend fun searchBooksByPrice(price: Int): List<BookItem>
    actual suspend fun searchBooksByOwner(owner: String): List<BookItem>

}