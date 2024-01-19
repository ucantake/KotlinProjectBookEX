package repository

import model.BookItem
import model.User

expect interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: Int): User
    suspend fun addUser(user: User): User
    suspend fun removeUser(id: Int): Boolean
    suspend fun updateUser(user: User): User
    suspend fun searchUsersByFirstName(firstName: String): List<User>
    suspend fun searchUsersByLastName(lastName: String): List<User>
    suspend fun searchUsersByUsername(username: String): List<User>
    suspend fun searchUsersByBooks(bookItems: List<BookItem>): List<User>
}