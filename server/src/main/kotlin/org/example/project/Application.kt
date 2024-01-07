package org.example.project

import BASE_LINK_GET
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.DAL.ExposedPostgres
import org.example.project.secure.checksUsersAccessConditions
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
            //регистрация в логе всех остальных запросов
            get("*"){
                logger.error(" responding to different request " + call.request.uri)
            }

            //возвращает json для страницы профиля
            get("/$BASE_LINK_GET/name/{name}&password/{password}&token/{token}/profile") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]
                val token = call.parameters["token"]

                if (!checksUsersAccessConditions(token.toString(), name.toString(), password.toString())) return@get

                val data = ExposedPostgres().getTableDataUsersAsJson(name!!.toString())

                call.respondText(data.toString()) //возвращаемое значение
            }


            //данные пользователя при входе в приложение
            get("/$BASE_LINK_GET/name/{name}&password/{password}") {
                val name = call.parameters["name"]
                val password = call.parameters["password"]

                //проверка на пустые значения
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


        }
    }catch (e : Exception){
        logger.error("exception in Application.module() = " + e.printStackTrace())
    }
}

data class Address (val city : String, val country : String)