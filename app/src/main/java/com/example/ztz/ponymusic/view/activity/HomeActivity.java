package com.example.ztz.ponymusic.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ztz.ponymusic.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private final static int COUNT = 1;
    private TextView countDown;
    private Timer timer;
    private Handler handler = new Handler(){
        int num = 3;
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case COUNT:
                    countDown.setText(String.valueOf(num)+"s后跳转");
                    num--;
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //点击按钮进行直接跳转
        countDown.setOnClickListener(this);
        immer();
    }

    @Override
    int setContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    void initView() {
        countDown =findViewById(R.id.count_down);
        timer = new Timer();
        final long end = System.currentTimeMillis() + 1000*3;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(COUNT);
            }
        }, 0, 1000);

        //这里的schedule的第二个参数意义是到了这个时间尽快运行run里面的方法
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                timer.cancel();
            }
        }, new Date(end));
    }

    @Override
    void initData() {

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
        //将timer进行取消
        finish();
        timer.cancel();
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
}
