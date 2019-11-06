package example.com.englishnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.englishnote.common.IntentExtra;
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
    ArrayList<Integer> wrongVocaIds;
    List<Integer> answerIdList;
    final int ANSWER_CNT_MAX = 4;
    final int TIME_OF_JUDGMENT_SHOWING = 2000;
    int nowQuestions = -1;

    VocabularyDBDAO db;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.action_test));
        getData();
        init();
        mHandler.post(setNewQuestionRunnable);
    }

    protected void init() {
        mHandler = new Handler();
        wrongVocaIds = new ArrayList<Integer>();
        answerIdList = new ArrayList<Integer>();
        List<Vocabulary> originalData = db.selectAll();

        for(int i=0; i<originalData.size(); i++) {
            answerIdList.add(originalData.get(i).getId());
        }
    }

    protected void getData() {
        wrongVocaIds = getIntent().getIntegerArrayListExtra(IntentExtra.VOCA_ID_LIST);
        db = new VocabularyDBDAO(this);

        if(wrongVocaIds == null) {

            data = db.selectAll();
        } else {
            data = new ArrayList<>();
            for(int id : wrongVocaIds) {
                data.add(db.selectById(id));
            }
        }

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
            Collections.shuffle(answerIdList);

            int idx=0;
            for(int cnt=0; cnt<ANSWER_CNT_MAX-1; cnt++) {
                if(answerIdList.get(idx) == data.get(nowQuestions).getId())
                    idx++;
                answers.add(db.selectById(answerIdList.get(idx++)).getMeans());
            }
            Collections.shuffle(answers);

            for(int i=0; i<ANSWER_CNT_MAX; i++) {
                meansBtns.get(i).setText(answers.get(i));
            }
            changeButtonEnable(true);
        }
    };

    protected void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void goToTestActivityWithWrongWord() {
        startActivity(new Intent(this, TestActivity.class)
                .putIntegerArrayListExtra(IntentExtra.VOCA_ID_LIST, wrongVocaIds));
        finish();;
    }

    protected void popUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.reTestWrongWord))
                .setTitle(R.string.action_test)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToTestActivityWithWrongWord();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        goToMainActivity();
                    }
                });
        builder.show();
    }

    protected void changeButtonEnable(boolean isEnable) {
        for(Button btn : meansBtns) {
            btn.setEnabled(isEnable);
        }
    }

    @OnClick({R.id.meansBtn1, R.id.meansBtn2, R.id.meansBtn3, R.id.meansBtn4})
    public void onMeansBtnsClicked(Button button) {
        changeButtonEnable(false);
        if(isCorrect(button.getText().toString())) {
            englishTV.setText(getString(R.string.msg_correct));
        } else {
            englishTV.setText(getString(R.string.msg_wrong));
            wrongVocaIds.add(data.get(nowQuestions).getId());
        }

        if(isEnd()) {
            if(wrongVocaIds.size()==0) {
                Toast.makeText(TestActivity.this, getString(R.string.allClear), Toast.LENGTH_SHORT).show();
                goToMainActivity();
            } else {
                popUpDialog();
            }

        } else
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
