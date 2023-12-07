package repository.managers

import model.User

expect interface UserManager {
    suspend fun getUserByName(identifier: String): User? //TODO в реализации identifier - это email, имя, логин, id
    suspend fun addUser(user: User): Boolean
    suspend fun removeUser(id: String): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun updateUserRating(user: User): Boolean
    suspend fun viewUIUser(user: User): Any//TODO посмотреть как в compose нужно отображать пользователя
    suspend fun updateUIViewUser(user: User): Boolean
}