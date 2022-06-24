package com.example.wordbook.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.databinding.ActivityStudyBinding

class StudyActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityStudyBinding
    private lateinit var mViewModel: StudyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_study)
        mViewModel = ViewModelProvider(this).get(StudyViewModel::class.java)

        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        mViewModel.error.observe(this) {
            if (it == true) {
                Toast.makeText(StudyActivity@this, R.string.msg_study_error, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}