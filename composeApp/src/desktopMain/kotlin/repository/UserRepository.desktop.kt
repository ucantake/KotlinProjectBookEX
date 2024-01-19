package repository

import model.BookItem
import model.User

actual interface UserRepository {
    actual suspend fun getUsers(): List<User>
    actual suspend fun getUser(id: Int): User
    actual suspend fun addUser(user: User): User
    actual suspend fun removeUser(id: Int): Boolean
    actual suspend fun updateUser(user: User): User
    actual suspend fun searchUsersByFirstName(firstName: String): List<User>
    actual suspend fun searchUsersByLastName(lastName: String): List<User>
    actual suspend fun searchUsersByUsername(username: String): List<User>
    actual suspend fun searchUsersByBooks(bookItems: List<BookItem>): List<User>
}