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
    @BindView(R.id.vocaRV)
    RecyclerView vocaRV;

    VocaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_list);
        ButterKnife.bind(this);

        ActionBarManager.initBackArrowActionbar(this, toolbar, getString(R.string.vocaList));
        setRecyclerView();
    }

    protected void setRecyclerView() {
        vocaRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VocaAdapter(this, getDataFromServer());
        vocaRV.setAdapter(adapter);
    }

    protected List<Vocabulary> getDataFromServer() {
        VocabularyDBDAO db = new VocabularyDBDAO(this);
        return db.selectAll();
    }

    protected void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.addBtn)
    public void onAddBtnClicked() {
        startActivity(new Intent(this, EditVocaActivity.class)
                .putExtra(IntentExtra.VOCA_ID, IntentExtra.VOCA_NULL));
        finish();
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
