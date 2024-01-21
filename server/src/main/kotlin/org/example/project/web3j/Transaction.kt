package org.example.project.web3j

import org.jetbrains.annotations.TestOnly
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.tx.ChainId
import org.web3j.tx.Transfer
import org.web3j.tx.Transfer.sendFunds
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
@TestOnly
fun Transaction(
    privateKeySender: String, //приватный ключ отправителя
    toAddress: String, //адрес получателя
    value: BigInteger, // размер перевода
    gasPrice: BigInteger = BigInteger.valueOf(20_000_000_000L), // цену газа
    gasLimit: BigInteger = BigInteger.valueOf(21_000), // лимит газа
): String? {

    val web3j = Web3j.build(HttpService("http://127.0.0.1:8545"))

    val credentialsSender = Credentials.create(privateKeySender)

    //параметры транзакции
    val fromAddress = credentialsSender.address


    // Получаем подпись транзакции
    val nonce = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).send().transactionCount
    val rawTransaction = RawTransaction.createEtherTransaction(
        nonce,
        gasPrice,
        gasLimit,
        toAddress,
        Convert.toWei(BigDecimal(value), Convert.Unit.ETHER).toBigInteger()
    )

    val signedMessage = TransactionEncoder.signMessage(rawTransaction, credentialsSender)
    val hexValue = Numeric.toHexString(signedMessage)

    // возвразает адрес транзакции
    return web3j.ethSendRawTransaction(hexValue).send().transactionHash
}