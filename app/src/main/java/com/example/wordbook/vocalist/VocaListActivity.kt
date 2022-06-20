package com.example.wordbook.vocalist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.databinding.ActivityVocaListBinding
import com.example.wordbook.edit.EditVocaActivity
import com.example.wordbook.register.RegisterVocaActivity
import com.example.wordbook.util.Constants

class VocaListActivity : AppCompatActivity() {
    private lateinit var mViewModel : VocaListViewModel
    private lateinit var mBinding: ActivityVocaListBinding
    private lateinit var mAdapter: VocaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(VocaListViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_voca_list)

        mBinding.viewModel = mViewModel
        mBinding.list.layoutManager = LinearLayoutManager(this)
        mAdapter = VocaListAdapter(::moveToEditVoca)
        mBinding.list.adapter = mAdapter

        mViewModel.moveToRegisterVoca.observe(this) {
            if (it) {
                moveToRegisterVoca()
            }
        }

        mViewModel.vocas.observe(this) {
            mAdapter.submitList(it)
        }
    }

    private fun moveToEditVoca(word: Word) {
        val intent = Intent(this, EditVocaActivity::class.java)
        intent.putExtra(Constants.EXTRA_VOCA_ID, word?.id ?: -1)
        startActivity(intent)
    }

    private fun moveToRegisterVoca() {
        startActivity(Intent(this, RegisterVocaActivity::class.java))
    }
}