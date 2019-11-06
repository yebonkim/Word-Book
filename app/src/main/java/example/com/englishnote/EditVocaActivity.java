package example.com.englishnote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    @BindView(R.id.edit_english)
    EditText englishEdit;
    @BindView(R.id.edit_means)
    EditText meansEdit;

    private VocabularyDBDAO mDb;

    private int mVocaId;
    private boolean mIsNewVoca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voca);
        ButterKnife.bind(this);

        mVocaId = getIntent().getIntExtra(IntentExtra.VOCA_ID, IntentExtra.VOCA_NULL);
        mIsNewVoca = (mVocaId == IntentExtra.VOCA_NULL);
        mDb = new VocabularyDBDAO(this);

        setData(mDb.selectById(mVocaId));

        if (mIsNewVoca) {
            ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.action_add_voca));
        } else {
            ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.action_edit_voca));
        }
    }

    protected void setData(@Nullable Vocabulary previousVoca) {
        if (previousVoca == null) {
            return;
        }

        englishEdit.setText(previousVoca.getEnglish());
        meansEdit.setText(previousVoca.getMeans());
    }

    @NonNull
    protected Vocabulary collectData() {
        Vocabulary newVoca = new Vocabulary(englishEdit.getText().toString(),
                meansEdit.getText().toString(), new Date().getTime());

        if (!mIsNewVoca) {
            newVoca.setId(mVocaId);
        }

        return newVoca;
    }

    private void save() {
        if(!mIsNewVoca) {
            mDb.update(collectData());
        } else {
            mDb.insert(collectData());
        }
    }

    @OnClick(R.id.button_confirm)
    public void onConfirmBtnClicked() {
        save();
        goToVocaListActivity();
    }

    private void goToVocaListActivity() {
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
