package com.example.ztz.ponymusic.model.IModel;

import com.example.ztz.ponymusic.model.bean.PlayMusicBean;

/**
 * Created by ztz on 2018/1/9.
 */

public interface IMusicModelView {
    void Successful(PlayMusicBean playMusicBean);
    void Faile(String e);
}
