package example.com.englishnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.englishnote.adapter.VocaAdapter;
import example.com.englishnote.common.IntentExtra;
import example.com.englishnote.database.VocabularyDBDAO;
import example.com.englishnote.model.Vocabulary;

public class VocaListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_voca)
    RecyclerView vocaList;

    private VocaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_list);
        ButterKnife.bind(this);

        ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.title_voca_list));
        setRecyclerView();
    }

    private void setRecyclerView() {
        vocaList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VocaAdapter(getDataFromDb());
        vocaList.setAdapter(mAdapter);
    }

    protected List<Vocabulary> getDataFromDb() {
        VocabularyDBDAO db = new VocabularyDBDAO(this);
        return db.selectAll();
    }

    private void goToMainActivity() {
        finish();
    }

    @OnClick(R.id.button_add)
    public void onAddBtnClicked() {
        startActivity(new Intent(this, EditVocaActivity.class)
                .putExtra(IntentExtra.VOCA_ID, IntentExtra.VOCA_NULL));
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
