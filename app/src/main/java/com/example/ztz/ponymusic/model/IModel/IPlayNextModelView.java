package com.example.ztz.ponymusic.model.IModel;

import com.example.ztz.ponymusic.model.bean.PlayMusicBean;

/**
 * Created by ztz on 2018/1/10.
 */

public interface IPlayNextModelView {
    void Success(PlayMusicBean playMusicBean);
    void Failed(String e);
}
