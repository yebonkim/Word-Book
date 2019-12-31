package example.com.englishnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @BindView(R.id.text_english)
    TextView englishText;
    @BindViews({R.id.button_means1, R.id.button_means2, R.id.button_means3, R.id.button_means4})
    List<Button> meansBtns;

    private final static int ANSWER_CNT_MAX = 4;
    private final static int TIME_OF_JUDGMENT_SHOWING = 2000;

    private List<Vocabulary> mData;
    private ArrayList<Integer> mWrongVocaIds;
    private List<Integer> mAnswerIdList;
    private int mNowQuestions = -1;

    private VocabularyDBDAO mDb;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.action_test));
        getData();
        init();
        mHandler.post(mSettingNewQuestionRunnable);
    }

    private void init() {
        mHandler = new Handler();
        mWrongVocaIds = new ArrayList<>();
        mAnswerIdList = new ArrayList<>();
        List<Vocabulary> originalData = mDb.selectAll();

        for(int i = 0; i < originalData.size(); i++) {
            mAnswerIdList.add(originalData.get(i).getId());
        }
    }

    protected void getData() {
        mWrongVocaIds = getIntent().getIntegerArrayListExtra(IntentExtra.VOCA_ID_LIST);
        mDb = new VocabularyDBDAO(this);
        Vocabulary voca;

        if (mWrongVocaIds == null) {
            mData = mDb.selectAll();
        } else {
            mData = new ArrayList<>();

            for (int id : mWrongVocaIds) {
                voca = mDb.selectById(id);

                if (voca != null) {
                    mData.add(voca);
                }
            }
        }

        Collections.shuffle(mData);
    }

    protected boolean isCorrect(String clickedMeans) {
        if (mData.get(mNowQuestions).getMeans().equals(clickedMeans)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isEnd() {
        return (mNowQuestions == mData.size() - 1) ? true : false;
    }

    Runnable mSettingNewQuestionRunnable = new Runnable() {
        @Override
        public void run() {
            mNowQuestions++;
            englishText.setText(mData.get(mNowQuestions).getEnglish());

            List<String> answers = new ArrayList<>();

            answers.add(mData.get(mNowQuestions).getMeans());
            Collections.shuffle(mAnswerIdList);

            int idx=0;
            Vocabulary voca;
            for(int cnt = 0 ; cnt < ANSWER_CNT_MAX - 1 ; cnt++) {
                if (mAnswerIdList.get(idx) == mData.get(mNowQuestions).getId()) {
                    idx++;
                }
                voca = mDb.selectById(mAnswerIdList.get(idx++));

                if (voca != null) {
                    answers.add(voca.getMeans());
                }
            }
            Collections.shuffle(answers);

            for(int i = 0 ; i < ANSWER_CNT_MAX ; i++) {
                meansBtns.get(i).setText(answers.get(i));
            }
            changeButtonEnables(true);
        }
    };

    protected void goToMainActivity() {
        finish();
    }

    protected void goToTestActivityWithWrongWord() {
        startActivity(new Intent(this, TestActivity.class)
                .putIntegerArrayListExtra(IntentExtra.VOCA_ID_LIST, mWrongVocaIds));
        finish();
    }

    protected void popUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msg_test_again_wrong_voca))
                .setTitle(R.string.action_test)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToTestActivityWithWrongWord();
                    }
                })
                .setNegativeButton(getString(R.string.action_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        goToMainActivity();
                    }
                });
        builder.show();
    }

    protected void changeButtonEnables(boolean isEnable) {
        for(Button btn : meansBtns) {
            btn.setEnabled(isEnable);
        }
    }

    @OnClick({R.id.button_means1, R.id.button_means2, R.id.button_means3, R.id.button_means4})
    public void onMeansBtnsClicked(Button button) {
        changeButtonEnables(false);

        if(isCorrect(button.getText().toString())) {
            englishText.setText(getString(R.string.msg_correct));
        } else {
            englishText.setText(getString(R.string.msg_wrong));
            mWrongVocaIds.add(mData.get(mNowQuestions).getId());
        }

        if(isEnd()) {
            if (mWrongVocaIds.size() == 0) {
                Toast.makeText(TestActivity.this, getString(R.string.msg_all_solved), Toast.LENGTH_SHORT).show();
                goToMainActivity();
            } else {
                popUpDialog();
            }
        } else
            mHandler.postDelayed(mSettingNewQuestionRunnable, TIME_OF_JUDGMENT_SHOWING);
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
