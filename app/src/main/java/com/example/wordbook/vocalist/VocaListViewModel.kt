package com.example.wordbook.vocalist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wordbook.database.Word
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository

class VocaListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordRepository(getDatabase(application))

    private val _moveToEditVoca = MutableLiveData<Boolean>()
    val moveToEditVoca: LiveData<Boolean>
        get() = _moveToEditVoca

    val vocas = repository.words

    fun moveToEditVoca() {
        _moveToEditVoca.value = true
    }
}