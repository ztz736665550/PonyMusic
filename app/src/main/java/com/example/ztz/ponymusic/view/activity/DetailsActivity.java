package com.example.ztz.ponymusic.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.EventLrcBean;
import com.example.ztz.ponymusic.model.bean.EventmFileLinkBean;
import com.example.ztz.ponymusic.model.bean.LrcBean;
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.model.bean.PlayEventMusicBean;
import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.presenter.LrcPresenter;
import com.example.ztz.ponymusic.presenter.MainPresenter;
import com.example.ztz.ponymusic.presenter.PlayMusicPresenter;
import com.example.ztz.ponymusic.view.IView.ILrcView;
import com.example.ztz.ponymusic.view.IView.IMainView;
import com.example.ztz.ponymusic.view.IView.IPlayMusicView;
import com.example.ztz.ponymusic.view.adapter.DetailsRecyAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

public class DetailsActivity extends BaseActivity implements IMainView, IPlayMusicView {

    @BindView(R.id.details_recyclerview)
    RecyclerView detailsRecyclerview;
    @BindView(R.id.details_img_back)
    ImageView detailsImgBack;
    @BindView(R.id.details_spring_view)
    SpringView details_spring_view;
    private MainPresenter mainPresenter;
    private DetailsRecyAdapter detailsRecyAdapter;
    private PlayMusicPresenter playMusicPresenter;
    private MusicBean musicBean;
    private int postion;
    private boolean isLoading = false;
    private boolean needClear = false;
    private LinearLayoutManager linearLayoutManager;
    private String song_id;
    private LrcPresenter lrcPresenter;
    private String mfile_link;
    private ArrayList<MusicBean.SongListBean> list_music  = new ArrayList<>();//创建音乐的集合,
    // 把每次上拉加载得到的数据进行存放到集合中,因为适配器把每次请求到的数据进行放到了集合,那么如果在这里不
    // 放到集合会引起条目的song_id重叠,播放歌曲条目错乱
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                musicBean = (MusicBean) msg.obj;
                //添加到集合
                if (musicBean.getSong_list() != null){
                    list_music.addAll(musicBean.getSong_list());
                }
            }
        }
    };
    private int postion1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int setContentViewId() {
        return R.layout.activity_details;
    }

    @Override
    void initView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detailsRecyclerview.setLayoutManager(linearLayoutManager);
        detailsRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        detailsRecyAdapter = new DetailsRecyAdapter(this);
        detailsRecyclerview.setAdapter(detailsRecyAdapter);
        details_spring_view.setHeader(new DefaultHeader(this));
        details_spring_view.setFooter(new DefaultFooter(this));
        details_spring_view.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                needClear = true;
                mainPresenter.refreshData(postion);
            }

            @Override
            public void onLoadmore() {
                mainPresenter.loadMore(postion);
            }
        });

        //返回键
        detailsImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //点击进行请求音乐数据
        detailsRecyAdapter.setItemOnClickListener(new DetailsRecyAdapter.MyItemOnClickListener() {

            @Override
            public void onItemOnClick(View view, int postion) {
                //点击条目拿到条目的song_id，根据id去获取音乐
                postion1 = postion;
                song_id = list_music.get(postion).getSong_id();
                playMusicPresenter.getData(song_id);

            }
        });
    }

    /**
     * 播放音乐的数据
     *
     * @param playMusicBean
     */
    @Override
    public void Success(PlayMusicBean playMusicBean) {
        if (playMusicBean != null) {
            EventBus.getDefault().postSticky(new EventmFileLinkBean(mfile_link));
            String music_name = playMusicBean.getSonginfo().getTitle();
            String name = playMusicBean.getSonginfo().getAuthor();
            String big_music_iamge = playMusicBean.getSonginfo().getPic_radio();
            String show_link = playMusicBean.getBitrate().getShow_link();
            String music_image = playMusicBean.getSonginfo().getPic_huge();
            EventBus.getDefault().postSticky(new PlayEventMusicBean(show_link, music_name, name, music_image, big_music_iamge,song_id));
            //拿到音乐的地址
            Intent intent = new Intent(DetailsActivity.this, Play_Details_Activity.class);//实例化一个Intent对象
            intent.putExtra("isDetails","isDetails");
            intent.putExtra("music_name",music_name);
            intent.putExtra("name",name);
            intent.putExtra("big_music_iamge",big_music_iamge);
            intent.putExtra("show_link",show_link);
            intent.putExtra("music_image",music_image);
            intent.putExtra("music_name",music_name);
            intent.putExtra("song_id",song_id);
            intent.putExtra("list_music",list_music);
            intent.putExtra("mpostion",postion1);
            startActivity(intent);
        }
    }

    @Override
    void initData() {
        Intent intent = getIntent();
        postion = intent.getIntExtra("pos", -1);
        //判断条目的下标，根据下标来确定请求的路径
        if (postion == 1) {
            postion = 1;
        } else if (postion == 2) {
            postion = 2;
        } else if (postion == 4) {
            postion = 11;
        } else if (postion == 5) {
            postion = 12;
        } else if (postion == 6) {
            postion = 16;
        } else if (postion == 7) {
            postion = 21;
        } else if (postion == 8) {
            postion = 22;
        } else if (postion == 9) {
            postion = 23;
        } else if (postion == 11) {
            postion = 24;
        } else if (postion == 12) {
            postion = 25;
        }
        mainPresenter = new MainPresenter();
        playMusicPresenter = new PlayMusicPresenter();
        mainPresenter.onAttach(this);
        playMusicPresenter.onAttach(this);
        mainPresenter.getData(postion);

    }

    @Override
    public void Success(MusicBean musicBean, boolean needClear) {
        if (musicBean.getSong_list() != null) {
            detailsRecyAdapter.addData(musicBean,needClear);
            //把下载音乐的路径传过去
            detailsRecyAdapter.notifyDataSetChanged();
            details_spring_view.onFinishFreshAndLoad();//停止刷新

            Message message = new Message();
            message.what = 2;
            message.obj = musicBean;
            handler.sendMessage(message);
        }else if (musicBean.getSong_list() == null){
            details_spring_view.onFinishFreshAndLoad();//停止刷新
            Toast.makeText(this,"没有更多了",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Failed(String e) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.detach();
    }

}
