package model

import kotlinx.serialization.Serializable


@Serializable
data class BalanceJson(val account: String,val balanceWei : String, val balanceEth : String)
@Serializable
data class UserJson(val name: String, val email: String, val role: String)
@Serializable
data class WalletJson(val account: String, val key: String)
@Serializable
data class BooksJson (val title : String, val author : String, val price : String, val quantity : Int)
//для JSON
@Serializable
data class JsonData(val user: UserJson,
                    val wallet: WalletJson,
                    val books: BooksJson,
                    val balance: BalanceJson)

//для JSON_PROFILE
@Serializable
data class Book(
    val title: String,
    val author: String,
    val price: String
)

@Serializable
data class BooksResponse(
    val books: BooksContainer
)

@Serializable
data class BooksContainer(
    val books: List<Book>,
    val quantity: Int
)

@Serializable
data class JsonSmartContract (
    val users: List<UserJsonSmartContract>,
    val books: List<BooksJsonSmartContract>
)

@Serializable
data class UserJsonSmartContract (
    val id: String,
    val name: String
)
@Serializable
data class BooksJsonSmartContract (
    val title: String,
    val author: String,
    val user_id: String,
    val price: String,
    val genre : String,
    val date_published : String
)

//для истории транзакций
@Serializable
data class TransactionsjsonHistory (
    val transactions: List<Transactions>
)

@Serializable //изменить на лист данных от таблицы транзакции
data class Transactions(
    val book_title : String,
    val user_sender : String,
    val user_receiver : String,
    val comment : String,
    val successful : Boolean
)