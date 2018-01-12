package com.example.ztz.ponymusic.view.IView;

import com.example.ztz.ponymusic.model.bean.MusicBean;

/**
 * Created by ztz on 2017/12/28.
 */

public interface IMainView {
    void Success(MusicBean musicBean,boolean needClear);
    void Failed(String e);
}
