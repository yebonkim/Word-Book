package com.example.wordbook.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val english: String,
    val means: String,
    val timestamp: String
)