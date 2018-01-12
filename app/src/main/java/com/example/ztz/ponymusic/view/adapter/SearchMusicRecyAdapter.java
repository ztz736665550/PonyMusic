package com.example.ztz.ponymusic.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.SearchBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ztz on 2017/12/30.
 */

public class SearchMusicRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public SearchMusicRecyAdapter(Context context) {
        this.context = context;
    }

    private List<SearchBean.SongBean> list;

    public void addData(List<SearchBean.SongBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_recylayout, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  SearchViewHolder){
            ((SearchViewHolder) holder).searchName.setText(list.get(position).getSongname());
            ((SearchViewHolder) holder).searchTitle.setText(list.get(position).getArtistname());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.search_name)
        TextView searchName;
        @BindView(R.id.search_title)
        TextView searchTitle;

        SearchViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
