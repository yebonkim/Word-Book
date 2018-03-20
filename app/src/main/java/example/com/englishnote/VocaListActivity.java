package example.com.englishnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.englishnote.adapter.VocaAdapter;
import example.com.englishnote.common.IntentExtra;
import example.com.englishnote.database.VocabularyDBDAO;
import example.com.englishnote.model.Vocabulary;

public class VocaListActivity extends AppCompatActivity {

    @BindView(R.id.vocaRV)
    RecyclerView vocaRV;

    VocaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_list);
        ButterKnife.bind(this);

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

    @OnClick(R.id.addBtn)
    public void onAddBtnClicked() {
        startActivity(new Intent(this, EditVocaActivity.class)
                .putExtra(IntentExtra.VOCA_ID, IntentExtra.VOCA_NULL));
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
