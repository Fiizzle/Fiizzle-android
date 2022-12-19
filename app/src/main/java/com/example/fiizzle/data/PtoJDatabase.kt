package com.example.fiizzle.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fiizzle.data.dao.SubjectDao
import com.example.fiizzle.data.dao.UserDao
import com.example.fiizzle.data.entity.Subject
import com.example.fiizzle.data.entity.User

@Database(entities = [User::class, Subject::class], version = 1, exportSchema = false)
abstract class PtoJDatabase : RoomDatabase() {
    abstract val userDao : UserDao
    abstract val subjectDao : SubjectDao

    companion object {
        @Volatile
        private var INSTANCE : PtoJDatabase? = null

        fun getInstance(context: Context) : PtoJDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PtoJDatabase::class.java,
                        "p_to_j_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}