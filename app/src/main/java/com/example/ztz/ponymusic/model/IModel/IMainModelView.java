package com.example.ztz.ponymusic.model.IModel;

import com.example.ztz.ponymusic.model.bean.MusicBean;

/**
 * Created by ztz on 2017/12/28.
 */

public interface IMainModelView {
     void Success(MusicBean musicBean);
     void Failed(String e);
}
