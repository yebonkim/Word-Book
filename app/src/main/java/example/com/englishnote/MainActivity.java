package example.com.englishnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @OnClick(R.id.studyBtn)
    public void onClickStudyBtn() {
        startActivity(new Intent(this, StudyActivity.class));
        finish();
    }

    @OnClick(R.id.testBtn)
    public void onClickTestBtn() {
        startActivity(new Intent(this, TestActivity.class));
        finish();
    }

    @OnClick(R.id.registerBtn)
    public void onClickRegisterBtn() {
        startActivity(new Intent(this, WordListActivity.class));
        finish();
    }



}
