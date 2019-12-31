package example.com.englishnote;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.englishnote.database.VocabularyDBDAO;
import example.com.englishnote.model.Vocabulary;

public class StudyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_english)
    TextView englishText;
    @BindView(R.id.text_means)
    TextView meansText;
    @BindView(R.id.button_visibility)
    Button visibilityBtn;

    private VocabularyDBDAO mDb;

    private final static int[] STUDY_TYPE = {R.string.title_both, R.string.title_only_english, R.string.title_only_korean};
    private List<Vocabulary> mDatas;

    private int mNowStudyTypeIdx = 0;
    private int mNowStudyIdx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);

        ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.action_study));
        getDataFromDB();
        setQuestion();
    }

    protected void getDataFromDB() {
        mDb = new VocabularyDBDAO(this);
        mDatas = mDb.selectAll();
        Collections.shuffle(mDatas);
    }

    protected void setNowStudyTypeText() {
        visibilityBtn.setText(STUDY_TYPE[mNowStudyTypeIdx]);
    }

    protected void setQuestion() {
        englishText.setText(mDatas.get(mNowStudyIdx).getEnglish());
        meansText.setText(mDatas.get(mNowStudyIdx).getMeans());
    }

    protected void setTextsVisibility() {
        englishText.setVisibility(View.VISIBLE);
        meansText.setVisibility(View.VISIBLE);

        switch (STUDY_TYPE[mNowStudyTypeIdx]) {
            case R.string.title_both:
                break;
            case R.string.title_only_english:
                meansText.setVisibility(View.INVISIBLE);
                break;
            case R.string.title_only_korean:
                englishText.setVisibility(View.INVISIBLE);
                break;
        }
    }

    protected void goToMainActivity() {
        finish();
    }

    @OnClick(R.id.button_previous)
    public void onPreviousBtnClicked() {
        if (mNowStudyIdx == 0) {
            Toast.makeText(this, getString(R.string.msg_first_question), Toast.LENGTH_SHORT).show();
            return;
        }

        mNowStudyIdx--;
        setQuestion();
    }

    @OnClick(R.id.button_next)
    public void onNextBtnClicked() {
        if (mNowStudyIdx == mDatas.size() - 1) {
            Toast.makeText(this, getString(R.string.msg_last_question), Toast.LENGTH_SHORT).show();
            return;
        }

        mNowStudyIdx++;
        setQuestion();
    }

    @OnClick(R.id.button_visibility)
    public void onVisibilityBtnClicked() {
        if (mNowStudyTypeIdx == STUDY_TYPE.length - 1) {
            mNowStudyTypeIdx = 0;
        } else {
            mNowStudyTypeIdx++;
        }

        setNowStudyTypeText();
        setTextsVisibility();
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
