package com.example.ztz.ponymusic.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.custom.SlideMenu;
import com.example.ztz.ponymusic.model.bean.EventMineBean;
import com.example.ztz.ponymusic.model.bean.EventNextBean;
import com.example.ztz.ponymusic.model.bean.PlayEventMusicBean;
import com.example.ztz.ponymusic.service.MusicService;
import com.example.ztz.ponymusic.view.adapter.MainFragmentViewPager;
import com.example.ztz.ponymusic.view.fragment.LineMusicFragment;
import com.example.ztz.ponymusic.view.fragment.MineMusicFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.Main_cela)
    ImageView MainCela;
    @BindView(R.id.main_sousuo)
    ImageView mainSousuo;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.music_icon)
    ImageView musicIcon;
    @BindView(R.id.main_musicname)
    TextView mainMusicname;
    @BindView(R.id.main_name)
    TextView mainName;
    @BindView(R.id.main_zanting)
    CheckBox mainZanting;
    @BindView(R.id.main_xiayishou)
    ImageView mainXiayishou;
    @BindView(R.id.main_linear)
    LinearLayout main_linear;
    @BindView(R.id.minemusic)
    RadioButton minemusic;
    @BindView(R.id.zaixianmusic)
    RadioButton zaixianmusic;
    @BindView(R.id.SlideMenu)
    com.example.ztz.ponymusic.custom.SlideMenu SlideMenu;
    @BindView(R.id.gongengsehzhi)
    TextView gongengsehzhi;
    @BindView(R.id.yejianmoshi)
    TextView yejianmoshi;
    @BindView(R.id.dingshibofang)
    TextView dingshibofang;
    @BindView(R.id.tuichuyingyong)
    TextView tuichuyingyong;
    @BindView(R.id.guanyuboni)
    TextView guanyuboni;
    @BindView(R.id.main_tvcity)
    TextView mainTvcity;
    @BindView(R.id.main_img_icon)
    ImageView mainImgIcon;
    @BindView(R.id.main_tv_wendu)
    TextView mainTvWendu;
    private RadioGroup mainRadioGroup;
    private boolean isPause = false;
    private SharedPreferences sharedPreferences;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用base类时,应该把butterknife的ButterKnife.bind(this);放在base类
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //sharedPreferences存值,改变主题的状态
        sharedPreferences = getSharedPreferences("isNight", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        mainRadioGroup = findViewById(R.id.main_radiogroup);
        mainSousuo.setOnClickListener(this);
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new MineMusicFragment());
        list.add(new LineMusicFragment());
        MainFragmentViewPager mainFragmentViewPager = new MainFragmentViewPager(list, getSupportFragmentManager());
        mainViewpager.setAdapter(mainFragmentViewPager);
        mainViewpager.setOnPageChangeListener(this);
        //radiogroup的点击事件
        mainRadioGroup.setOnCheckedChangeListener(this);
        //侧拉按钮
        MainCela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SlideMenu.switchMenu();
            }
        });
        //功能设置
        gongengsehzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "功能", Toast.LENGTH_SHORT).show();
            }
        });
        //夜间模式
        yejianmoshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用sharedPreferences存值,来判断切换夜间模式
                boolean mode = sharedPreferences.getBoolean("Mode", true);
                if (mode) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);//切换夜间模式
                    editor.putBoolean("Mode", false);
                    editor.apply();
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);//切换白天模式
                    editor.putBoolean("Mode", true);
                    editor.apply();
                }
                recreate();//重新启动当前activity
            }
        });
        //定时播放
        dingshibofang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "定时播放", Toast.LENGTH_SHORT).show();
            }
        });
        //退出应用
        tuichuyingyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtyContainer.getInstance().finishAllActivity();
            }
        });
        //关于波尼
        guanyuboni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "关于波尼", Toast.LENGTH_SHORT).show();
            }
        });
        //下方播放条--点击跳转到播放页面
        musicIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Play_Details_Activity.class);
                intent.putExtra("isMain", "isMain");
                String main_music_name = mainMusicname.getText().toString();
                String main_music = mainName.getText().toString();
                if (main_music_name!= null && main_music != null){
                    intent.putExtra("main_music_name",main_music_name);
                    intent.putExtra("main_music",main_music);
                    startActivity(intent);
                }

            }
        });

        mainZanting.setChecked(true);
        //暂停/继续
        mainZanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicService.player != null) {
                    if (MusicService.player.isPlaying()) {
                        mainZanting.setChecked(false);
                        MusicService.player.pause();
                        isPause = true;
                    } else {
                        if (isPause) {
                            MusicService.player.start();
                            mainZanting.setChecked(true);
                        }
                    }
                }
            }
        });
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(PlayEventMusicBean playEventMusicBean) {
        Glide.with(this)
                .load(playEventMusicBean.getMusic_image())
                .placeholder(R.drawable.imageloading)
                .into(musicIcon);
        mainMusicname.setText(playEventMusicBean.getMusic_name());
        mainName.setText(playEventMusicBean.getName());
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(EventMineBean eventMineBean) {
        mainMusicname.setText(eventMineBean.getSong());
        mainName.setText(eventMineBean.getSonger());
        musicIcon.setImageResource(R.drawable.imageloading);
    }
    @Subscribe(sticky = true)
    public void onMessageEvent(EventNextBean eventNextBean) {
        mainMusicname.setText(eventNextBean.getTitle());
        mainName.setText(eventNextBean.getAuther());
        Glide.with(this)
                .load(eventNextBean.getBig_iamge())
                .placeholder(R.drawable.imageloading)
                .into(musicIcon);
    }

    @Override
    protected void initData() {
        //注册EventBus
        EventBus.getDefault().register(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    /**
     * viewpager的滚动监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //viewpager页面滑动,实现按钮跟随滑动
        mainRadioGroup.check(mainRadioGroup.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //按钮
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.minemusic:
                mainViewpager.setCurrentItem(0, true);
                break;
            case R.id.zaixianmusic:
                mainViewpager.setCurrentItem(1, true);
                break;
            default:
                mainViewpager.setCurrentItem(1, true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
