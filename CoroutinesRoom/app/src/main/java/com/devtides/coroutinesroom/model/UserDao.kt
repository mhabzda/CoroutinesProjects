package com.devtides.coroutinesroom.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM user WHERE user_name = :userName")
    suspend fun getUser(userName: String): User?

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUser(userId: Long)
}