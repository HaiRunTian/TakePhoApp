package com.alan.hairun.takephoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alan.hairun.takephoapp.utils.CameraUtils;
import com.alan.hairun.takephoapp.utils.DateTimeUtil;
import com.alan.hairun.takephoapp.utils.FileUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: Alan
 * @date: 2020/5/24 0024
 * @time: 上午 9:31
 * @deprecated:
 */
public class SeekPictureActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager2)
    ViewPager2 viewpager2;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private Unbinder bind;
    private ViewPagerFragmentStateAdapter mAdapter;
    private List<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekpicture_activity_layout);
        bind = ButterKnife.bind(this);
        initEvent();
        initData();

    }

    private void initData() {
        fragments = new ArrayList<>();
        String path = getIntent().getStringExtra("path");
        double x =  getIntent().getDoubleExtra("x",0.0);
        double y = getIntent().getDoubleExtra("y",0.0);
        int fileCount = FileUtils.getInstance().getFoldeCount(path);
        for (int i = 0; i < fileCount; i++) {
            int j = i + 1;
            tablayout.addTab(tablayout.newTab().setText("文件夹" + j));
            fragments.add(PageFragment.newInstance(path + "/" + j,x,y));
        }

        mAdapter = new ViewPagerFragmentStateAdapter(this,fragments);
        viewpager2.setAdapter(mAdapter);
        viewpager2.setOffscreenPageLimit(1);
        setDefaultCurrentItem(0);
    }

    @OnClick({R.id.tvTitle})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvTitle:
                finish();
                break;

        default:break;
        }
    }



    /**
     * 请求权限
     *
     * @author: Alan
     * created at: 2020/5/10 0010 下午 2:04
     * @deprecated :
     */
    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CameraUtils.PHOTO_REQUEST_TAKEPHOTO);
            return false;
        } else {
            return true;
        }
    }

    private void initEvent() {
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager2.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        // 注册页面变化的回调接口
        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tablayout.setScrollPosition(position, 0, false);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * @param position 默认显示的Item
     */
    private void setDefaultCurrentItem(int position) {
        viewpager2.setCurrentItem(position, false);

    }

}
