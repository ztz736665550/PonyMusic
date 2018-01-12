package com.example.ztz.ponymusic.model.http;

import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.presenter.PlayMusicPresenter;
import com.example.ztz.ponymusic.util.okhttp.AbstractUiCallBack;
import com.example.ztz.ponymusic.util.okhttp.OkhttpUtils;


/**
 * Created by ztz on 2018/1/1.
 */

public class PlayMusicModel {

    private String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid=";
    public void getData(String song_id, final PlayMusicPresenter iPlayMusicModelView) {
        OkhttpUtils.getInstance().asy(null, url+song_id, new AbstractUiCallBack<PlayMusicBean>() {

            @Override
            public void success(PlayMusicBean playMusicBean) {
                iPlayMusicModelView.Success(playMusicBean);
            }

            @Override
            public void failure(Exception e) {
                iPlayMusicModelView.Failed(e.getMessage());
            }
        });
    }
}
