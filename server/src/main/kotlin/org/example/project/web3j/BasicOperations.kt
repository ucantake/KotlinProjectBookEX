package org.example.project.web3j

import GANACHE_RPC_SERVER
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import java.math.BigInteger

/*
* Класс для работы с базовыми операциями с блокчейном
* только для локального доступа RPC сервера Ganache
 */
class BasicOperations (){

    private val logger = LoggerFactory.getLogger("NettyLogger")

    //соединение с сервером
    val web3j = Web3j.build(HttpService(GANACHE_RPC_SERVER))

    //получение баланса
    private fun getBalance (key : String): BigInteger =
        web3j.ethGetBalance(
            Credentials.create(key).address,
            DefaultBlockParameterName.LATEST
        ).send().balance


    /*
    * возвращает json с данными о балансе
    * используется в профиле пользователя через метод get
     */
    fun jsonObject(key: String, account: String): JsonObject {
        val jsonObject = JsonObject()
        try {
            val balance = getBalance(key)

            jsonObject.addProperty("account", account).toString()
            jsonObject.addProperty("balanceWei", balance.toString()).toString()
            jsonObject.addProperty("balanceEth", balance.divide(BigInteger.TEN.pow(18)).toString()).toString()
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return jsonObject
        }
    }

    /*
    * проверка аккаунта и ключа в блокчейне
     */
    fun checkData (key : String, account : String) : Boolean {
        var data = false
        try {
            println("\nASDASDSADSDASD ${getBalance(key)}\n")
            if (getBalance(key) > BigInteger.ZERO) data = true
        }finally {
            return data
        }
    }
}