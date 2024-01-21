package model

import kotlinx.serialization.SerialName
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
    @SerialName("books")
    val booksContainer: BooksContainer
)

@Serializable
data class BooksContainer(
    @SerialName("books")
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
    val price: Int
)