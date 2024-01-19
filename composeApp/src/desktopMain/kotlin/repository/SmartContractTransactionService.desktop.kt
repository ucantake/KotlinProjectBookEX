package repository

import model.BookItem
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
    actual suspend fun searchBooksByStatus(status: String): List<BookItem>
    actual suspend fun searchBooksByBuyer(buyer: String): List<BookItem>
    actual suspend fun searchBooksBySeller(seller: String): List<BookItem>
    actual suspend fun searchBooksByTransaction(transaction: String): List<BookItem>
    actual suspend fun searchBooksBySmartContract(smartContract: String): List<BookItem>
    actual suspend fun searchBooksByTransactionId(transactionId: String): BookItem
    actual suspend fun searchBooksBySmartContractId(smartContractId: String): BookItem
    actual suspend fun searchBooksBySmartContractAddress(smartContractAddress: String): BookItem
    actual suspend fun searchBooksBySmartContractSender(smartContractSender: String): BookItem
    actual suspend fun searchBooksBySmartContractReceiver(smartContractReceiver: String): BookItem
    actual suspend fun searchBooksBySmartContractAmount(smartContractAmount: String): BookItem
    actual suspend fun searchBooksBySmartContractDate(smartContractDate: String): BookItem
    actual suspend fun searchBooksBySmartContractTime(smartContractTime: String): BookItem
    actual suspend fun searchBooksBySmartContractDateTime(smartContractDateTime: String): BookItem
    actual suspend fun searchBooksBySmartContractStatus(smartContractStatus: String): BookItem
    actual suspend fun searchBooksBySmartContractType(smartContractType: String): BookItem
    actual suspend fun searchBooksBySmartContractTransaction(smartContractTransaction: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionId(smartContractTransactionId: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionDate(smartContractTransactionDate: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionTime(smartContractTransactionTime: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionDateTime(smartContractTransactionDateTime: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionStatus(smartContractTransactionStatus: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionType(smartContractTransactionType: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionAmount(smartContractTransactionAmount: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionSender(smartContractTransactionSender: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionReceiver(smartContractTransactionReceiver: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionSmartContract(smartContractTransactionSmartContract: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBook(smartContractTransactionBook: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionSmartContractId(smartContractTransactionSmartContractId: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookId(smartContractTransactionBookId: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionSmartContractAddress(
        smartContractTransactionSmartContractAddress: String,
    ): BookItem

    actual suspend fun searchBooksBySmartContractTransactionBookTitle(smartContractTransactionBookTitle: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookAuthor(smartContractTransactionBookAuthor: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookYear(smartContractTransactionBookYear: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookRating(smartContractTransactionBookRating: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookPrice(smartContractTransactionBookPrice: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookOwner(smartContractTransactionBookOwner: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookStatus(smartContractTransactionBookStatus: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookBuyer(smartContractTransactionBookBuyer: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookSeller(smartContractTransactionBookSeller: String): BookItem
    actual suspend fun searchBooksBySmartContractTransactionBookTransaction(smartContractTransactionBookTransaction: String): BookItem
    actual     suspend fun searchUsersByBalance(balance: Any): List<User>
    actual suspend fun searchUsersByCryptoWalletLink(cryptoWalletLink: String): List<User>

}