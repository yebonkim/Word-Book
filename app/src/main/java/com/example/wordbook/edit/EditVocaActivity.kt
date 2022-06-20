package com.example.wordbook.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.ActivityEditVocaBinding
import com.example.wordbook.util.Constants
import kotlinx.coroutines.launch

class EditVocaActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityEditVocaBinding
    private lateinit var mViewModel: EditVocaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_voca)

        val mWordId = intent.getIntExtra(Constants.EXTRA_VOCA_ID, -1)
        val viewModelFactory = EditVocaViewModelFactory(application, mWordId)
        mViewModel = ViewModelProvider(this, viewModelFactory).get(EditVocaViewModel::class.java)

        mViewModel.state.observe(this) {
            when (it) {
                is EditVocaViewModelState.Ready -> mBinding.word = it.word
//                is EditVocaViewModelState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.confirm.setOnClickListener {
            val english = mBinding.englishInput.text.toString()
            val means = mBinding.meansInput.text.toString()

            lifecycleScope.launch {
                mViewModel.updateWord(english, means)
                finish()
            }
        }
    }
}