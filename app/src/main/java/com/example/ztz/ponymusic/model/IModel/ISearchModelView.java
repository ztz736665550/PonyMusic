package com.example.ztz.ponymusic.model.IModel;

import com.example.ztz.ponymusic.model.bean.SearchBean;

/**
 * Created by ztz on 2017/12/30.
 */

public interface ISearchModelView {
    void Success(SearchBean searchBean);
    void Failed(String e);
}
