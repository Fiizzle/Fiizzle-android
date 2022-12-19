package com.example.fiizzle.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "subject_table")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    var subjectId : Int = 0,

    @ColumnInfo(name = "subject_userId")
    var userId : Int = 0,

    @ColumnInfo(name = "subject_name")
    var name : String = "",

    @ColumnInfo(name = "subject_endDate")
    var endDate : Long = 0
)
