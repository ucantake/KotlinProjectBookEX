package org.example.project.DAL

import NAME_DB
import USER_NAME
import USER_PASSWORD
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.example.project.DAL.tables.Ganache
import org.example.project.DAL.tables.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

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
}