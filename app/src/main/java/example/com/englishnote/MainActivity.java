package example.com.englishnote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_study)
    public void onClickStudyBtn() {
//        if (new VocabularyDBDAO(this).getCount() == 0) {
//            Toast.makeText(this, getString(R.string.error_limit_1_word), Toast.LENGTH_SHORT).show();
//            return;
//        }
//        startActivity(new Intent(this, StudyActivity.class));
    }

    @OnClick(R.id.button_test)
    public void onClickTestBtn() {
//        if (new VocabularyDBDAO(this).getCount() < 4) {
//            Toast.makeText(this, getString(R.string.error_limit_4_word), Toast.LENGTH_SHORT).show();
//            return;
//        }
//        startActivity(new Intent(this, TestActivity.class));
    }

    @OnClick(R.id.button_register)
    public void onClickRegisterBtn() {
        startActivity(new Intent(this, VocaListActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
