package repository

import model.BookItem
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

    suspend fun searchBooksByStatus(status: String): List<BookItem>
    suspend fun searchBooksByBuyer(buyer: String): List<BookItem>
    suspend fun searchBooksBySeller(seller: String): List<BookItem>
    suspend fun searchBooksByTransaction(transaction: String): List<BookItem>
    suspend fun searchBooksBySmartContract(smartContract: String): List<BookItem>

    suspend fun searchBooksByTransactionId(transactionId: String): BookItem
    suspend fun searchBooksBySmartContractId(smartContractId: String): BookItem
    suspend fun searchBooksBySmartContractAddress(smartContractAddress: String): BookItem
    suspend fun searchBooksBySmartContractSender(smartContractSender: String): BookItem
    suspend fun searchBooksBySmartContractReceiver(smartContractReceiver: String): BookItem
    suspend fun searchBooksBySmartContractAmount(smartContractAmount: String): BookItem
    suspend fun searchBooksBySmartContractDate(smartContractDate: String): BookItem
    suspend fun searchBooksBySmartContractTime(smartContractTime: String): BookItem
    suspend fun searchBooksBySmartContractDateTime(smartContractDateTime: String): BookItem
    suspend fun searchBooksBySmartContractStatus(smartContractStatus: String): BookItem
    suspend fun searchBooksBySmartContractType(smartContractType: String): BookItem
    suspend fun searchBooksBySmartContractTransaction(smartContractTransaction: String): BookItem
    suspend fun searchBooksBySmartContractTransactionId(smartContractTransactionId: String): BookItem
    suspend fun searchBooksBySmartContractTransactionDate(smartContractTransactionDate: String): BookItem
    suspend fun searchBooksBySmartContractTransactionTime(smartContractTransactionTime: String): BookItem
    suspend fun searchBooksBySmartContractTransactionDateTime(smartContractTransactionDateTime: String): BookItem
    suspend fun searchBooksBySmartContractTransactionStatus(smartContractTransactionStatus: String): BookItem
    suspend fun searchBooksBySmartContractTransactionType(smartContractTransactionType: String): BookItem
    suspend fun searchBooksBySmartContractTransactionAmount(smartContractTransactionAmount: String): BookItem
    suspend fun searchBooksBySmartContractTransactionSender(smartContractTransactionSender: String): BookItem
    suspend fun searchBooksBySmartContractTransactionReceiver(smartContractTransactionReceiver: String): BookItem
    suspend fun searchBooksBySmartContractTransactionSmartContract(smartContractTransactionSmartContract: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBook(smartContractTransactionBook: String): BookItem
    suspend fun searchBooksBySmartContractTransactionSmartContractId(smartContractTransactionSmartContractId: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookId(smartContractTransactionBookId: String): BookItem
    suspend fun searchBooksBySmartContractTransactionSmartContractAddress(smartContractTransactionSmartContractAddress: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookTitle(smartContractTransactionBookTitle: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookAuthor(smartContractTransactionBookAuthor: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookYear(smartContractTransactionBookYear: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookRating(smartContractTransactionBookRating: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookPrice(smartContractTransactionBookPrice: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookOwner(smartContractTransactionBookOwner: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookStatus(smartContractTransactionBookStatus: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookBuyer(smartContractTransactionBookBuyer: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookSeller(smartContractTransactionBookSeller: String): BookItem
    suspend fun searchBooksBySmartContractTransactionBookTransaction(smartContractTransactionBookTransaction: String): BookItem
    suspend fun searchUsersByBalance(balance: Any): List<User>
    suspend fun searchUsersByCryptoWalletLink(cryptoWalletLink: String): List<User>
}