package repository.managers

actual interface SmartContractTransactionService {
    actual suspend fun executeTransaction(): Boolean
    actual suspend fun verifyTransaction(): Boolean
    actual suspend fun getTransaction(): Any
    actual suspend fun getTransactionStatus(): Boolean
    actual suspend fun getTransactionHistory(Any: Any): List<Any>
    actual suspend fun createSmartContract(Any: Any): Boolean
    actual suspend fun deploySmartContract(): Boolean
    actual suspend fun updateSmartContract(): Any
    actual suspend fun deleteSmartContractStatus(): Boolean
    actual suspend fun querySmartContract(): Any

}