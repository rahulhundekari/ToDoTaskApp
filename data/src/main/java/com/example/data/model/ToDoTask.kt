package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "todo_table")
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String
) : Serializable