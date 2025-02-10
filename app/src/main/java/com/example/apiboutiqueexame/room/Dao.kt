package com.example.apiboutiqueexame.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM user WHERE logged = 1 LIMIT 1")
    fun getUserLogged(): User?

    @Query("SELECT * FROM user WHERE logged = 1 LIMIT 1")
    fun getUserLoggedFlow(): Flow<User?>

    @Query("UPDATE user SET logged = :isLogged WHERE id = :userId")
    fun updateUserLoggedStatus(userId: Int, isLogged: Boolean)

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    fun getLastUser(): Flow<User?>

    @Query("UPDATE user SET logged = 0 WHERE logged = 1")
    fun logout()

    @Update
    suspend fun update(user: User)
}
