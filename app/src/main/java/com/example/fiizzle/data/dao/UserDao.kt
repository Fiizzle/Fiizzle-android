package com.example.fiizzle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fiizzle.data.entity.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user : User)

    @Delete
    fun deleteUser(user : User)

    @Update
    fun updateUser(user : User)

    @Query("SELECT * FROM user_table WHERE userIdx= :userId")
    fun getUserInfo(userId : Int) : User

    @Query("SELECT userIdx FROM user_table WHERE user_email=:email AND user_password=:pw")
    fun getUserIdxWithLogin(email : String, pw : String) : Int
}