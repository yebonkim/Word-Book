package com.example.wordbook.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.wordbook.database.Word

@BindingAdapter("english")
fun setEnglish(textView: TextView, word: Word) {
    textView.text = word.english
}

@BindingAdapter("means")
fun setMeans(textView: TextView, word: Word) {
    textView.text = word.means
}