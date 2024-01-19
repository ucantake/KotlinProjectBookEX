package model

actual interface User {
    actual val id: String
    actual val firstName: String
    actual val lastName: String
    actual val username: String
    actual val password: String
    actual val balance: Any
    actual val cryptoWalletLink: String
    actual val books: List<BookItem>
}