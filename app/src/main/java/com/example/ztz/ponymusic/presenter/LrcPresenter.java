package com.example.ztz.ponymusic.presenter;

import android.content.Context;

import com.example.ztz.ponymusic.model.IModel.ILrcModelView;
import com.example.ztz.ponymusic.model.bean.LrcBean;
import com.example.ztz.ponymusic.model.http.LrcModel;
import com.example.ztz.ponymusic.view.IView.ILrcView;
import com.example.ztz.ponymusic.view.activity.DetailsActivity;
import com.example.ztz.ponymusic.view.activity.Play_Details_Activity;

import org.sang.lrcview.LrcView;

/**
 * Created by ztz on 2018/1/7.
 */

public class LrcPresenter implements ILrcModelView {

    private final LrcModel lrcModel;
    public LrcPresenter(){
        lrcModel = new LrcModel();
    }

    private ILrcView lrcView;
    public void Attach(ILrcView lrcView) {
        this.lrcView =lrcView;
    }

    public void getData(String song_id) {
        lrcModel.getData(this,song_id);
    }

    @Override
    public void Success(LrcBean lrcBean) {
        lrcView.Success(lrcBean);
    }

    @Override
    public void Failed(String e) {
        lrcView.Failed(e);
    }
}
