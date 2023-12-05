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
    actual suspend fun searchBooksByStatus(status: String): List<Book>
    actual suspend fun searchBooksByBuyer(buyer: String): List<Book>
    actual suspend fun searchBooksBySeller(seller: String): List<Book>
    actual suspend fun searchBooksByTransaction(transaction: String): List<Book>
    actual suspend fun searchBooksBySmartContract(smartContract: String): List<Book>
    actual suspend fun searchBooksByDate(date: String): List<Book>
    actual suspend fun searchBooksByTime(time: String): List<Book>
    actual suspend fun searchBooksByDateTime(dateTime: String): List<Book>
    actual suspend fun searchBooksByTransactionId(transactionId: String): Book
    actual suspend fun searchBooksBySmartContractId(smartContractId: String): Book
    actual suspend fun searchBooksBySmartContractAddress(smartContractAddress: String): Book
    actual suspend fun searchBooksBySmartContractSender(smartContractSender: String): Book
    actual suspend fun searchBooksBySmartContractReceiver(smartContractReceiver: String): Book
    actual suspend fun searchBooksBySmartContractAmount(smartContractAmount: String): Book
    actual suspend fun searchBooksBySmartContractDate(smartContractDate: String): Book
    actual suspend fun searchBooksBySmartContractTime(smartContractTime: String): Book
    actual suspend fun searchBooksBySmartContractDateTime(smartContractDateTime: String): Book
    actual suspend fun searchBooksBySmartContractStatus(smartContractStatus: String): Book
    actual suspend fun searchBooksBySmartContractType(smartContractType: String): Book
    actual suspend fun searchBooksBySmartContractTransaction(smartContractTransaction: String): Book
    actual suspend fun searchBooksBySmartContractTransactionId(smartContractTransactionId: String): Book
    actual suspend fun searchBooksBySmartContractTransactionDate(smartContractTransactionDate: String): Book
    actual suspend fun searchBooksBySmartContractTransactionTime(smartContractTransactionTime: String): Book
    actual suspend fun searchBooksBySmartContractTransactionDateTime(smartContractTransactionDateTime: String): Book
    actual suspend fun searchBooksBySmartContractTransactionStatus(smartContractTransactionStatus: String): Book
    actual suspend fun searchBooksBySmartContractTransactionType(smartContractTransactionType: String): Book
    actual suspend fun searchBooksBySmartContractTransactionAmount(smartContractTransactionAmount: String): Book
    actual suspend fun searchBooksBySmartContractTransactionSender(smartContractTransactionSender: String): Book
    actual suspend fun searchBooksBySmartContractTransactionReceiver(smartContractTransactionReceiver: String): Book
    actual suspend fun searchBooksBySmartContractTransactionSmartContract(smartContractTransactionSmartContract: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBook(smartContractTransactionBook: String): Book
    actual suspend fun searchBooksBySmartContractTransactionSmartContractId(smartContractTransactionSmartContractId: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookId(smartContractTransactionBookId: String): Book
    actual suspend fun searchBooksBySmartContractTransactionSmartContractAddress(
        smartContractTransactionSmartContractAddress: String,
    ): Book

    actual suspend fun searchBooksBySmartContractTransactionBookTitle(smartContractTransactionBookTitle: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookAuthor(smartContractTransactionBookAuthor: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookYear(smartContractTransactionBookYear: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookRating(smartContractTransactionBookRating: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookPrice(smartContractTransactionBookPrice: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookOwner(smartContractTransactionBookOwner: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookStatus(smartContractTransactionBookStatus: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookBuyer(smartContractTransactionBookBuyer: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookSeller(smartContractTransactionBookSeller: String): Book
    actual suspend fun searchBooksBySmartContractTransactionBookTransaction(smartContractTransactionBookTransaction: String): Book

}