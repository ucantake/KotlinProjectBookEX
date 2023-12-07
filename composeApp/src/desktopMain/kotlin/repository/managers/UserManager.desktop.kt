package repository.managers

import model.User

actual interface UserManager {
    actual suspend fun getUserByName(identifier: String): User?
    actual suspend fun addUser(user: User): Boolean
    actual suspend fun removeUser(id: String): Boolean
    actual suspend fun updateUser(user: User): Boolean
    actual suspend fun updateUserRating(user: User): Boolean
    actual suspend fun viewUIUser(user: User): Any
    actual suspend fun updateUIViewUser(user: User): Boolean
}