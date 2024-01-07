package webservices

/*
* Объект для хранения токенов пользователей
* в addData добавлять токены уже созданные
 */

object TokenUsersObject {
    private val dataSet: MutableSet<String> = mutableSetOf()


    //добавлять уже токен авторизации
    fun addData(data: String) : Boolean {
        if (!compareWithString(data)) dataSet.add(data)
        else return false
        return true
    }

    //перед удалением проверяет наличие данных в объекте
    fun removeData(data: String) : Boolean {
        if (compareWithString(data)) dataSet.remove(data)
        else return false
        return true
    }

    /*
    * проверяет наличие данных в объекте
    * возвращает true или false в зависимости от наличия данных
    */
    fun compareWithString(data: String): Boolean {
        return dataSet.contains(data)
    }

    fun getAllData(): Set<String> {
        return dataSet.toSet()
    }
}