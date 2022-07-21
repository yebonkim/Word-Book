package com.example.wordbook.vocalist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository

class VocaListViewModel(application: Application): AndroidViewModel(application) {
    private val repository = WordRepository(getDatabase(application))

    private val _moveToRegisterVoca = MutableLiveData<Boolean>()
    val moveToRegisterVoca: LiveData<Boolean>
        get() = _moveToRegisterVoca

    val vocas = repository.getWordListByLiveData()

    fun moveToRegisterVoca() {
        _moveToRegisterVoca.value = true
    }

    fun moveToRegisterVocaDone() {
        _moveToRegisterVoca.value = false
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("Yebon", "cleared");
    }
}