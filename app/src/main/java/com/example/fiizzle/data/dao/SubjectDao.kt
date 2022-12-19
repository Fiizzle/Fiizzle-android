package com.example.fiizzle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fiizzle.data.entity.Subject

@Dao
interface SubjectDao {
    @Insert
    fun insertSubject(subject: Subject)

    @Delete
    fun deleteSubject(subject: Subject)

    @Update
    fun updateSubject(subject: Subject)

    @Query("SELECT * FROM subject_table WHERE subject_userId=:userId")
    fun getAllSubject(userId : Int) : List<Subject>
}