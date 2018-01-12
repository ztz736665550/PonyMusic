package com.example.ztz.ponymusic.view.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.model.bean.Song;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ztz on 2018/1/3.
 */

public class MineRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    public MineRecyclerAdapter(Context context) {
        this.context = context;
    }

    private List<Song> musicData;
    public void addData(List<Song> musicData) {
        this.musicData = musicData;
    }

    //要在Adapter中对外暴露方法
    private MyItemOnClickListener mMyItemOnClickListener;
    public void setItemOnClickListener(MyItemOnClickListener listener){
        mMyItemOnClickListener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mine_recyclerlayout, parent, false);
        return new ItemMineViewHolder(view,mMyItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemMineViewHolder){
            ((ItemMineViewHolder) holder).mineIcon.setImageResource(R.drawable.play_page_default_cover);
            ((ItemMineViewHolder) holder).mineName.setText(musicData.get(position).song);
            ((ItemMineViewHolder) holder).mineTitle.setText(musicData.get(position).singer);
        }
    }

    @Override
    public int getItemCount() {
        return musicData == null ? 0 : musicData.size();
    }

    static class ItemMineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.mine_icon)
        ImageView mineIcon;
        @BindView(R.id.mine_name)
        TextView mineName;
        @BindView(R.id.mine_title)
        TextView mineTitle;

        MyItemOnClickListener mListener;
        ItemMineViewHolder(View view,MyItemOnClickListener myItemOnClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.mListener=myItemOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null){
                mListener.onItemOnClick(view,getPosition());
            }
        }
    }

    //定义接口
    public interface MyItemOnClickListener {
        public void onItemOnClick(View view,int postion);
    }
}
