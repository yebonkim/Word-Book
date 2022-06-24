package com.example.wordbook.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wordbook.R
import com.example.wordbook.StudyActivity
import com.example.wordbook.databinding.ActivityMainBinding
import com.example.wordbook.test.TestActivity
import com.example.wordbook.vocalist.VocaListActivity
import kotlinx.coroutines.launch

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
                lifecycleScope.launch {
                    if (mViewModel.moveToStudyEnabled()) {
                        startActivity(Intent(this@MainActivity, StudyActivity::class.java))
                    } else {
                        Toast.makeText(this@MainActivity, R.string.error_limit_1_word, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        mViewModel.mMoveToTest.observe(this) {
            if (it) {
                lifecycleScope.launch {
                    if (mViewModel.moveToTestEnabled()) {
                        startActivity(Intent(this@MainActivity, TestActivity::class.java))
                    } else {
                        Toast.makeText(this@MainActivity, R.string.error_limit_4_word, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        mViewModel.mMoveToVocaList.observe(this) {
            if (it) {
                startActivity(Intent(this, VocaListActivity::class.java))
            }
        }
    }
}