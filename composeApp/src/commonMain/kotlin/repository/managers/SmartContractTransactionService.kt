 /*
    переделывает транзакции в системе в смарт контракты в блокчейне
 */
package repository.managers

expect interface SmartContractTransactionService {
    suspend fun executeTransaction(): Boolean //выполнения\создание транзакции
    suspend fun verifyTransaction(): Boolean //проверка транзакции: проверили статус смарт контракта, записали в БД
    suspend fun getTransaction(): Any //получение транзакции: чтение данных о транзакции из бд, нужен будет для проверки доступности getTransactionStatus и getTransactionHistory
    suspend fun getTransactionStatus(): Boolean //получение статуса транзакции (частный случай getTransaction)
    suspend fun getTransactionHistory(Any : Any): List<Any> //получение истории транзакций (частный случай getTransaction), входные параметры для определения параметров поиска
    suspend fun createSmartContract(Any : Any): Boolean //создание смарт контракта TODO: посмотреть какие параметры нужны, придумать как связать с solidity кодом; входные параметры должны быть от двух пользователей сразу
    suspend fun deploySmartContract(): Boolean //деплой (развертывание в блоокчейне) смарт контракта
    suspend fun updateSmartContract(): Any //TODO: ?а такое вообще возможно?
    suspend fun deleteSmartContractStatus(): Boolean //TODO ?а такое вообще возможно?
    suspend fun querySmartContract(): Any //получение текущего статуса смарт контракта (просмотр без изменения данных)

}