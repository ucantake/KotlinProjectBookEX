package model.managers

import model.User

expect interface UserManager {
    suspend fun getUserByName(identifier: String): User?
    suspend fun addUser(user: User): Boolean
    suspend fun removeUser(id: String): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun updateUserRating(user: User): Boolean
    suspend fun viewUIUser(user: User): Any
    suspend fun updateUIViewUser(user: User): Boolean
}