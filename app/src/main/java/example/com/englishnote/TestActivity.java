package example.com.englishnote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.englishnote.database.VocabularyDBDAO;
import example.com.englishnote.model.Vocabulary;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.englishTV)
    TextView englishTV;
    @BindViews({R.id.meansBtn1, R.id.meansBtn2, R.id.meansBtn3, R.id.meansBtn4})
    List<Button> meansBtns;

    List<Vocabulary> data;
    List<Integer> wrongVocaIds;
    List<Integer> answerIdxList;
    final int ANSWER_CNT_MAX = 4;
    final int TIME_OF_JUDGMENT_SHOWING = 2000;
    int nowQuestions = 0;

    VocabularyDBDAO db;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.test));
        getDataFromDB();
        init();
        mHandler.post(setNewQuestionRunnable);
    }

    protected void init() {
        mHandler = new Handler();
        wrongVocaIds = new ArrayList<Integer>();
        answerIdxList = new ArrayList<Integer>();

        for(int i=0; i<data.size(); i++) {
            answerIdxList.add(i);
        }
    }

    protected void getDataFromDB() {
        db = new VocabularyDBDAO(this);

        data = db.selectAll();
        Collections.shuffle(data);
    }

    protected boolean isCorrect(String clickedMeans) {
        if(data.get(nowQuestions).getMeans().equals(clickedMeans))
            return true;
        else
            return false;
    }

    protected boolean isEnd() {
        return (nowQuestions==data.size()-1) ? true : false;
    }

    Runnable setNewQuestionRunnable = new Runnable() {
        @Override
        public void run() {
            nowQuestions++;
            englishTV.setText(data.get(nowQuestions).getEnglish());

            List<String> answers = new ArrayList<>();

            answers.add(data.get(nowQuestions).getMeans());
            Collections.shuffle(answerIdxList);

            for(int i=0; i<ANSWER_CNT_MAX-1; i++) {
                answers.add(data.get(answerIdxList.get(i)).getMeans());
            }
            Collections.shuffle(answers);

            for(int i=0; i<ANSWER_CNT_MAX; i++) {
                meansBtns.get(i).setText(answers.get(i));
            }
        }
    };

    protected void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.meansBtn1, R.id.meansBtn2, R.id.meansBtn3, R.id.meansBtn4})
    public void onMeansBtnsClicked(Button button) {
        if(isCorrect(button.getText().toString())) {
            englishTV.setText(getString(R.string.correct));
        } else {
            englishTV.setText(getString(R.string.wrong));
            wrongVocaIds.add(data.get(nowQuestions).getId());
        }

        if(isEnd())
            //TODO
            ;
        else
            mHandler.postDelayed(setNewQuestionRunnable, TIME_OF_JUDGMENT_SHOWING);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home || id == android.R.id.home) {
            goToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        goToMainActivity();
    }
}
