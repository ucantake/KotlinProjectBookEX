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
import io.ktor.util.*
import org.example.project.DAL.ExposedPostgres
import org.example.project.secure.EncryptionUtils
import org.example.project.secure.checksUsersAccessConditions
import org.example.project.web3j.BasicOperations
import org.slf4j.LoggerFactory
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import util.stringToHex
import util.tokenCreate
import webservices.TokenUsersObject
import java.math.BigInteger
import java.nio.charset.Charset

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
                val token = call.parameters["token"]

                //проверка входящих значений
                if (!tokensUsersList.compareWithString(tokenCreate(name.toString())))
                {
                    logger.error("link = " + call.request.uri + " | " + "function get profile" + " | " + " name = $name token = $token is not in tokensUsersList ")
                    return@get
                }
                if (name.toString() == "" || token.toString() == "") {
                    logger.error("link = " + call.request.uri + " | " + "function get profile" + " | " + " name = $name token = $token is empty ")
                    return@get
                }

                //соединение с базой данных
                val dbConnect = ExposedPostgres()

                val data = JsonObject()
                val userData = dbConnect.getTableDataUsersAsJson(name!!.toString())

                val walletData = dbConnect.getUserDataGanache(name!!.toString())

                val booksData = dbConnect.getBooksData(name!!.toString())
                data.add("user", userData)
                data.add("wallet", walletData)
                data.add("books", booksData)


                // Получение значений из вложенных объектов
                val key = walletData.get("key").asString
                val account = walletData.get("account").asString

                //получение баланса с помощью web3j из блокчейна
                val balance = BasicOperations().jsonObject(key, account)
                data.add("balance", balance)

                println(data.toString())
                println(EncryptionUtils.encrypt(data.toString()))

                call.respondText(EncryptionUtils.encrypt(data.toString()), ContentType.Application.Json) //возвращаемое значение
            }


            /*
            * данные пользователя при входе в приложение
            * возвращает хэш код в качестве проверки на правильность данных
             */
            get("/$BASE_LINK_GET/name/{name}&password/{password}") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]

                //проверка входящих значений (в данном случае только на пустые значения)
                if (name == "" || password == "") return@get

                //поиск данных в базе сохраненных пользователей
                val data = ExposedPostgres().getDataTableUserPassword(name = name!!)

                //добавление токена в список, после проверки на наличие данные в базе данных
                if (data != null) {
                    tokensUsersList.addData(tokenCreate(name))
                }else return@get

                //возвращаемое значение в виде хэш кода в качестве проверки на правильность данных
                call.respondText(data.hashCode().toString())
                logger.info("responding to user sign in name = $name")
            }

            /*
            * запрос для регистрации пользователя
             */
            get ("/$BASE_LINK_GET/registration/name/{name}/password/{password}/email/{email}/{account}/{key}") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]
                val email = call.parameters["email"]
                val key = call.parameters["key"]
                val account = call.parameters["account"]

                println("DATA response name = $name password = $password email = $email")
                //проверка входящих значений
                if (!checksUsersAccessConditions(name =  name.toString(), password =  password.toString(), auth = true)) return@get

                if (!BasicOperations().checkData(key.toString(), account.toString())) {
                    call.respondText("0")
                    logger.error("account = $account or key = $key is not in blockchain")
                    return@get
                }

                //соединение с базой данных
                val dbConnect = ExposedPostgres()

                //проверка на наличие пользователя в базе данных
                if (!dbConnect.checkUserInDatabase(name.toString())) {
                    call.respondText("0")
                    logger.error("name = $name is already in database")
                    return@get
                }

                //добавление пользователя в базу данных
                dbConnect.addUser(name.toString(), password.toString(), email.toString(), account.toString(), key.toString())

                //возвращаемое значение имени и пароля
                val data = ""+name+password+""

                //возвращаемое значение в виде хэш кода в качестве проверки на правильность данных
                call.respondText(data.hashCode().toString())
                logger.info("responding to user registration name = $name")
            }


            /*
            * запрос на добавление книг в базу данных
             */
            get("/$BASE_LINK_GET/name/{name}/addBook/title/{title}/author/{author}/isbn/{isbn}/udc/{udc}/bbk/{bbk}/price/{price}") {
                val name = call.parameters["name"]
                val title = call.parameters["title"]
                val author = call.parameters["author"]
                val isbn = call.parameters["isbn"]
                val udc = call.parameters["udc"]
                val bbk = call.parameters["bbk"]
                val price = call.parameters["price"]

                //проверка входящих значений
                if (!checksUsersAccessConditions(name =  name.toString(), password =  title.toString(), auth = true)) return@get

                //соединение с базой данных
                val dbConnect = ExposedPostgres()
                println("00000000000000000000000000000000000000000")
                println("toke " + tokenCreate(name!!) + " name $name ")
                println("tokenList" + tokensUsersList.getAllData())
                //проверка на наличие пользователя в базе данных
                if (!tokensUsersList.compareWithString(tokenCreate(name!!))) {
                    call.respondText("0")
                    logger.error("name = $name is not in database")
                    return@get
                }

                //добавление книги в базу данных
                dbConnect.addBook(
                    name = name.toString(),
                    title = title.toString(),
                    author = author.toString(),
                    isbn = isbn.toString(),
                    udc = udc.toString(),
                    bbk = bbk.toString(),
                    price = price.toString()
                )

                //возвращаемое значение имени и пароля
                val data = "" + name + title + ""

                //возвращаемое значение в виде хэш кода в качестве проверки на правильность данных
                call.respondText(data.hashCode().toString())
                logger.info("responding to user add book name = $name")
            }

            /*
            * запрос для поиска пользователей
             */

            /*
            * запрос для получения списка книг от найденных ранее пользователей
             */


        }
    }catch (e : Exception){
        logger.error("exception in Application.module() = " + e.printStackTrace())
    }
}