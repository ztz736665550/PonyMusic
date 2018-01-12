package com.example.ztz.ponymusic.view.IView;

import com.example.ztz.ponymusic.model.bean.LrcBean;

/**
 * Created by ztz on 2018/1/7.
 */

public interface ILrcView {
    void Success(LrcBean lrcBean);
    void Failed(String e);
}
