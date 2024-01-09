package model

data class UserJson(val name: String, val email: String, val role: String)
data class WalletJson(val account: String, val key: String)
data class MyObject(val user: UserJson, val wallet: WalletJson)