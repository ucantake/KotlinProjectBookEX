package model

actual interface Transaction {
    actual val id: String
    actual val sender: User
    actual val receiver: User
    actual val amount: Any
    actual val smartContract: SmartContract
    actual val date: String

}