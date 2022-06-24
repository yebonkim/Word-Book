package com.example.wordbook.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.wordbook.R
import com.example.wordbook.database.Word
import com.example.wordbook.test.TestViewModel

@BindingAdapter("english")
fun setEnglish(textView: TextView, word: Word) {
    textView.text = word.english
}

@BindingAdapter("means")
fun setMeans(textView: TextView, word: Word) {
    textView.text = word.means
}

@BindingAdapter("question")
fun TextView.setWordEnglish(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.question.english
    }
}

@BindingAdapter("resultState")
fun TextView.setResultState(resultState: TestViewModel.ResultState?) {
    text = if (resultState != null) {
        context.getString(
            when (resultState) {
                TestViewModel.ResultState.NONE -> R.string.msg_none
                TestViewModel.ResultState.CORRECT -> R.string.msg_correct
                TestViewModel.ResultState.WRONG -> R.string.msg_wrong
            }
        )
    } else {
        context.getString(R.string.msg_none)
    }
}

@BindingAdapter("firstCandidate")
fun TextView.setFirstCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[0].means
    }
}

@BindingAdapter("secondCandidate")
fun TextView.setSecondCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[1].means
    }
}

@BindingAdapter("thirdCandidate")
fun TextView.setThirdCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[2].means
    }
}

@BindingAdapter("fourthCandidate")
fun TextView.setFourthCandidate(item: TestViewModel.TestUnit?) {
    item?.let {
        text =  it.candidates[3].means
    }
}