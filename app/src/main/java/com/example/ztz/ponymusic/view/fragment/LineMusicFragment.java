package com.example.ztz.ponymusic.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.MusicBean;
import com.example.ztz.ponymusic.presenter.MainPresenter;
import com.example.ztz.ponymusic.view.IView.IMainView;
import com.example.ztz.ponymusic.view.activity.DetailsActivity;
import com.example.ztz.ponymusic.view.adapter.DetailsRecyAdapter;
import com.example.ztz.ponymusic.view.adapter.LineMusicRecyAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ztz on 2017/12/28.
 */

public class LineMusicFragment extends Fragment{

    @BindView(R.id.linemusic_recyclerview)
    RecyclerView linemusicRecyclerview;
    private MainPresenter mainPresenter;
    Unbinder unbinder;
    private LineMusicRecyAdapter lineMusicRecyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.linemusicfm_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        lineMusicRecyAdapter.setItemOnClickListener(new LineMusicRecyAdapter.MyItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int postion) {
                //跳转到详情展示音乐页面
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                //传postion过去
                intent.putExtra("pos",postion);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        linemusicRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        linemusicRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        lineMusicRecyAdapter = new LineMusicRecyAdapter(getContext());
        linemusicRecyclerview.setAdapter(lineMusicRecyAdapter);
    }

    private void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
