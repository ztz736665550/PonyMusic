package com.example.ztz.ponymusic.model.http;

import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.presenter.MusicFilePresenter;
import com.example.ztz.ponymusic.util.okhttp.AbstractUiCallBack;
import com.example.ztz.ponymusic.util.okhttp.OkhttpUtils;

/**
 * Created by ztz on 2018/1/9.
 */

public class MusicFileModel {
    private String baseUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid=";
    public void getFile(final MusicFilePresenter musicFilePresenter, String song_id) {
        OkhttpUtils.getInstance().asy(null, baseUrl + song_id, new AbstractUiCallBack<PlayMusicBean>() {
            @Override
            public void success(PlayMusicBean playMusicBean) {
                musicFilePresenter.Successful(playMusicBean);
            }

            @Override
            public void failure(Exception e) {
                musicFilePresenter.Faile(e.getMessage());
            }
        });
    }
}
