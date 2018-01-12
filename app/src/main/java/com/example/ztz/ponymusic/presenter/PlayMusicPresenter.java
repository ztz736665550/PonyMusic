package com.example.ztz.ponymusic.presenter;

import com.example.ztz.ponymusic.model.IModel.ISearchModelView;
import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.model.bean.SearchBean;
import com.example.ztz.ponymusic.model.http.PlayMusicModel;
import com.example.ztz.ponymusic.view.IView.IPlayMusicView;
import com.example.ztz.ponymusic.view.activity.DetailsActivity;

/**
 * Created by ztz on 2018/1/1.
 */

public class PlayMusicPresenter extends BasePresenter implements IPlayMusicView{

    private final PlayMusicModel playMusicModel;
    public PlayMusicPresenter(){
        this.playMusicModel = new PlayMusicModel();
    }

    private IPlayMusicView iPlayMusicView;
    public void onAttach(IPlayMusicView iPlayMusicView) {
        this.iPlayMusicView = iPlayMusicView;
    }

    public void getData(String sond_id) {
        playMusicModel.getData(sond_id,this);
    }

    @Override
    public void Success(PlayMusicBean playMusicBean) {
        iPlayMusicView.Success(playMusicBean);
    }

    @Override
    public void Failed(String e) {
        iPlayMusicView.Failed(e);
    }
}
