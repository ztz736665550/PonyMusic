package com.example.ztz.ponymusic.view.IView;

import com.example.ztz.ponymusic.model.bean.SearchBean;

/**
 * Created by ztz on 2017/12/30.
 */

public interface ISearchView {
    void Success(SearchBean searchBean);
    void Failed(String e);
}
