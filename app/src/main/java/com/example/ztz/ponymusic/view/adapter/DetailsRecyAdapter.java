package com.example.ztz.ponymusic.view.adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.presenter.MusicFilePresenter;
import com.example.ztz.ponymusic.view.IView.IMusicFileView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ztz on 2017/12/29.
 */

public class DetailsRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,IMusicFileView {
    /**
     * 创建集合,每次加载的数据都添加到集合中,这样刷新出来的数据都会显示在recyclerview中
     */
    private ArrayList<MusicBean.SongListBean> list = new ArrayList<>();
    private ArrayList<MusicBean.BillboardBean> list1 = new ArrayList<>();
    private Context context;
    private PopupWindow popupWindow;
    private ArrayList<String> groups;
    private View view;
    private String file_link;
    private String mMusictitle;


    public DetailsRecyAdapter(Context context) {
        this.context = context;
    }

    private MusicBean musicBean;
    public void addData(MusicBean musicBean, boolean needClear) {
        if (needClear) {
            //当下拉刷新时显示第一页的数据,清空集合中的所有数据
            list.clear();
        }
        this.musicBean = musicBean;
        if (musicBean.getSong_list() != null) {
            list1.add(musicBean.getBillboard());
            list.addAll(musicBean.getSong_list());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 提供对外暴露的点击事件
     */
    private MyItemOnClickListener mMyItemOnClickListener;
    public void setItemOnClickListener(MyItemOnClickListener listener) {
        mMyItemOnClickListener = listener;
    }

    /**
     * 根据条目的type判断引用的布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_details_first_recylayout, parent, false);
            return new details_first_ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_details_recylayout, parent, false);
            return new detailsViewHolder(view, mMyItemOnClickListener);
        }
    }

    /**
     * 与viewholder进行绑定
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (holder instanceof details_first_ViewHolder) {
            Glide.with(context).load(list1.get(position).getPic_s192()).placeholder(R.drawable.imageloading).into(((details_first_ViewHolder) holder).detailsFirstTextIcon);
            ((details_first_ViewHolder) holder).detailsFirstTextGexintime.setText(list1.get(position).getUpdate_date());
            ((details_first_ViewHolder) holder).detailsFirstTextBoni.setText(list1.get(position).getComment());
            ((details_first_ViewHolder) holder).detailsFirstTextRege.setText(list1.get(position).getName());

        } else if (holder instanceof detailsViewHolder) {
            Glide.with(context).load(list.get(position).getAlbum_500_500()).into(((detailsViewHolder) holder).detailsIcon);
            ((detailsViewHolder) holder).detailsName.setText(list.get(position).getTitle());
            ((detailsViewHolder) holder).detailsTitle.setText(list.get(position).getAuthor() + " - " + list.get(position).getAlbum_title());
            ((detailsViewHolder) holder).detailsItemGengduo.setOnClickListener(new View.OnClickListener() {
                /**
                 * 下载音乐
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    String song_id = list.get(position).getSong_id();
                    showPopwindow(song_id);
                    MusicFilePresenter musicFilePresenter = new MusicFilePresenter();
                    musicFilePresenter.Attach(DetailsRecyAdapter.this);
                    musicFilePresenter.getData(song_id);
                }
            });
        }
    }

    /**
     * 返回条目的长度
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 点击更多弹框
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

    }


    private void showPopwindow(final String song_id) {
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_layout, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(view);
        Button pop_down = view.findViewById(R.id.pop_down);
        Button pop_share = view.findViewById(R.id.pop_share);
        final Button pop_cancle = view.findViewById(R.id.pop_cancle);
        /**
         * 下载
         */
        pop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("------", "onClick: "+file_link);
                //创建下载任务,downloadUrl就是下载链接
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(file_link));
                //指定下载路径和下载文件名
                request.setDestinationInExternalPublicDir("/download/",mMusictitle);
                //获取下载管理器
                DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                //将下载任务加入下载队列，否则不会进行下载
                assert downloadManager != null;
                downloadManager.enqueue(request);
                popupWindow.dismiss();
            }
        });
        /**
         * 分享
         */
        pop_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
                share_intent.setType("text/plain");//设置分享内容的类型
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享至");//添加分享内容标题
                share_intent.putExtra(Intent.EXTRA_TEXT, "我用波尼音乐分享了歌曲《"+mMusictitle+"》"+file_link);//添加分享内容
                //创建分享的Dialog
                share_intent = Intent.createChooser(share_intent, "波尼分享");
                context.startActivity(share_intent);
                popupWindow.dismiss();
            }
        });
        /**
         * 取消
         */
        pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.item_details_recylayout, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);// 使其聚集
        popupWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAsDropDown(view);
    }

    /**
     * 音乐的下载
     * @param playMusicBean
     */
    @Override
    public void Successful(PlayMusicBean playMusicBean) {
        if (playMusicBean != null){
            mMusictitle = playMusicBean.getSonginfo().getTitle();
            file_link = playMusicBean.getBitrate().getFile_link();
        }
    }

    @Override
    public void Faile(String e) {

    }


    static class detailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.details_icon)
        ImageView detailsIcon;
        @BindView(R.id.details_name)
        TextView detailsName;
        @BindView(R.id.details_title)
        TextView detailsTitle;
        @BindView(R.id.details_item_gengduo)
        ImageView detailsItemGengduo;
        MyItemOnClickListener mListener;

        detailsViewHolder(View view, MyItemOnClickListener myItemOnClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.mListener = myItemOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemOnClick(view, getPosition());
            }
        }
    }

    static class details_first_ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.details_first_text_icon)
        ImageView detailsFirstTextIcon;
        @BindView(R.id.details_first_text_rege)
        TextView detailsFirstTextRege;
        @BindView(R.id.details_first_text_gexintime)
        TextView detailsFirstTextGexintime;
        @BindView(R.id.details_first_text_boni)
        TextView detailsFirstTextBoni;

        details_first_ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //定义接口
    public interface MyItemOnClickListener {
        public void onItemOnClick(View view, int postion);
    }
}
