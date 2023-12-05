package model

actual interface SmartContract {
    actual fun reserveFunds(sender: User, receiver: User, amount: Any): Boolean
    actual fun releaseFunds(sender: User, receiver: User, amount: Any): Boolean
}