package org.example.project.DAL

import NAME_DB
import USER_NAME
import USER_PASSWORD
import org.example.project.DAL.tables.Users
import org.example.project.DAL.tables.Users.name
import org.example.project.DAL.tables.Users.password
import org.slf4j.LoggerFactory
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database
//import com.google.gson.Gson


class ExposedPostgres {
    private val logger = LoggerFactory.getLogger("NettyLogger")

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

    fun getTableDataAsJson(tableName: Table): String? {
        var result = ""
        try {
            transaction {
                val query = tableName.selectAll()
                val resultSet = query.toList()
                println("RESULT query toList = " + resultSet + "\n")
                query.forEach {
                    println("RESULT query forEach = " + it + "\n")
                    println("USER name = " + it.get(Users.name) + "\n")

                }

            }
        }catch (e : Exception) {
            logger.error("EXEPCTION " + e.message)
        }finally {
            return result
        }
    }
}