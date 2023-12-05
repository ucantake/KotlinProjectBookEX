package repository

import model.Book
import model.User

actual interface SmartContractTransactionService {
    actual suspend fun searchUsersByWallet(wallet: String): List<User>
    actual suspend fun searchUsersByTransaction(transaction: String): List<User>
    actual suspend fun searchUsersBySmartContract(smartContract: String): List<User>
    actual suspend fun searchUsersByTransactionId(transactionId: String): User
    actual suspend fun searchUsersBySmartContractId(smartContractId: String): User
    actual suspend fun searchUsersBySmartContractAddress(smartContractAddress: String): User
    actual suspend fun searchUsersBySmartContractSender(smartContractSender: String): User
    actual suspend fun searchUsersBySmartContractReceiver(smartContractReceiver: String): User
    actual suspend fun searchUsersBySmartContractAmount(smartContractAmount: String): User
    actual suspend fun searchUsersBySmartContractDate(smartContractDate: String): User
    actual suspend fun searchUsersBySmartContractTime(smartContractTime: String): User
    actual suspend fun searchUsersBySmartContractDateTime(smartContractDateTime: String): User
    actual suspend fun searchUsersBySmartContractStatus(smartContractStatus: String): User
    actual suspend fun searchUsersBySmartContractType(smartContractType: String): User
    actual suspend fun searchBooksByStatus(status: String): List<Book>
    actual suspend fun searchBooksByBuyer(buyer: String): List<Book>
    actual suspend fun searchBooksBySeller(seller: String): List<Book>
    actual suspend fun searchBooksByTransaction(transaction: String): List<Book>
    actual suspend fun searchBooksBySmartContract(smartContract: String): List<Book>
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
    actual     suspend fun searchUsersByBalance(balance: Any): List<User>
    actual suspend fun searchUsersByCryptoWalletLink(cryptoWalletLink: String): List<User>

}