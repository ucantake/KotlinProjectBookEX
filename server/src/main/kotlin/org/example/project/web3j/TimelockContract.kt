//TODO сделать резервирование денег по времени согласно TimeLockContract.sol
//package org.example.project.web3j
//
//import org.web3j.crypto.WalletUtils
//import org.web3j.protocol.Web3j
//import org.web3j.protocol.http.HttpService
//import org.web3j.tx.gas.DefaultGasProvider
//import java.math.BigInteger
//
//fun TimelockContract() {
//    val web3j = Web3j.build(HttpService("https://rinkeby.infura.io/v3/"))
//    val credentials = WalletUtils.loadCredentials(
//        "password",
//        "/path/to/walletfile"
//    )
//    val contract = Timelock.load(
//        "0x123abc...",
//        web3j,
//        credentials,
//        DefaultGasProvider()
//    )
//    val transactionReceipt = contract.transfer(
//        "0xaaa...",
//        BigInteger.valueOf(1000)
//    ).send()
//    val value = contract.balance().send()
//    println(value)
//}