package com.example.wordbook.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.StudyActivity
import com.example.wordbook.TestActivity
import com.example.wordbook.VocaListActivity
import com.example.wordbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mBinding.viewModel = mViewModel

        mViewModel.mMoveToStudy.observe(this) {
            if (it) {
                startActivity(Intent(this, StudyActivity::class.java))
            }
        }

        mViewModel.mMoveToTest.observe(this) {
            if (it) {
                startActivity(Intent(this, TestActivity::class.java))
            }
        }

        mViewModel.mMoveToVocaList.observe(this) {
            if (it) {
                startActivity(Intent(this, VocaListActivity::class.java))
            }
        }
    }
}