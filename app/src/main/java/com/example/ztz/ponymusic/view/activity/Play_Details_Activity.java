package com.example.ztz.ponymusic.view.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.EventNextBean;
import com.example.ztz.ponymusic.model.bean.LrcBean;
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.presenter.LrcPresenter;
import com.example.ztz.ponymusic.presenter.PlayNextPresenter;
import com.example.ztz.ponymusic.service.MusicService;
import com.example.ztz.ponymusic.view.IView.ILrcView;
import com.example.ztz.ponymusic.view.IView.IPlayNextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.greenrobot.eventbus.EventBus;
import org.sang.lrcview.LrcView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Play_Details_Activity extends AppCompatActivity implements ILrcView,IPlayNextView{

    @BindView(R.id.song_back)
    ImageView songBack;
    @BindView(R.id.tv_music_text)
    TextView tvMusicText;
    @BindView(R.id.name_text)
    TextView nameText;
    @BindView(R.id.iv_mode)
    ImageView ivMode;
    @BindView(R.id.iv_prev)
    ImageView ivPrev;
    @BindView(R.id.iv_play)
    CheckBox ivPlay;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.play_page_bg)
    SimpleDraweeView play_page_bg;
    @BindView(R.id.lrc_view)
    LrcView lrcView;
    private List<Integer> times = new ArrayList<>();//时间集合
    private TimerTask taskLrc;
    private Timer timerLrc;
    private Intent intent;
    private ServiceConnection conn;
    public static SeekBar sk;
    private static TextView music;
    private static TextView tv_total;
    private PlayMusicBean playMusicBean;
    private String show_link;
    private Intent service;
    private MusicService.MusicControl binder;
    private boolean isPause = false;
    private String path;
    private String lrcContent;
    private LrcPresenter lrcPresenter;
    private String song;
    private ArrayList<MusicBean.SongListBean> list_music;
    private int mpostion;
    private String song_id;
    private PlayNextPresenter playNextPresenter;
    private int count;
    private String mShow_link;
    private String mauthor;
    private String mtitle;
    private String msong_id;
    private String mpic_bigimage;
    private ArrayList<MusicBean.SongListBean> musiclist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__details_);
        ButterKnife.bind(this);
        immer();
        //接收条目展示页面传来的音乐数据
        isMain();
    }

    /**
     * 进行判断,如果是detailsActivity歌曲条目页面跳转过来就进行播放音乐
     * 如果是MainActivity跳转来的就保持当前的歌曲状态不重新开始播放
     * 从MinemusicFragment跳转过来的就播放本地音乐
     * 这样可以避免每次进入播放页面重新播放歌曲的问题
     */
    public void isMain() {
        if (getIntent().getStringExtra("isMain") != null) {
            initmainView();
            initDataisMain();
        } else if (getIntent().getStringExtra("isMine") != null) {
            initView();
            initMineData();
        } else if (getIntent().getStringExtra("isDetails") != null) {
            initView();
            initData();
        }
    }

    /**
     * 歌词
     * @param lrcBean
     */
    @Override
    public void Success(LrcBean lrcBean) {
        if (lrcBean.getLrcContent()!= null){
            String lrcContent = lrcBean.getLrcContent();
            //设置歌词
            lrcView.setLrc(lrcContent);
            if (MusicService.player != null){
                MediaPlayer player = MusicService.player;
                lrcView.setPlayer(player);
                lrcView.init();
            }
        }
    }

    @Override
    public void Failed(String e) {

    }

    /**
     * 以高斯模糊显示。
     *
     * @param draweeView View。
     * @param url        url.
     * @param iterations 迭代次数，越大越魔化。
     * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
     */
    public static void showUrlBlur(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
        try {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(6, blurRadius))
                    .build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initView() {
        ivPlay.setChecked(true);
        sk = findViewById(R.id.sk);
        music = findViewById(R.id.music);
        tv_total = findViewById(R.id.tv_total);

        //下一首/上一首--与playnextpresenter交互
        playNextPresenter = new PlayNextPresenter();
        playNextPresenter.Attach(this);
        //控制播放的点击事件
        onButtonClick();

        //进度条的滑动
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //当滑动条中的进度改变后,此方法被调用
            @Override
            public void onProgressChanged(SeekBar s0eekBar, int progress, boolean fromUser) {
            }

            //滑动条刚开始滑动,此方法被调用
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //当滑动条停止滑动,此方法被调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //根据拖动的进度改变音乐播放进度
                int progress = seekBar.getProgress();
                //改变播放进度
                binder.seekTo(progress);
            }
        });
        //点击进行退出播放页面
        songBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void initmainView() {
        ivPlay.setChecked(true);
        sk = findViewById(R.id.sk);
        music = findViewById(R.id.music);
        tv_total = findViewById(R.id.tv_total);

        //下一首/上一首--与playnextpresenter交互
        playNextPresenter = new PlayNextPresenter();
        playNextPresenter.Attach(this);
        //控制播放的点击事件
        //onButtonClick();
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicService.player.isPlaying()) {
                    ivPlay.setChecked(false);
                    MusicService.player.pause();
                    isPause = true;
                } else {
                    if (isPause) {
                        MusicService.player.start();
                        ivPlay.setChecked(true);
                    }
                }
            }
        });

        //进度条的滑动
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //当滑动条中的进度改变后,此方法被调用
            @Override
            public void onProgressChanged(SeekBar s0eekBar, int progress, boolean fromUser) {
            }

            //滑动条刚开始滑动,此方法被调用
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //当滑动条停止滑动,此方法被调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //根据拖动的进度改变音乐播放进度
                int progress = seekBar.getProgress();
                //改变播放进度
                binder.seekTo(progress);
            }
        });
        //点击进行退出播放页面
        songBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void initData() {
        Intent intent = getIntent();
        this.show_link = intent.getStringExtra("show_link");
        String music_name = intent.getStringExtra("music_name");
        String name = intent.getStringExtra("name");
        String music_image = intent.getStringExtra("music_image");
        String big_music_iamge = intent.getStringExtra("big_music_iamge");
        String song_id = intent.getStringExtra("song_id");
        list_music = (ArrayList<MusicBean.SongListBean>) intent.getSerializableExtra("list_music");
        musiclist.addAll(list_music);
        mpostion = intent.getIntExtra("mpostion",-1);
        count = mpostion;

        tvMusicText.setText(music_name);
        nameText.setText(name);
        showUrlBlur(play_page_bg,big_music_iamge,2,2);
        //获取歌词,请求网络,与Lrcpresenter交互
        lrcPresenter = new LrcPresenter();
        lrcPresenter.Attach(Play_Details_Activity.this);
        lrcPresenter.getData(song_id);
        //绑定服务
        /**
         * Intent service,开启服务的意图
         *  ServiceConnection conn, 连接服务的对象
         *  int flags   BIND_AUTO_CREATE绑定之后自动创建
         */
        service = new Intent(Play_Details_Activity.this, MusicService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                // 链接后返回的中间人对象
                binder = (MusicService.MusicControl) iBinder;
                //开启服务后,进入页面直接播放音乐
                start(show_link);

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        //在绑定服务之前,,,调用startService来启动一下服务,,,,使用的
        // 是这种混合启动服务的方式...因为只绑定在退出的时候同生共死,
        // 服务销毁,,,而starService方式只要不调用stopService方法,服务不销毁
        startService(service);
        Play_Details_Activity.this.bindService(service, conn, BIND_AUTO_CREATE);
    }

    public void initDataisMain() {
        Intent intent = getIntent();
        String main_music_name = intent.getStringExtra("main_music_name");
        String main_music = intent.getStringExtra("main_music");
        tvMusicText.setText(main_music_name);
        nameText.setText(main_music);
        //绑定服务
        /**
         * Intent service,开启服务的意图
         *  ServiceConnection conn, 连接服务的对象
         *  int flags   BIND_AUTO_CREATE绑定之后自动创建
         */
        service = new Intent(Play_Details_Activity.this, MusicService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                // 链接后返回的中间人对象
                binder = (MusicService.MusicControl) iBinder;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        //在绑定服务之前,,,调用startService来启动一下服务,,,,使用的
        // 是这种混合启动服务的方式...因为只绑定在退出的时候同生共死,
        // 服务销毁,,,而starService方式只要不调用stopService方法,服务不销毁
        startService(service);
        Play_Details_Activity.this.bindService(service, conn, BIND_AUTO_CREATE);
    }

    void initMineData() {
        Intent intent = getIntent();
        if (intent != null){
            path = intent.getStringExtra("path");
            String song = intent.getStringExtra("song");
            String singer = intent.getStringExtra("singer");
            tvMusicText.setText(song);
            nameText.setText(singer);
        }
        //绑定服务
        /**
         * Intent service,开启服务的意图
         *  ServiceConnection conn, 连接服务的对象
         *  int flags   BIND_AUTO_CREATE绑定之后自动创建
         */
        service = new Intent(Play_Details_Activity.this, MusicService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                // 链接后返回的中间人对象
                binder = (MusicService.MusicControl) iBinder;
                //开启服务后,进入页面直接播放音乐
                start(path);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        //在绑定服务之前,,,调用startService来启动一下服务,,,,使用的
        // 是这种混合启动服务的方式...因为只绑定在退出的时候同生共死,
        // 服务销毁,,,而starService方式只要不调用stopService方法,服务不销毁
        startService(service);
        Play_Details_Activity.this.bindService(service, conn, BIND_AUTO_CREATE);

    }

    void initNextData(final String mshowLink, String mauthor, String mtitle , String msong_id ,String mpic_bigimage) {
        tvMusicText.setText(mtitle);
        nameText.setText(mauthor);
        showUrlBlur(play_page_bg,mpic_bigimage,2,2);
        //获取歌词,请求网络,与Lrcpresenter交互
        lrcPresenter = new LrcPresenter();
        lrcPresenter.Attach(Play_Details_Activity.this);
        lrcPresenter.getData(msong_id);

        //绑定服务
        /**
         * Intent service,开启服务的意图
         *  ServiceConnection conn, 连接服务的对象
         *  int flags   BIND_AUTO_CREATE绑定之后自动创建
         */
        service = new Intent(Play_Details_Activity.this, MusicService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                // 链接后返回的中间人对象
                binder = (MusicService.MusicControl) iBinder;
                //开启服务后,进入页面直接播放音乐
                start(mshowLink);

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        //在绑定服务之前,,,调用startService来启动一下服务,,,,使用的
        // 是这种混合启动服务的方式...因为只绑定在退出的时候同生共死,
        // 服务销毁,,,而starService方式只要不调用stopService方法,服务不销毁
        startService(service);
        Play_Details_Activity.this.bindService(service, conn, BIND_AUTO_CREATE);
    }
    public void onButtonClick(){
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicService.player.isPlaying()) {
                    ivPlay.setChecked(false);
                    MusicService.player.pause();
                    isPause = true;
                } else {
                    if (isPause) {
                        MusicService.player.start();
                        ivPlay.setChecked(true);
                    }
                }
            }
        });

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (musiclist != null){
                    if (count>0){
                        count--;
                    }else{
                        Toast.makeText(Play_Details_Activity.this,"没有上一首了",Toast.LENGTH_SHORT).show();
                    }
                    song_id = musiclist.get(count).getSong_id();
                    if (song_id != null){
                        //
                        playNextPresenter.getData(song_id);
                    }
                }
            }
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musiclist != null ){
                    if (count>=0 || count<=musiclist.size()){
                        count++;
                    }else{
                        Toast.makeText(Play_Details_Activity.this,"没有下一首了",Toast.LENGTH_SHORT).show();
                    }
                    song_id = musiclist.get(count).getSong_id();
                    if (song_id != null){
                        //
                        playNextPresenter.getData(song_id);
                    }
                }
            }
        });
    }


    /**
     * 上一首/下一首
     * @param playMusicBean
     */
    @Override
    public void Success(PlayMusicBean playMusicBean) {
        if (playMusicBean != null){
            mShow_link = playMusicBean.getBitrate().getShow_link();
            mauthor = playMusicBean.getSonginfo().getAuthor();
            mtitle = playMusicBean.getSonginfo().getTitle();
            msong_id = playMusicBean.getSonginfo().getSong_id();
            mpic_bigimage = playMusicBean.getSonginfo().getPic_big();

            EventBus.getDefault().postSticky(new EventNextBean(mauthor,mtitle,mpic_bigimage));
            initView();
            initNextData(mShow_link, mauthor, mtitle, msong_id, mpic_bigimage);
        }
    }

    /**
     * 开始播放
     */
    public void start(String path) {
        binder.playInBinder(path);
    }

    /**
     * 暂停
     */
    public void pause() {
        binder.pauseInBinder();
    }

    private void immer() {
        //图片类型沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
        //解除绑定
        if (conn != null) {
            unbindService(conn);
            conn = null;
        }
        super.onDestroy();
    }

    //创建消息处理器对象
    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {

        //在主线程中处理从子线程发送过来的消息
        @Override
        public void handleMessage(Message msg) {
            //获取从子线程发送过来的音乐播放的进度
            Bundle bundle = msg.getData();

            //歌曲的总时长(毫秒)
            int duration = bundle.getInt("duration");

            //歌曲的当前进度(毫秒)
            int currentPostition = bundle.getInt("currentPosition");

            //刷新滑块的进度
            sk.setMax(duration);
            sk.setProgress(currentPostition);
            //歌曲的总时长
            int minute = duration / 1000 / 60;
            int second = duration / 1000 % 60;

            String strMinute = null;
            String strSecond = null;

            //如果歌曲的时间中的分钟小于10
            if (minute < 10) {

                //在分钟的前面加一个0
                strMinute = "0" + minute;
            } else {

                strMinute = minute + "";
            }

            //如果歌曲的时间中的秒钟小于10
            if (second < 10) {
                //在秒钟前面加一个0
                strSecond = "0" + second;
            } else {

                strSecond = second + "";
            }

            music.setText(strMinute + ":" + strSecond);

            //歌曲当前播放时长
            minute = currentPostition / 1000 / 60;
            second = currentPostition / 1000 % 60;

            //如果歌曲的时间中的分钟小于10
            if (minute < 10) {

                //在分钟的前面加一个0
                strMinute = "0" + minute;
            } else {

                strMinute = minute + "";
            }

            //如果歌曲的时间中的秒钟小于10
            if (second < 10) {

                //在秒钟前面加一个0
                strSecond = "0" + second;
            } else {

                strSecond = second + "";
            }

            tv_total.setText(strMinute + ":" + strSecond);
        }
    };

}
