package com.devtides.coroutinesroom.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Query("SELECT * FROM user WHERE user_name = :userName")
    fun getUser(userName: String): User

    @Query("DELETE FROM user WHERE id = :userId")
    fun deleteUser(userId: Long)
}