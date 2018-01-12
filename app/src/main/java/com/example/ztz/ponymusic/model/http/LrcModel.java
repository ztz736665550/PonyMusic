package com.example.ztz.ponymusic.model.http;

import com.example.ztz.ponymusic.model.bean.LrcBean;
import com.example.ztz.ponymusic.presenter.LrcPresenter;
import com.example.ztz.ponymusic.util.okhttp.AbstractUiCallBack;
import com.example.ztz.ponymusic.util.okhttp.OkhttpUtils;

/**
 * Created by ztz on 2018/1/7.
 */

public class LrcModel {
   private String baseUrl = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.lry&songid=";
    public void getData(final LrcPresenter lrcPresenter, String song_id) {
        OkhttpUtils.getInstance().asy(null, baseUrl + song_id, new AbstractUiCallBack<LrcBean>() {

            @Override
            public void success(LrcBean lrcBean) {
                lrcPresenter.Success(lrcBean);
            }

            @Override
            public void failure(Exception e) {
                lrcPresenter.Failed(e.getMessage());
            }
        });
    }
}
