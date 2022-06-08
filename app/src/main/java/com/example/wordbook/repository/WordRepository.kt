package com.example.wordbook.repository

import androidx.lifecycle.LiveData
import com.example.wordbook.database.Word
import com.example.wordbook.database.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(private val database: WordDatabase) {
    val words: LiveData<List<Word>> = database.wordDao.selectAll()
    val counts: LiveData<Int> = database.wordDao.getCount()

    suspend fun save(word: Word): Long {
        var result: Long = -1

        withContext(Dispatchers.IO) {
            result = database.wordDao.insert(word)
        }

        return result
    }

    suspend fun update(word: Word): Int {
        var result: Int = -1

        withContext(Dispatchers.IO) {
            result = database.wordDao.update(word)
        }
        return result
    }

    suspend fun findById(id: Int): Word {
        lateinit var result: Word

        withContext(Dispatchers.IO) {
            result = database.wordDao.findById(id)
        }

        return result
    }
}