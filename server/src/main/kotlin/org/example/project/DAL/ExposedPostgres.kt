package org.example.project.DAL

import NAME_DB
import USER_NAME
import USER_PASSWORD
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.example.project.DAL.tables.Books
import org.example.project.DAL.tables.Ganache
import org.example.project.DAL.tables.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                user = USER_NAME,
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

    fun addUser (name : String, password : String, email : String, account : String, key : String) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDate.now().format(formatter).toString()
        println(currentDate)
        try {
            transaction {
                Users.insert {
                    it[Users.name] = name
                    it[Users.password] = password
                    it[Users.email] = email
                    it[Users.role] = "user"
                    it[Users.status] = "active"
                    it[Users.created_at] = currentDate
                    it[Users.updated_at] = currentDate
                }
            }

            addGanache(name, account, key)
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }
    }

    fun addGanache (name: String, account : String, key : String) {
        val userId = searchUserId(name)
        try {
            transaction {
                Ganache.insert {
                    it[Ganache.customerId] = userId
                    it[Ganache.account] = account
                    it[Ganache.key] = key
                }
            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
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

    fun getBooksData (name : String) : JsonObject{
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

    fun addBook(name : String, title : String, author : String,bbk : String, udc : String, isbn : String, price : String) {
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
                }
            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }
    }
}