package repository

import model.Book
import model.User

expect interface SmartContractTransactionService {
    suspend fun searchUsersByWallet(wallet: String): List<User>
    suspend fun searchUsersByTransaction(transaction: String): List<User>
    suspend fun searchUsersBySmartContract(smartContract: String): List<User>
    suspend fun searchUsersByTransactionId(transactionId: String): User
    suspend fun searchUsersBySmartContractId(smartContractId: String): User
    suspend fun searchUsersBySmartContractAddress(smartContractAddress: String): User
    suspend fun searchUsersBySmartContractSender(smartContractSender: String): User
    suspend fun searchUsersBySmartContractReceiver(smartContractReceiver: String): User
    suspend fun searchUsersBySmartContractAmount(smartContractAmount: String): User
    suspend fun searchUsersBySmartContractDate(smartContractDate: String): User
    suspend fun searchUsersBySmartContractTime(smartContractTime: String): User
    suspend fun searchUsersBySmartContractDateTime(smartContractDateTime: String): User
    suspend fun searchUsersBySmartContractStatus(smartContractStatus: String): User
    suspend fun searchUsersBySmartContractType(smartContractType: String): User

    suspend fun searchBooksByStatus(status: String): List<Book>
    suspend fun searchBooksByBuyer(buyer: String): List<Book>
    suspend fun searchBooksBySeller(seller: String): List<Book>
    suspend fun searchBooksByTransaction(transaction: String): List<Book>
    suspend fun searchBooksBySmartContract(smartContract: String): List<Book>

    suspend fun searchBooksByTransactionId(transactionId: String): Book
    suspend fun searchBooksBySmartContractId(smartContractId: String): Book
    suspend fun searchBooksBySmartContractAddress(smartContractAddress: String): Book
    suspend fun searchBooksBySmartContractSender(smartContractSender: String): Book
    suspend fun searchBooksBySmartContractReceiver(smartContractReceiver: String): Book
    suspend fun searchBooksBySmartContractAmount(smartContractAmount: String): Book
    suspend fun searchBooksBySmartContractDate(smartContractDate: String): Book
    suspend fun searchBooksBySmartContractTime(smartContractTime: String): Book
    suspend fun searchBooksBySmartContractDateTime(smartContractDateTime: String): Book
    suspend fun searchBooksBySmartContractStatus(smartContractStatus: String): Book
    suspend fun searchBooksBySmartContractType(smartContractType: String): Book
    suspend fun searchBooksBySmartContractTransaction(smartContractTransaction: String): Book
    suspend fun searchBooksBySmartContractTransactionId(smartContractTransactionId: String): Book
    suspend fun searchBooksBySmartContractTransactionDate(smartContractTransactionDate: String): Book
    suspend fun searchBooksBySmartContractTransactionTime(smartContractTransactionTime: String): Book
    suspend fun searchBooksBySmartContractTransactionDateTime(smartContractTransactionDateTime: String): Book
    suspend fun searchBooksBySmartContractTransactionStatus(smartContractTransactionStatus: String): Book
    suspend fun searchBooksBySmartContractTransactionType(smartContractTransactionType: String): Book
    suspend fun searchBooksBySmartContractTransactionAmount(smartContractTransactionAmount: String): Book
    suspend fun searchBooksBySmartContractTransactionSender(smartContractTransactionSender: String): Book
    suspend fun searchBooksBySmartContractTransactionReceiver(smartContractTransactionReceiver: String): Book
    suspend fun searchBooksBySmartContractTransactionSmartContract(smartContractTransactionSmartContract: String): Book
    suspend fun searchBooksBySmartContractTransactionBook(smartContractTransactionBook: String): Book
    suspend fun searchBooksBySmartContractTransactionSmartContractId(smartContractTransactionSmartContractId: String): Book
    suspend fun searchBooksBySmartContractTransactionBookId(smartContractTransactionBookId: String): Book
    suspend fun searchBooksBySmartContractTransactionSmartContractAddress(smartContractTransactionSmartContractAddress: String): Book
    suspend fun searchBooksBySmartContractTransactionBookTitle(smartContractTransactionBookTitle: String): Book
    suspend fun searchBooksBySmartContractTransactionBookAuthor(smartContractTransactionBookAuthor: String): Book
    suspend fun searchBooksBySmartContractTransactionBookYear(smartContractTransactionBookYear: String): Book
    suspend fun searchBooksBySmartContractTransactionBookRating(smartContractTransactionBookRating: String): Book
    suspend fun searchBooksBySmartContractTransactionBookPrice(smartContractTransactionBookPrice: String): Book
    suspend fun searchBooksBySmartContractTransactionBookOwner(smartContractTransactionBookOwner: String): Book
    suspend fun searchBooksBySmartContractTransactionBookStatus(smartContractTransactionBookStatus: String): Book
    suspend fun searchBooksBySmartContractTransactionBookBuyer(smartContractTransactionBookBuyer: String): Book
    suspend fun searchBooksBySmartContractTransactionBookSeller(smartContractTransactionBookSeller: String): Book
    suspend fun searchBooksBySmartContractTransactionBookTransaction(smartContractTransactionBookTransaction: String): Book
    suspend fun searchUsersByBalance(balance: Any): List<User>
    suspend fun searchUsersByCryptoWalletLink(cryptoWalletLink: String): List<User>
}