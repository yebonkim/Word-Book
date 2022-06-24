package com.example.wordbook.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.databinding.ActivityTestBinding

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