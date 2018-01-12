package com.example.ztz.ponymusic.presenter;

import com.example.ztz.ponymusic.model.IModel.IPlayNextModelView;
import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.model.http.PlayNextModel;
import com.example.ztz.ponymusic.view.IView.IPlayNextView;
import com.example.ztz.ponymusic.view.activity.Play_Details_Activity;

/**
 * Created by ztz on 2018/1/10.
 */

public class PlayNextPresenter implements IPlayNextModelView{


    private final PlayNextModel playNextModel;
    public PlayNextPresenter(){
        playNextModel = new PlayNextModel();
    }

    private IPlayNextView iPlayNextView;
    public void Attach(IPlayNextView iPlayNextView) {
        this.iPlayNextView = iPlayNextView;
    }

    public void getData(String song_id) {
        playNextModel.getData(this,song_id);
    }

    @Override
    public void Success(PlayMusicBean playMusicBean) {
        iPlayNextView.Success(playMusicBean);
    }

    @Override
    public void Failed(String e) {
        iPlayNextView.Failed(e);
    }
}
