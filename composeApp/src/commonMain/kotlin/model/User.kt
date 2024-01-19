 /*
    общий интерфейс описывающий пользователей
 */
package model
expect interface User {
    val id: String                  //идентификатор пользователя
    val firstName: String           //имя пользователя
    val lastName: String            //фамилия
    val username: String            //имя в системе или логин, по которому можно найти\войти пользователя
    val password: String            //пароль пользователя
    val balance: Any                //баланс криптокашелька
    val cryptoWalletLink: String    //публичный ключ криптокошелька для доступа
    val books: List<BookItem>            //список имеющихся книг
}