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
@Serializable
data class JsonData(val user: UserJson,
                    val wallet: WalletJson,
                    val books: BooksJson,
                    val balance: BalanceJson)