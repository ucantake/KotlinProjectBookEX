package org.example.project.DAL

import NAME_DB
import DB_NAME
import USER_PASSWORD
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.example.project.DAL.tables.Books
import org.example.project.DAL.tables.Ganache
import org.example.project.DAL.tables.Transactions
import org.example.project.DAL.tables.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.LocalDate.now

//import com.google.gson.Gson


class ExposedPostgres {
    private val logger = LoggerFactory.getLogger("NettyLogger")
    private var result = "no data"

    /*
    * При инициализации класса подключается к базе данных
     */
    init {
        try {
            val db = Database.connect(
                "jdbc:postgresql://localhost:5432/"+NAME_DB,
                driver = "org.postgresql.Driver",
                user = DB_NAME,
                password = USER_PASSWORD
            )
            logger.info("Connected is exposed to PostgreSQL database!")

        }catch (e : Exception){
            logger.error("EXEPCTION " + e.message)
        }finally {

        }
    }

    fun creteTables(name : Table) {
        try {
            transaction { SchemaUtils.create(name) }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }
    }

    //возвращает значение имени пользователя
    fun getTableDataUsersAsJson(name : String) : JsonObject {
        var userId = searchUserId(name)
        var combinedJson = JsonObject()

        try {
            transaction {
                val json = Gson()

                var columnData = ""
                Users.select { Users.id eq userId }.forEach {
                    Users.columns.forEach { column ->
                        //выбранные поля добавляет в json объект возвращаемый в ответе
                        if (column.name == "name" || column.name == "email" || column.name == "role") {
                            columnData = it.get(column).toString()
                            combinedJson.add(column.name, json.toJsonTree(columnData))
                        }
                    }

                }
            }

        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return combinedJson
        }

    }

    fun getUserDataGanache (name : String) : JsonObject {
        var userId = searchUserId(name)
        var combinedJson = JsonObject()

        try {
            transaction {
                val json = Gson()

                var columnData = ""
                Ganache.select { Ganache.customerId eq userId }.forEach {
                    Ganache.columns.forEach { column ->
                        //выбранные поля добавляет в json объект возвращаемый в ответе
                        if (column.name == "account" || column.name == "key") {
                            columnData = it.get(column).toString()
                            combinedJson.add(column.name, json.toJsonTree(columnData))
                        }
                    }

                }
            }

        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return combinedJson
        }

    }

