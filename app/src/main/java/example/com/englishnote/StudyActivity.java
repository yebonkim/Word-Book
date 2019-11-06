package example.com.englishnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.englishTV)
    TextView englishTV;
    @BindView(R.id.meansTV)
    TextView meansTV;
    @BindView(R.id.visibilityBtn)
    Button visibilityBtn;

    VocabularyDBDAO db;

    int[] studyType;
    int nowStudyTypeIdx = 0;
    int nowStudyIdx = 0;
    List<Vocabulary> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);

        ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.action_study));
        setStudyTypeStrRes();
        getDataFromDB();
        setQuestion();
    }

    protected void setStudyTypeStrRes() {
        studyType = new int[3];
        studyType[0] = R.string.both;
        studyType[1] = R.string.onlyEnglish;
        studyType[2] = R.string.onlyKorean;
    }

    protected void getDataFromDB() {
        db = new VocabularyDBDAO(this);
        data = db.selectAll();
        Collections.shuffle(data);
    }

    protected void setNowStudyTypeText() {
        visibilityBtn.setText(studyType[nowStudyTypeIdx]);
    }

    protected void setQuestion() {
        englishTV.setText(data.get(nowStudyIdx).getEnglish());
        meansTV.setText(data.get(nowStudyIdx).getMeans());
    }

    protected void setTVsVisibility() {
        englishTV.setVisibility(View.VISIBLE);
        meansTV.setVisibility(View.VISIBLE);

        switch(studyType[nowStudyTypeIdx]) {
            case R.string.both:
                break;
            case R.string.onlyEnglish:
                meansTV.setVisibility(View.INVISIBLE);
                break;
            case R.string.onlyKorean:
                englishTV.setVisibility(View.INVISIBLE);
                break;
        }
    }

    protected void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.preBtn)
    public void onPreBtnClicked() {
        if(nowStudyIdx ==0) {
            Toast.makeText(this, getString(R.string.firstQuestionWarning), Toast.LENGTH_SHORT).show();
            return;
        }

        nowStudyIdx--;
        setQuestion();
    }

    @OnClick(R.id.nextBtn)
    public void onNextBtnClicked() {
        if(nowStudyIdx ==data.size()-1) {
            Toast.makeText(this, getString(R.string.lastQuestionWarning), Toast.LENGTH_SHORT).show();
            return;
        }

        nowStudyIdx++;
        setQuestion();

    }

    @OnClick(R.id.visibilityBtn)
    public void onVisibilityBtnClicked() {
        if(nowStudyTypeIdx==studyType.length-1)
            nowStudyTypeIdx = 0;
        else
            nowStudyTypeIdx++;

        setNowStudyTypeText();
        setTVsVisibility();
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
