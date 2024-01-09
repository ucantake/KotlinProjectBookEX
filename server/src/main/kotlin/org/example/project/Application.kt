package org.example.project

import BASE_LINK_GET
import GANACHE_RPC_SERVER
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.DAL.ExposedPostgres
import org.example.project.secure.checksUsersAccessConditions
import org.example.project.web3j.BasicOperations
import org.slf4j.LoggerFactory
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import util.tokenCreate
import webservices.TokenUsersObject
import java.math.BigInteger

private val logger = LoggerFactory.getLogger("NettyLogger")

private val tokensUsersList = TokenUsersObject


fun main (args: Array<String>) : Unit = io.ktor.server.netty.EngineMain.main(args)

//TODO а как обрабатывать заросы которые были отправлены но не входят в роутинг?
//TODO обработка исключений SSL (когда кто-то пытается получить доступ без сертификата)

//TODO может шифровать сами сообщения? (просто для интереса) ?оконечное шифрование?
fun Application.module() {
    try {
        routing {
            /*
            * регистрация в логе запросов на сервер, которые не входят в оставшиеся роутинг
             */
            get("*"){
                logger.error(" responding to different request " + call.request.uri)
            }

            /*
            * возвращает json для страницы профиля
            *
             */
            get("/$BASE_LINK_GET/name/{name}&token/{token}/profile") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]
                val token = call.parameters["token"]

                //проверка входящих значений
                if (!checksUsersAccessConditions(token.toString(), name.toString(), password.toString())) return@get

                //соединение с базой данных
                val dbConnect = ExposedPostgres()

                val data = JsonObject()
                val userData = dbConnect.getTableDataUsersAsJson(name!!.toString())

                val walletData = dbConnect.getUserDataGanache(name!!.toString())
                data.add("user", userData)
                data.add("wallet", walletData)


                // Получение значений из вложенных объектов
                val key = walletData.get("key").asString
                val account = walletData.get("account").asString

                //получение баланса с помощью web3j из блокчейна
                val balance = BasicOperations().jsonObject(key, account)
                data.add("balance", balance)

                call.respondText(data.toString(), ContentType.Application.Json) //возвращаемое значение
            }


            /*
            * данные пользователя при входе в приложение
            * возвращает хэш код в качестве проверки на правильность данных
             */
            get("/$BASE_LINK_GET/name/{name}&password/{password}") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]

                //проверка входящих значений (в данном случае только на пустые значения)
                if (!checksUsersAccessConditions(name = name.toString(), password = password.toString(), auth = true)) return@get

                //поиск данных в базе сохраненных пользователей
                val data = ExposedPostgres().getDataTableUserPassword(name = name!!)

                //добавление токена в список, после проверки на наличие данные в базе данных
                if (data != null) {
                    tokensUsersList.addData(tokenCreate(data))
                }else return@get

                //возвращаемое значение в виде хэш кода в качестве проверки на правильность данных
                call.respondText(data.hashCode().toString())
                logger.info("responding to user sign in name = $name")
            }

            /*
            * создание таблицы в базе данных
            * нужно было для создания таблицы Ganache
             */
//            post("/$BASE_LINK_GET/name/{name}&password/{password}/{token}") {
//                val name = call.parameters["name"]
//                val password = call.parameters["password"]
////                val table = call.parameters["table"]
//                val token = call.parameters["token"]
//
////                if (table == "" || table == null) return@post
//
//                //проверка входящих значений
//                if (!checksUsersAccessConditions(
//                        name = name.toString(),
//                        password = password.toString(),
//                        token = token.toString()
//                )) return@post
//
//                if (name != "admin") return@post
//
//                //создание таблицы в базе данных
//                ExposedPostgres().creteTables(Ganache)
//
//                call.respondText("t c")
//                logger.info("responding to user sign in name = $name")
//
//            }


        }
    }catch (e : Exception){
        logger.error("exception in Application.module() = " + e.printStackTrace())
    }
}