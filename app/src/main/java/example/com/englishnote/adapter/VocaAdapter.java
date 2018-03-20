package example.com.englishnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.englishnote.EditVocaActivity;
import example.com.englishnote.R;
import example.com.englishnote.common.IntentExtra;
import example.com.englishnote.model.Vocabulary;

/**
 * Created by yebonkim on 2018. 3. 20..
 */

public class VocaAdapter extends RecyclerView.Adapter<VocaAdapter.ViewHolder> {

    List<Vocabulary> data;
    public static final int VIEW_TYPE_ITEM = 1;
    static Activity activity;

    public VocaAdapter(Activity activity, List<Vocabulary> data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public VocaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_voca, parent, false);
                return new ViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(VocaAdapter.ViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.englishTV)
        TextView englishTV;
        @BindView(R.id.meansTV)
        TextView meansTV;

        View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }

        protected void setData(final Vocabulary voca) {
            englishTV.setText(voca.getEnglish());
            meansTV.setText(voca.getMeans());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, EditVocaActivity.class)
                            .putExtra(IntentExtra.VOCA_ID, voca.getId()));
                    activity.finish();
                }
            });
        }
    }
}
