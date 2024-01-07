package org.example.project.secure

import org.slf4j.LoggerFactory
import webservices.TokenUsersObject

//логгирование
private val logger = LoggerFactory.getLogger("NettyLogger")

//доступ к списку токенов
private val tokensUsersList = TokenUsersObject

/*
    * Функция проверок соединения
    * Проверки на :
    *   пустые значения токена, имени, пароля (если хотя бы одно из значений пустое то возвращается false)
    *   наличие токена в списке авторизированных пользователей
    *
    * auth - указывает на то осущетвляется ли доступ с формы авторизации
    *
    * Входные данные :
    *   токен - генерируется при входе
    *   имя - введенное пользователем
    *   пароль - введенный пользователем
    *
    * Выходные значение:
    *   true - если все проверки пройдены
    *   false - если хотя бы одна проверка не прошла
 */
fun checksUsersAccessConditions(token : String = "", name: String, password : String, auth : Boolean = false) : Boolean {
    //логгирование входных данных
    logger.info("checksUsersAccessConditions token = $token name = $name password = $password auth = $auth")

    if (auth) {//если данные с формы авторизации то проверка будет без токена
        if (name == null || password == null){
            logger.error("False Access for is auth form token = $token name = $name")
            return false
        }
        else {
            logger.info("True Access for is auth form token = $token name = $name")
            return true
        }
    } else {

        if (token == null || name == null || password == null) { //проверка не переданные значения
            logger.error("False Access for token = $token name = $name")
            return false
        } else if (token == "" || name == "" || password == "") {//проверка на пустые значения
            logger.error("False Access for token = $token name = $name")
            return false
        }


            if (tokensUsersList.compareWithString(token)) {   //проверка на наличие токена в списке
                logger.info("True Access for token = $token name = $name")
                return true
            } else {
                logger.error("False Access for token = $token name = $name")
                return false
            }

    }

}