package com.example.ztz.ponymusic.presenter;

import android.util.Log;

import com.example.ztz.ponymusic.model.IModel.ISearchModelView;
import com.example.ztz.ponymusic.model.bean.SearchBean;
import com.example.ztz.ponymusic.model.http.SearchModel;
import com.example.ztz.ponymusic.view.IView.ISearchView;
import com.example.ztz.ponymusic.view.activity.SearchActivity;

/**
 * Created by ztz on 2017/12/30.
 */

public class SearchPresenter extends BasePresenter implements ISearchModelView{

    private ISearchView iSearchView;
    private final SearchModel searchModel;

    public void Attach(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
    }

    //注意：重载构造方法，去new model
    public SearchPresenter(){
        searchModel = new SearchModel();
    }

    public void getData(String searchtext) {

        searchModel.getData(searchtext,this);
    }

    /**
     *请求得到的数据
     * @param searchBean
     */
    @Override
    public void Success(SearchBean searchBean) {
        iSearchView.Success(searchBean);
    }

    @Override
    public void Failed(String e) {
        iSearchView.Failed(e);
    }
}
