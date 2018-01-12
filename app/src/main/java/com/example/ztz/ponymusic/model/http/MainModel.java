package com.example.ztz.ponymusic.model.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.example.ztz.ponymusic.model.IModel.IMainModelView;
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.util.okhttp.AbstractUiCallBack;
import com.example.ztz.ponymusic.util.okhttp.OkhttpUtils;

/**
 * Created by ztz on 2017/12/28.
 */

public class MainModel {
    private IMainModelView iModelView;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MusicBean musicBean = (MusicBean) msg.obj;
            if (musicBean != null){
                iModelView.Success(musicBean);
            }
        }
    };

    public void getData(final IMainModelView iModelView, int mtype, int msize, final int offset) {
        this.iModelView = iModelView;
        OkhttpUtils.getInstance().asy(null, "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type="+mtype+"&size="+msize+"&offset="+offset+"", new AbstractUiCallBack<MusicBean>() {
            @Override
            public void success(MusicBean musicBean) {
                if (musicBean!= null){
                    Message message = new Message();
                    message.obj = musicBean;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void failure(Exception e) {
                iModelView.Failed(e.getMessage());
            }
        });
    }
}
