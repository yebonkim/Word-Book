package com.example.wordbook.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.ActivityEditVocaBinding
import com.example.wordbook.util.Constants

class RegisterVocaActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityEditVocaBinding
    private lateinit var mViewModel: RegisterVocaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_voca)
        mViewModel = ViewModelProvider(this).get(RegisterVocaViewModel::class.java)

        mBinding.confirm.setOnClickListener {
            val english = mBinding.englishInput.text.toString()
            val means = mBinding.meansInput.text.toString()

            mViewModel.registerWord(Word.make(english, means))
            finish()
        }
    }
}