package com.example.wordbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Query("select * from words")
    fun selectAll(): LiveData<List<Word>>

    @Query("select * from words WHERE id = :id")
    fun findById(id: Int): LiveData<Word>

    @Query("select count(*) from words")
    fun getCount(): LiveData<Int>
}