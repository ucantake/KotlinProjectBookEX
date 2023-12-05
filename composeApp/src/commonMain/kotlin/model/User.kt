 /*
    общий интерфейс описывающий пользователей
 */
package model
expect interface User {
    val firstName: String           //имя пользователя
    val lastName: String            //фамилия
    val username: String            //имя в системе или логин, по которому можно найти\войти пользователя
    val password: String            //пароль пользователя
    val balance: Any                //баланс криптокашелька
    val cryptoWalletLink: String    //публичный ключ криптокошелька для доступа
    val books: List<Any>            //список имеющихся книг
}