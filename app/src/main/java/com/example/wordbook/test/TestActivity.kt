package com.example.wordbook.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wordbook.R
import com.example.wordbook.databinding.ActivityTestBinding
import com.example.wordbook.databinding.ActivityVocaListBinding
import com.example.wordbook.vocalist.VocaListViewModel
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityTestBinding
    private lateinit var mViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        mViewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        mBinding.setLifecycleOwner(this)
        mBinding.viewModel = mViewModel
    }
}