package com.example.ztz.ponymusic.model.http;

import android.os.Handler;
import android.os.Message;

import com.example.ztz.ponymusic.model.IModel.ISearchModelView;
import com.example.ztz.ponymusic.model.bean.SearchBean;
import com.example.ztz.ponymusic.util.okhttp.AbstractUiCallBack;
import com.example.ztz.ponymusic.util.okhttp.OkhttpUtils;


/**
 * Created by ztz on 2017/12/30.
 */

public class SearchModel {
    private String base_url = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    private String url = "?method=baidu.ting.search.catalogSug&query=";
    private ISearchModelView iSearchModelView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SearchBean searchBean= (SearchBean)msg.obj;
            iSearchModelView.Success(searchBean);
            //Log.i("-----", "handleMessage: "+searchBean.getSong().get(1).getArtistname()+"searchmodel");
        }
    };

    public void getData(String searchtext, final ISearchModelView iSearchModelView) {
        this.iSearchModelView = iSearchModelView;
        OkhttpUtils.getInstance().asy(null, base_url + url + searchtext, new AbstractUiCallBack<SearchBean>() {

            @Override
            public void success(SearchBean searchBean) {
                Message message = new Message();
                message.obj = searchBean;
                handler.sendMessage(message);
            }

            @Override
            public void failure(Exception e) {
                iSearchModelView.Failed(e.getMessage());
            }
        });
    }

}
