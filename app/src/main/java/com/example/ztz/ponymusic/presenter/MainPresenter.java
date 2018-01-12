package com.example.ztz.ponymusic.presenter;

import com.example.ztz.ponymusic.model.IModel.IMainModelView;
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.model.http.MainModel;
import com.example.ztz.ponymusic.view.IView.IMainView;

/**
 * Created by ztz on 2017/12/28.
 */

public class MainPresenter extends BasePresenter implements IMainModelView {

    private final MainModel mainModel;

    private int msize = 15;
    private int offset = 0;
    private boolean  needCler = false;

    private IMainView iMainView;
    public void onAttach(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public MainPresenter(){
        mainModel = new MainModel();
    }
    public void getData(int postion) {
        mainModel.getData(this,postion,15,offset);
    }

    @Override
    public void Success(MusicBean musicBean) {
        if (musicBean != null){
            iMainView.Success(musicBean,needCler);
            needCler = false;
        }
    }

    @Override
    public void Failed(String e) {
        iMainView.Failed(e);
    }

    public void detach(){
        if (iMainView != null){
            iMainView = null;
        }
    }

    public void refreshData(int postion) {
        needCler = true;
        offset = 0;
        //刷新就显示最新的数据
        mainModel.getData(this,postion,15,offset);
    }

    public void loadMore(int postion) {
        offset++;
        mainModel.getData(this,postion,15 ,msize*offset);
    }
}
