package com.example.ztz.ponymusic.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.SearchBean;
import com.example.ztz.ponymusic.presenter.SearchPresenter;
import com.example.ztz.ponymusic.view.IView.ISearchView;
import com.example.ztz.ponymusic.view.adapter.LineMusicRecyAdapter;
import com.example.ztz.ponymusic.view.adapter.SearchMusicRecyAdapter;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements ISearchView, View.OnClickListener {

    @BindView(R.id.serach_fanhui)
    ImageView serachFanhui;
    @BindView(R.id.serach_edit_text_sousuo)
    EditText serachEditTextSousuo;
    @BindView(R.id.search_sousuo)
    ImageView searchSousuo;
    @BindView(R.id.line)
    LinearLayout line;
    @BindView(R.id.seachrecyclerview)
    RecyclerView seachrecyclerview;
    private SearchPresenter searchPresenter;
    private String searchtext;
    private SearchMusicRecyAdapter searchMusicRecyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //搜索键
        searchSousuo.setOnClickListener(this);
    }

    @Override
    int setContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    void initView() {
        //返回键
        serachFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seachrecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        seachrecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        searchMusicRecyAdapter = new SearchMusicRecyAdapter(this);
        seachrecyclerview.setAdapter(searchMusicRecyAdapter);
    }

    @Override
    void initData() {
        searchPresenter = new SearchPresenter();
        searchPresenter.Attach(this);
    }

    /**
     * 得到的数据
     * @param searchBean
     */
    @Override
    public void Success(SearchBean searchBean) {
        if (searchBean != null){
            List<SearchBean.SongBean> list = searchBean.getSong();
            searchMusicRecyAdapter.addData(list);
            searchMusicRecyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void Failed(String e) {
    }

    @Override
    public void onClick(View view) {
        //搜索框输入的内容
        searchtext = serachEditTextSousuo.getText().toString();
        //请求网络进行搜索
        searchPresenter.getData(searchtext);
    }
}
