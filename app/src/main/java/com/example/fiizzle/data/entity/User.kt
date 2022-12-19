package com.example.fiizzle.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(

    @ColumnInfo(name = "user_nickname")
    var nickname: String = "",

    @ColumnInfo(name = "user_email")
    var email: String = "",

    @ColumnInfo(name = "user_password")
    var password: String = "",

    @PrimaryKey(autoGenerate = true)
    var userIdx: Int = 0,
)
