package com.example.ztz.ponymusic.presenter;

import android.content.Context;

import com.example.ztz.ponymusic.model.IModel.IMusicModelView;
import com.example.ztz.ponymusic.model.bean.PlayMusicBean;
import com.example.ztz.ponymusic.model.http.MusicFileModel;
import com.example.ztz.ponymusic.view.IView.IMusicFileView;

/**
 * Created by ztz on 2018/1/9.
 */

public class MusicFilePresenter implements IMusicModelView{

    private final MusicFileModel musicFileModel;
    public MusicFilePresenter(){
        musicFileModel = new MusicFileModel();
    }
    private IMusicFileView iMusicFileView;
    public void Attach(IMusicFileView iMusicFileView) {
        this.iMusicFileView = iMusicFileView;
    }

    public void getData(String song_id) {
        musicFileModel.getFile(this,song_id);
    }

    @Override
    public void Successful(PlayMusicBean playMusicBean) {
        iMusicFileView.Successful(playMusicBean);
    }

    @Override
    public void Faile(String e) {
        iMusicFileView.Faile(e);
    }
}
