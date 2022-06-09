package com.example.wordbook.vocalist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordbook.EditVocaActivity
import com.example.wordbook.R
import com.example.wordbook.database.WordDatabase
import com.example.wordbook.database.getDatabase
import com.example.wordbook.databinding.ActivityVocaListBinding
import com.example.wordbook.repository.WordRepository

class VocaListActivity : AppCompatActivity() {
    private lateinit var mViewModel : VocaListViewModel
    private lateinit var mBinding: ActivityVocaListBinding
    private val mAdapter: VocaListAdapter by lazy { VocaListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(VocaListViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_voca_list)

        mBinding.viewModel = mViewModel
        mBinding.list.layoutManager = LinearLayoutManager(this)
        mBinding.list.adapter = mAdapter

        mViewModel.moveToEditVoca.observe(this) {
            if (it) {
                startActivity(Intent(this, EditVocaActivity::class.java))
            }
        }

        mViewModel.vocas.observe(this) {
            mAdapter.submitList(it)
            Log.d("Yebon", "${it.size}")
        }
    }
}