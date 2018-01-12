package com.example.ztz.ponymusic.view.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ztz.ponymusic.R;
import com.example.ztz.ponymusic.model.bean.EventMineBean;
import com.example.ztz.ponymusic.model.bean.Song;
import com.example.ztz.ponymusic.util.musicscan.MusicUtils;
import com.example.ztz.ponymusic.view.activity.Play_Details_Activity;
import com.example.ztz.ponymusic.view.adapter.MineRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ztz on 2017/12/28.
 */

public class MineMusicFragment extends Fragment {
    @BindView(R.id.mine_recyclerview)
    RecyclerView mineRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.mine_btn_sousuo)
    Button mineBtnSousuo;
    private MineRecyclerAdapter mineRecyclerAdapter;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private List<Song> musicData;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                musicData = (List<Song>) msg.obj;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.minemusicfm_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        mineRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mineRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mineRecyclerAdapter = new MineRecyclerAdapter(getContext());
        mineRecyclerview.setAdapter(mineRecyclerAdapter);
        //本地音乐的条目点击事件
        mineRecyclerAdapter.setItemOnClickListener(new MineRecyclerAdapter.MyItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int postion) {
                String path = musicData.get(postion).path;
                String song = musicData.get(postion).song;
                String singer = musicData.get(postion).singer;
                EventBus.getDefault().postSticky(new EventMineBean(path, song, singer));
                //跳转Play_Details_Activity页面
                Intent intent = new Intent(getActivity(), Play_Details_Activity.class);
                intent.putExtra("isMine", "isMine");
                intent.putExtra("song",song);
                intent.putExtra("songer",singer);
                intent.putExtra("path",path);
                startActivity(intent);

            }
        });
        mineBtnSousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //判断是否是6.0，然后进行权限设置
        if (Build.VERSION.SDK_INT >= 23) {
            mRunTime();
        } else {
            initData();
        }

    }

    private void initData() {
        List<Song> musicData = MusicUtils.getMusicData(getContext());
        if (musicData != null) {
            //添加到适配器
            mineRecyclerAdapter.addData(musicData);
            mineRecyclerAdapter.notifyDataSetChanged();
            Message message = new Message();
            message.what = 0;
            message.obj = musicData;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void mRunTime() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            initData();
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                initData();

            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
