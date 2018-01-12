package com.example.ztz.ponymusic.model.IModel;

import com.example.ztz.ponymusic.model.bean.LrcBean;

/**
 * Created by ztz on 2018/1/7.
 */

public interface ILrcModelView {
    void Success(LrcBean lrcBean);
    void Failed(String e);
}
