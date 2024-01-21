package org.example.project

import BASE_LINK
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.DAL.ExposedPostgres
import org.example.project.secure.EncryptionUtils
import org.example.project.secure.checksUsersAccessConditions
import org.example.project.web3j.BasicOperations
import org.example.project.web3j.Transaction
import org.slf4j.LoggerFactory
import util.tokenCreate
import webservices.TokenUsersObject

private val logger = LoggerFactory.getLogger("NettyLogger")

private val tokensUsersList = TokenUsersObject


fun main (args: Array<String>) : Unit = io.ktor.server.netty.EngineMain.main(args)

//TODO а как обрабатывать заросы которые были отправлены но не входят в роутинг?
//TODO обработка исключений SSL (когда кто-то пытается получить доступ без сертификата)

//TODO может шифровать сами сообщения? (просто для интереса) ?оконечное шифрование?

fun Application.module() {
    try {
        install(ContentNegotiation) {
            gson()
        }

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
            get("/$BASE_LINK/name/{name}&token/{token}/profile") {
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

                val booksData = dbConnect.getBookData(name!!.toString())
                data.add("user", userData)
                data.add("wallet", walletData)
                data.add("books", booksData)


                // Получение значений из вложенных объектов
                val key = walletData.get("key").asString
                val account = walletData.get("account").asString

                //получение баланса с помощью web3j из блокчейна
                val balance = BasicOperations().jsonObject(key, account)
                data.add("balance", balance)

                call.respondText(EncryptionUtils.encrypt(data.toString()), ContentType.Application.Json) //возвращаемое значение
            }


            /*
            * данные пользователя при входе в приложение
            * возвращает хэш код в качестве проверки на правильность данных
             */
            get("/$BASE_LINK/name/{name}&password/{password}") {
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
            get ("/$BASE_LINK/registration/name/{name}/password/{password}/email/{email}/{account}/{key}") {
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
            post("/$BASE_LINK/name/{name}/addBook") {
                try {
                    val name = call.parameters["name"]
                    // Получаем данные из тела POST-запроса
                    val bookData = call.receive<BookData>()
                    logger.info("Received book data: $bookData")

                    //TODO переделать проверку данных
                    //проверка входящих значений
                    if (name == null || name == "") {
                        call.respondText("Access Denied")
                        return@post
                    }

                    //соединение с базой данных
                    val dbConnect = ExposedPostgres()

                    //проверка на наличие пользователя в базе данных
                    if (!tokensUsersList.compareWithString(tokenCreate(bookData.name))) {
                        call.respondText("0")
                        logger.error("name = ${bookData.name} is not in database")
                        return@post
                    }

                    //добавление книги в базу данных
                    dbConnect.addBook(
                        name = bookData.name,
                        title = bookData.title,
                        author = bookData.author,
                        isbn = bookData.isbn,
                        udc = bookData.udc,
                        bbk = bookData.bbk,
                        price = bookData.price
                    )

                    //возвращаемое значение в виде хэш кода в качестве проверки на правильность данных
                    call.respondText((bookData.name + bookData.title).hashCode().toString())
                    logger.info("responding to user add book name = ${bookData.name}")
                } catch (e: Exception) {
                    call.respondText("Error processing request")
                    logger.error("Error processing request: ${e.message}")
                }
            }

            /*
            * запрос для поиска пользователей готовых меняться книгами
             */
            get("/$BASE_LINK/name/{name}/search") {
                val name = call.parameters["name"]

                //проверка входящих значений
                if (name == null || name.toString() == "") return@get

                //соединение с базой данных
                val dbConnect = ExposedPostgres()

                val data = JsonObject()
                //TODO книга которая уже в смарт-контракте, не может появится в этом списке
                val booksData = dbConnect.getBooksJsonNotCurrentUser(name!!.toString())
                val usersData = dbConnect.getUsersJsonNotCurrentUser(name!!.toString(), booksData)


                data.add("users", Gson().toJsonTree(usersData))
                data.add("books", Gson().toJsonTree(booksData))
                println("\n DATA users = $data\n")
                println(EncryptionUtils.encrypt(data.toString()))

                call.respondText(EncryptionUtils.encrypt(data.toString()), ContentType.Application.Json) //возвращаемое значение

            }
            /*
            * запрос для получения списка книг от найденных ранее пользователей
             */

            /*
            * запрос для получения списка книг текущего пользователя
             */
            get("/$BASE_LINK/name/{name}/getBooks") {
                val name = call.parameters["name"]

                //проверка входящих значений
                if (name == null || name.toString() == "") return@get

                //соединение с базой данных
                val dbConnect = ExposedPostgres()

                val data = JsonObject()

                val booksData = dbConnect.getBooksJson(name!!.toString())
                data.add("books", booksData)

                println(data.toString())
                println(EncryptionUtils.encrypt(data.toString()))

                call.respondText(EncryptionUtils.encrypt(data.toString()), ContentType.Application.Json) //возвращаемое значение
            }

            post ("/$BASE_LINK/createSmartContract/name/{name}/password/{password}"){
                try {
                    val name = call.parameters["name"]
                    val password = call.parameters["password"]
                    // Получаем данные из тела POST-запроса
                    val smartContractData = call.receive<CreateSmartContract>()
                    logger.info("Received book data: $smartContractData")
                    val userReciver = smartContractData.userResiver
                    val userSenderPrivateKey = ExposedPostgres().getPrivateKey(name.toString())
                    val userReciverPublicKey = ExposedPostgres().getAddress(userReciver.toString())

                    println("\n $userSenderPrivateKey $userReciverPublicKey \n")
                    //TODO написать запрос в базу данных о обновлении данных о книге которую обменяли
                    //TODO написать вывод базы данных транзакций на экран пользователя
                    val transaction = Transaction(userSenderPrivateKey, userSenderPrivateKey, smartContractData.price.toBigInteger())
                    println("\nTRANSACTION = $transaction\n")
                    if (transaction == null || transaction == "" || transaction == "0x0") {
                        call.respondText("1")
                        logger.error("responding ERROR TRANSACTION ($transaction) to user create Smart Contract name = ${smartContractData.userSender}")
                    }
                    val updateIdBook = ExposedPostgres().updateIdBook(smartContractData.userSender, smartContractData.bookTitle)
                    if (updateIdBook == false) {
                        call.respondText("1")
                        logger.error("responding ERROR UPDATE BOOK")
                    }
                    println("\nDATA\n" +
                            "userSender = ${smartContractData.userSender}\n" +
                            "userResiver = ${smartContractData.userResiver}\n" +
                            "bookTitle = ${smartContractData.bookTitle}\n" +
                            "price = ${smartContractData.price}\n" +
                            "comment = ${smartContractData.comment}\n" )
                    val noteTransaction = ExposedPostgres().addTransaction(smartContractData.userSender, smartContractData.userResiver, smartContractData.bookTitle)
                    if (noteTransaction == false) {
                        call.respondText("1")
                        logger.error("responding ERROR ADD TRANSACTION")
                    }
                    //возвращаемое значение в виде хэш кода в качестве проверки на правильность данных
                    call.respondText((name + password).hashCode().toString())
                }catch (e : Exception) {
                    call.respondText("Error processing request")
                    logger.error("exception in Application.module() post create Smart Contract = " + e.printStackTrace())
                }
            }


        }
    }catch (e : Exception){
        logger.error("exception in Application.module() = " + e.printStackTrace())
    }
}

// Определите класс BookData, который отражает структуру данных вашей книги
data class BookData(
    val name: String,
    val title: String,
    val author: String,
    val isbn: String,
    val udc: String,
    val bbk: String,
    val price: String
)

data class CreateSmartContract (
    val userSender: String,
    val userResiver: String,
    val bookTitle: String,
    val price: String,
    val comment: String
)