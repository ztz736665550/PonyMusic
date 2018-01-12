package com.example.ztz.ponymusic.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.view.activity.MainActivity;
import com.example.ztz.ponymusic.view.activity.Play_Details_Activity;

import java.io.IOException;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ztz on 2018/1/1.
 */

public class MusicService extends Service {
    public static MediaPlayer player;//声明一个MediaPlayer对象
    private Timer timer;
    private String music_name;

    //绑定服务时,调用此方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicControl();
    }

    //创建播放音乐的服务
    @Override
    public void onCreate() {
        super.onCreate();
        //创建音乐播放器对象
        player = new MediaPlayer();
    }

    //销毁播放音乐服务
    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止播放音乐
        player.stop();
        //释放占用的资源
        player.release();
        //将player置为空
        player = null;
    }

    //播放音乐
    public void play(String path) {
        try {
            if(player == null){
                player = new MediaPlayer();
            }
            //重置
            player.reset();
            //加载多媒体文件
            player.setDataSource(this,Uri.parse(path));
            //用异步播放音乐
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    player.start();
                    //添加计时器
                    addTimer();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        player.stop();
    }

    //暂停播放音乐
    public void pausePlay() {
        if (player != null){
            player.pause();
        }
    }

    //继续播放音乐
    public void continuePlay() {
        player.start();
    }

    //创建一个实现音乐接口的音乐控制类
    public class MusicControl extends Binder{

        //中间人里面播放的方法
        public void playInBinder(String path) {
            play(path);
        }
        public void stopInBinder() {
            // 调用servicestopMusic
            stop();
        }

        public void pauseInBinder() {
            // 调用servicestopMusic
            pausePlay();
        }

        public void seekTo(int progress) {
            mseekto(progress);
        }

        public void continueplaym() {
            continuePlay();
        }
    }

    //设置音乐的播放位置
    public void mseekto(int progress) {
        player.seekTo(progress);
    }

    //添加计时器用于设置音乐播放器中的播放进度
    public void addTimer() {

        //如果没有创建计时器对象
        if(timer == null) {
            //创建计时器对象
            timer = new Timer();
            timer.schedule(new TimerTask() {
                         //执行计时任务
                         @Override
                         public void run() {

                         //获得歌曲总时长
                         int duration = player.getDuration();

                         //获得歌曲的当前播放进度
                         int currentPosition = player.getCurrentPosition();

                         //创建消息对象
                         Message msg = Play_Details_Activity.handler.obtainMessage();

                         //将音乐的播放进度封装至消息对象中
                         Bundle bundle = new Bundle();
                         bundle.putInt("duration", duration);
                         bundle.putInt("currentPosition", currentPosition);
                         msg.setData(bundle);

                         //将消息发送到主线程的消息队列
                         Play_Details_Activity.handler.sendMessage(msg);
                        }
                    },

            //开始计时任务后的5毫秒，第一次执行run方法，以后每500毫秒执行一次
            5, 500);
        }
    }
}
