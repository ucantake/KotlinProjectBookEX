package JsonDataObjects

data class TwoFactorTransactionData(
    val nameSender : String,
    val nameResiver : String,
    val bookTitle : String,
    val state : Boolean //определяет действие удаления или подтверждения на сервере
)
