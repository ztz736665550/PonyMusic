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
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.util.okhttp.AbstractUiCallBack;
import com.example.ztz.ponymusic.util.okhttp.OkhttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ztz on 2017/12/29.
 */

public class LineMusicRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private String baseUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?fromat=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type=";
    private String url = "&size=10&offset=0";

    public LineMusicRecyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 3 || position == 10){
            return 0;
        }else{
            return 1;
        }
    }

    private MyItemOnClickListener mMyItemOnClickListener;
    public void setItemOnClickListener(MyItemOnClickListener listener){
        mMyItemOnClickListener=listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0 ) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_linerecy, parent, false);
            return new item_line_ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_linechildrecy, parent, false);
            return new itemchildViewHolder(view,mMyItemOnClickListener);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof item_line_ViewHolder){
            if (position == 0){
                ((item_line_ViewHolder) holder).itemLinerecyText.setText("主打榜单");
            }else if (position == 3){
                ((item_line_ViewHolder) holder).itemLinerecyText.setText("分类榜单");
            }else if (position == 10){
                ((item_line_ViewHolder) holder).itemLinerecyText.setText("媒体榜单");
            }
        }else if (holder instanceof itemchildViewHolder){
            if (position == 1){
                OkhttpUtils.getInstance().asy(null, baseUrl +"1" + url, new AbstractUiCallBack<MusicBean>() {
                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 2){
                OkhttpUtils.getInstance().asy(null, baseUrl + "2" + url, new AbstractUiCallBack<MusicBean>() {
                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 4){
                OkhttpUtils.getInstance().asy(null, baseUrl + "11" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 5){
                OkhttpUtils.getInstance().asy(null, baseUrl + "12" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 6 ){

                OkhttpUtils.getInstance().asy(null, baseUrl + "16" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 7){
                OkhttpUtils.getInstance().asy(null, baseUrl + "21" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        if (musicBean != null){
                            List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                            MusicBean.BillboardBean billboard = musicBean.getBillboard();
                            Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                            ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                            ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                            ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                        }
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 8){
                OkhttpUtils.getInstance().asy(null, baseUrl + "22" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 9){
                OkhttpUtils.getInstance().asy(null, baseUrl + "23" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 11){
                OkhttpUtils.getInstance().asy(null, baseUrl + "24" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }else if (position == 12){
                OkhttpUtils.getInstance().asy(null, baseUrl + "25" + url, new AbstractUiCallBack<MusicBean>() {

                    @Override
                    public void success(MusicBean musicBean) {
                        List<MusicBean.SongListBean> song_list = musicBean.getSong_list();
                        MusicBean.BillboardBean billboard = musicBean.getBillboard();
                        Glide.with(context).load(billboard.getPic_s260()).placeholder(R.drawable.imageloading).into(((itemchildViewHolder) holder).itemChildimage);
                        ((itemchildViewHolder) holder).itemChildmusictext01.setText(song_list.get(0).getTitle()+" - "+song_list.get(0).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext02.setText(song_list.get(1).getTitle()+" - "+song_list.get(1).getAuthor());
                        ((itemchildViewHolder) holder).itemChildmusictext03.setText(song_list.get(2).getTitle()+" - "+song_list.get(2).getAuthor());
                    }

                    @Override
                    public void failure(Exception e) {
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return 13;
    }

    static class item_line_ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_linerecy_text)
        TextView itemLinerecyText;

        item_line_ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class itemchildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_childimage)
        ImageView itemChildimage;
        @BindView(R.id.item_childmusictext01)
        TextView itemChildmusictext01;
        @BindView(R.id.item_childmusictext02)
        TextView itemChildmusictext02;
        @BindView(R.id.item_childmusictext03)
        TextView itemChildmusictext03;

        MyItemOnClickListener mListener;
        itemchildViewHolder(View view,MyItemOnClickListener myItemOnClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.mListener=myItemOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemOnClick(view,getPosition());
        }
    }

    //1、定义接口
    public interface MyItemOnClickListener {
        public void onItemOnClick(View view,int postion);
    }
}
