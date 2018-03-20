package example.com.englishnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.englishnote.common.IntentExtra;
import example.com.englishnote.database.VocabularyDBDAO;
import example.com.englishnote.model.Vocabulary;

public class EditVocaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.englishETV)
    EditText englishETV;
    @BindView(R.id.meansETV)
    EditText meansETV;

    VocabularyDBDAO db;
    Vocabulary preVoca;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voca);
        ButterKnife.bind(this);

        db = new VocabularyDBDAO(this);
        setData();
        if(isEdit)
            ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.editVoca));
        else
            ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.addVoca));
    }

    protected void setData() {
        int id = getIntent().getIntExtra(IntentExtra.VOCA_ID, -1);

        if(id==-1)
            return;

        isEdit = true;

        preVoca = db.selectById(id);

        englishETV.setText(preVoca.getEnglish());
        meansETV.setText(preVoca.getMeans());
    }

    protected Vocabulary collectData() {
        Vocabulary newVoca = new Vocabulary(englishETV.getText().toString(), meansETV.getText().toString(), new Date().getTime());
        if(isEdit)
            newVoca.setId(preVoca.getId());

        return newVoca;
    }

    protected void save() {
        if(isEdit)
            db.update(collectData());
        else
            db.insert(collectData());
    }

    @OnClick(R.id.okBtn)
    public void onOkBtnClicked() {
        save();
        goToVocaListActivity();
    }

    protected void goToVocaListActivity() {
        startActivity(new Intent(this, VocaListActivity.class));
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home || id == android.R.id.home) {
            goToVocaListActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        goToVocaListActivity();
    }
}
