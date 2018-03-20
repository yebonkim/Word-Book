package example.com.englishnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        startActivity(new Intent(this, VocaListActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