    fun getDataTableUserPassword(tableName: Table = Users, name : String): String? {

        try {
            transaction {
                val query = tableName.selectAll()
                query.forEach {
                    if (name == it.get(Users.name).toString()) {
                        result = ""+it.get(Users.name).toString()+it.get(Users.password).toString()+""
                        return@transaction
                    }

                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return result
        }
    }


    fun searchUserId (name : String) : Int {
        var userId = 0
        try {
            transaction {
                val tableName : Table = Users
                val query = tableName.selectAll()
                query.forEach {
                    if (name == it.get(Users.name).toString()) {
                        userId = it.get(Users.id)
                    }
                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return userId
        }
    }


    fun checkUserInDatabase (name : String) : Boolean {
        var result : Boolean = true
        try {
            transaction {
                val tableName : Table = Users
                val query = tableName.selectAll()
                query.forEach {
                    if (name == it.get(Users.name).toString()) {
                        result = false
                        return@transaction
                    }
                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return result
        }
    }

    fun addUser (name : String, password : String, email : String, account : String, key : String) : Boolean{
        var result = false
        try {
            val transaction = transaction {
                Users.insert {
                    it[Users.name] = name
                    it[Users.password] = password
                    it[Users.email] = email
                    it[Users.role] = "user"
                    it[Users.status] = "active"
                    it[Users.created_at] = now()
                    it[Users.updated_at] = now()
                }

            }
            addGanache(name, account, key)
            result = true
        }catch (e : Exception) {
            result = false
            logger.error("EXEPCTION in addUser " + e.message)
        }finally {
            return result
        }
    }

    fun addGanache (name: String, account : String, key : String) : Boolean {
        val userId = searchUserId(name)
        var result = false
        try {
            val transation = transaction {
                Ganache.insert {
                    it[Ganache.customerId] = userId
                    it[Ganache.account] = account
                    it[Ganache.key] = key
                }
            }
            if (transation.equals(1)){
                result = true
            }
        }catch (e : Exception) {
            result = false
            logger.error("EXEPCTION in addGanache " + e.message)
        }finally {
            return result
        }
    }

    fun searchBookQuantity(name : String) : Int {
        val userId = searchUserId(name).toString()
        var bookQuantity = 0
        try {
            transaction {
                val query = Books.selectAll()
                query.forEach {
                    if (userId == it.get(Books.userId).toString()) {
                        bookQuantity++
                    }
                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return bookQuantity
        }
    }

    fun getBookData (name : String) : JsonObject{
        var userId = searchUserId(name)
        var combinedJson = JsonObject()

        try {
            transaction {
                val json = Gson()
                var quantity = 0

                var columnData = ""
                Books.select { Books.userId eq userId }.forEach {
                    Books.columns.forEach { column ->

                        if (column.name == "user_id") {
                            if (it.get(column).toString() == userId.toString()){
                                quantity++
                            }
                        }
                        //выбранные поля добавляет в json объект возвращаемый в ответе
                        if (column.name == "title" || column.name == "author" || column.name == "price") {
                            columnData = it.get(column).toString()
                            combinedJson.add(column.name, json.toJsonTree(columnData))

                        }

                    }

                }

                if (quantity == 0) {
                    combinedJson.addProperty("title", "0")
                    combinedJson.addProperty("author", "0")
                    combinedJson.addProperty("price", "0")
                    combinedJson.addProperty("quantity", quantity)
                }

                combinedJson.addProperty("quantity", quantity)
                quantity = 0

            }

        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return combinedJson
        }
    }

    fun addBook(name : String, title : String, author : String,bbk : String, udc : String, isbn : String, price : String, genre : String, datePublished : String) : Boolean{
        val userId = searchUserId(name)
        try {
            transaction {
                Books.insert {
                    it[Books.userId] = userId
                    it[Books.title] = title
                    it[Books.bbk] = bbk
                    it[Books.udc] = udc
                    it[Books.isbn] = isbn
                    it[Books.author] = author
                    it[Books.price] = price.toLong()
                    it[Books.genre] = genre
                    it[Books.datePublished] = datePublished.toInt()
                }
            }
            return true
        }catch (e : Exception) {
            logger.error("EXEPCTION in addBook" + e.message)
            return false
        }
    }


    fun getBooksJson(name: String): JsonObject {
        val userId = searchUserId(name)
        val combinedJson = JsonObject()

        try {
            transaction {
                val json = Gson()
                val booksArray = JsonArray()
                var quantity = 0

                Books.select { Books.userId eq userId }.forEach { bookRow ->
                    val bookObject = JsonObject()

                    Books.columns.forEach { column ->
                        when (column.name) {
                            "user_id" -> {
                                if (bookRow[column].toString() == userId.toString()) {
                                    quantity++
                                }
                            }
                            "title", "author", "price" -> {
                                val columnData = bookRow[column].toString()
                                bookObject.addProperty(column.name, columnData)
                            }
                        }
                    }

                    booksArray.add(bookObject)
                }

                combinedJson.add("books", booksArray)
                combinedJson.addProperty("quantity", quantity)
            }

        } catch (e: Exception) {
            logger.error("EXCEPTION " + e.message)
        } finally {
            return combinedJson
        }
    }

    fun getUsersJsonNotCurrentUser(name: String, booksData : List<JsonObject>): List<JsonObject> {
        val result = mutableListOf<JsonObject>()
        val idUsers = booksData.map { it.get("user_id").asInt }.toList()
        var checkDataForEach = false
        try {
            transaction {
                Users.selectAll().forEach { userRow ->
                    if (userRow[Users.name].toString() == name || checkDataForEach) return@forEach
                    val combinedJson = JsonObject()
                    Users.columns.forEach { column ->
                        if (column.name == "name" || column.name == "id") {
                            if (column.name == "id" && !idUsers.contains(userRow[column])) {
                                checkDataForEach = true
                                return@forEach
                            }
                            else {
                                combinedJson.addProperty(column.name, userRow[column].toString())
                            }
                        }
                    }
                    if (userRow[Users.name].toString() == name || checkDataForEach) {
                        checkDataForEach = false
                        return@forEach
                    }
                    result.add(combinedJson)
                }
            }
        } catch (e: Exception) {
            logger.error("EXCEPTION " + e.message)
        } finally {
            return result
        }
    }


    fun getBooksJsonNotCurrentUser (name: String) : List<JsonObject> {
        val userId = searchUserId(name)

        val result = mutableListOf<JsonObject>()
        try {
            transaction {
                var quantity = 0

                Books.selectAll().forEach { bookRow ->
                    if (bookRow[Books.userId].toString() == userId.toString()) return@forEach
                    val combinedJson = JsonObject()

                    Books.columns.forEach { column ->
                        when (column.name) {
                            "user_id" -> {
                                if (bookRow[column].toString() != userId.toString()) {
                                    combinedJson.addProperty(column.name, bookRow[column].toString())
                                    quantity++
                                }
                            }
                            "title", "author", "price"-> {

                                combinedJson.addProperty(column.name, bookRow[column].toString())
                            }
                        }
                    }
                    result.add(combinedJson)
                }
            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return result
        }

    }

    fun getBookTitle (bookId : String) : String {
        var bookTitle = ""
        try {
            transaction {
                val query = Books.selectAll()
                query.forEach {
                    if (bookId == it.get(Books.bookId).toString()) {
                        bookTitle = it.get(Books.title).toString()
                        return@transaction
                    }
                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return bookTitle
        }
    }

    fun getUserName (userId : String) : String {
        var userName = ""
        try {
            transaction {
                val query = Users.selectAll()
                query.forEach {
                    if (userId == it.get(Users.id).toString()) {
                        userName = it.get(Users.name).toString()
                        return@transaction
                    }
                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return userName
        }
    }

    /*
    * функция для получения приватного ключа для ganache
     */
    fun getPrivateKey (name : String) : String {
        var userId = searchUserId(name)
        var privateKey = ""
        try {
            transaction {
                val tableName: Table = Ganache
                val query = tableName.selectAll()
                query.forEach {
                    if (userId == it.get(Ganache.customerId)) {
                        privateKey = it.get(Ganache.key).toString()
                        return@transaction
                    }
                }

            }
        } catch (e: Exception) {
            logger.error("EXEPCTION " + e.message)
        } finally {
            return privateKey
        }
    }

    /*
    * функция получения адреса для ganache
     */
    fun getAddress (name : String) : String {
        var userId = searchUserId(name)
        var address = ""
        try {
            transaction {
                val tableName: Table = Ganache
                val query = tableName.selectAll()
                query.forEach {
                    if (userId == it.get(Ganache.customerId)) {
                        address = it.get(Ganache.account).toString()
                        return@transaction
                    }
                }

            }
        } catch (e: Exception) {
            logger.error("EXEPCTION " + e.message)
        } finally {
            return address
        }
    }

    /*
    * изменение пользовательского id книги но поиск книги по её названию
     */
    fun updateIdBook (name : String, title : String) :Boolean {
        val userId = searchUserId(name)
        var result = false
        try {
            transaction {
                Books.update({ Books.title eq title }) {
                    it[Books.userId] = userId
                }
            }
            result = true
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
            result = false
        }finally {
            return result
        }
    }

    /*
    * функция поиска id книги по её названию и пользователю
     */
    fun searchBookId (name : String, title : String) : Int {
        val userId = searchUserId(name)
        var bookId = 0
        try {
            transaction {
                val query = Books.selectAll()
                query.forEach {
                    if (userId == it.get(Books.userId) && title == it.get(Books.title)) {
                        bookId = it.get(Books.bookId)
                    }
                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return bookId
        }
    }

    /*
    * функция добавления записи в таблицу Transaction для смарт контракта
     */
    fun addTransaction (userSender: String,userReceiver: String, valueBook: String) : Boolean {
        val userSenderId = searchUserId(userSender)
        val userReceiverid = searchUserId(userReceiver)
        val bookId = searchBookId(userSender, valueBook)
        var result = false
        try {
            transaction {
                Transactions.insert {
                    it[Transactions.bookId] = bookId
                    it[Transactions.userSenderId] = userSenderId
                    it[Transactions.userReceiverId] = userReceiverid
                }
            }
            result = true
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
            result = false
        }finally {
            return result
        }
    }

    /*
    * функция поиска данных из таблицы Transactions
     */
    fun searchDataTransactionsData (name : String) : JsonObject {
        val userId = searchUserId(name)
        var combinedJson = JsonObject()
        try {
            transaction {
                val booksArray = JsonArray()

                Transactions.select { Transactions.userSenderId eq userId }.forEach { transactionRow ->
                    val bookObject = JsonObject()

                    Transactions.columns.forEach { column ->
                        when (column.name) {
                            "user_sender_id"  -> {
                                val columnData = transactionRow[column].toString()
                                val userName = getUserName(columnData)
                                bookObject.addProperty("user_sender", userName)
                            }
                            "user_receiver_id" -> {
                                val columnData = transactionRow[column].toString()
                                val userName = getUserName(columnData)
                                bookObject.addProperty("user_receiver", userName)
                            }
                            "book_id" -> {
                                val bookId = transactionRow[column].toString()
                                val bookTitle = getBookTitle(bookId)
                                bookObject.addProperty("book_title", bookTitle)

                            }
                        }
                    }

                    booksArray.add(bookObject)
                }

                combinedJson.add("transactions", booksArray)

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return combinedJson
        }
    }
}