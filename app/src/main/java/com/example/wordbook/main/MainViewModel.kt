package com.example.wordbook.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.wordbook.database.getDatabase
import com.example.wordbook.repository.WordRepository
import kotlinx.coroutines.async

class MainViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        const val LIMIT_TO_MOVE_STUDY = 1
        const val LIMIT_TO_MOVE_TEST = 4
    }
    private val repository = WordRepository(getDatabase(application))

    private val _mMoveToStudy = MutableLiveData<Boolean>(false)
    val mMoveToStudy: LiveData<Boolean>
        get() = _mMoveToStudy

    private val _mMoveToTest = MutableLiveData<Boolean>(false)
    val mMoveToTest: LiveData<Boolean>
        get() = _mMoveToTest

    private val _mMoveToVocaList= MutableLiveData<Boolean>(false)
    val mMoveToVocaList: LiveData<Boolean>
        get() = _mMoveToVocaList

    fun moveToStudy() {
        _mMoveToStudy.value = true
    }

    fun moveToStudyDone() {
        _mMoveToStudy.value = false
    }

    fun moveToTest() {
        _mMoveToTest.value = true
    }

    fun moveToTestDone() {
        _mMoveToTest.value = false
    }

    fun moveToVocaList() {
        _mMoveToVocaList.value = true
    }

    fun moveToVocaListDone() {
        _mMoveToVocaList.value = false
    }

    suspend fun moveToStudyEnabled(): Boolean {
        return viewModelScope.async {
            repository.getCounts() >= LIMIT_TO_MOVE_STUDY
        }.await()
    }

    suspend fun moveToTestEnabled(): Boolean {
        return viewModelScope.async {
            repository.getCounts() >= LIMIT_TO_MOVE_TEST
        }.await()
    }

    override fun onCleared() {
        super.onCleared()

        Log.d("Yebon", "onClear main")
    }
}