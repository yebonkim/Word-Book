package com.example.wordbook.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _mMoveToStudy = MutableLiveData<Boolean>()
    val mMoveToStudy: LiveData<Boolean>
        get() = _mMoveToStudy

    private val _mMoveToTest = MutableLiveData<Boolean>()
    val mMoveToTest: LiveData<Boolean>
        get() = _mMoveToTest

    private val _mMoveToVocaList= MutableLiveData<Boolean>()
    val mMoveToVocaList: LiveData<Boolean>
        get() = _mMoveToVocaList

    init {
        _mMoveToStudy.value = false
        _mMoveToTest.value = false
        _mMoveToVocaList.value = false
    }


    fun moveToStudy() {
        _mMoveToStudy.value = true
    }

    fun moveToTest() {
        _mMoveToTest.value = true
    }

    fun moveToVocaList() {
        _mMoveToVocaList.value = true
    }
}