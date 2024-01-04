package org.example.project

import BASE_LINK_GET
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.DAL.ExposedPostgres
import org.example.project.DAL.tables.Users
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
        routing {
            get("/$BASE_LINK_GET") {
                val exposedPostgres = ExposedPostgres()

                val dataTable  = exposedPostgres.getTableDataAsJson(Users)

                call.respondText("$dataTable \n Ktor: base link ")
                logger.info("responding to /")
            }

            //данные пользователя при входе в приложение
            get("/$BASE_LINK_GET/name/{name}&password/{password}") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]

                if (name == null || password == null) return@get //проверка на пустые значения

                //TODO сохранять что этот вошедшего пользователя для дальнейшей работы с ним
                val data = ExposedPostgres().getDataTableUsers(name = name!!, password = password!!)

                println("Data getDataTableUsers = " + data + "\n")

                //добавление токена в список
                if (data != null) {
                    tokensUsersList.addData(tokenCreate(data))
                }else return@get

                println(tokensUsersList.getAllData())

//                call.respondText(" Ktor: base link $name $password - user sign in") //возвращаемое значение
                call.respondText(data.hashCode().toString()) //возвращаемое значение
//                call.respond(true) //возвращаемое значение
                logger.info("responding to user sign in")
            }

            get("/$BASE_LINK_GET/name/{name}&password/{password}&token/{token}") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]
                val token = call.parameters["token"]

                if (name == null || password == null || token == null) return@get //проверка на пустые значения

                if (tokensUsersList.compareWithString(token!!)) {
                    call.respondText(" Ktor: base link $name $password - user sign in") //возвращаемое значение
                    logger.info("responding to user sign in")
                } else return@get


            }

            //регистрация в логе всех остальных запросов
            get("*"){
                logger.info(" responding to different request " + call.request.uri)
            }
        }
    }catch (e : Exception){
        logger.info("exception in Application.module() = " + e.printStackTrace())
    }
}
