package com.alan.hairun.takephoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.alan.hairun.takephoapp.utils.CameraUtils;
import com.alan.hairun.takephoapp.utils.FileUtils;
import com.alan.hairun.takephoapp.utils.LogUtills;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

/**
 * @author: Alan
 * @date: 2020/5/24 0024
 * @time: 上午 9:54
 * @deprecated:
 */
public class PageFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private static final String COLORS = "colors";
    private static final String POSITION = "position";
    private Unbinder bind;
    private List<String> list;
    private GridViewAdapter gridViewAdapter;
    private View inflate;
    private ListView listView;

    public static PageFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString("path", path);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        LogUtills.i("fragment addres = " + fragment);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.picture_fragment, container, false);
        listView = inflate.findViewById(R.id.listView);
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        if (getArguments() != null) {
            String path = getArguments().getString("path");
            int fileCount = FileUtils.getInstance().getFileCount(path);
            File file = new File(path);
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    list.add(files[i].getAbsolutePath());
                }
            }

            gridViewAdapter = new GridViewAdapter(getActivity(), list);
            listView.setAdapter(gridViewAdapter);
        }

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        viewPicture(position);
    }

    /**
     * 查看图片
     */
    private void viewPicture(int position) {
        if (list.get(position) != null) {
            File file = new File(list.get(position));
            //打开照片查看
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri _uri;
            if (Build.VERSION.SDK_INT >= 24) {
                _uri = FileProvider.getUriForFile(getContext().getApplicationContext(), "com.alan.hairun.takephoapp", file);
            } else {
                _uri = Uri.fromFile(file);
            }
            intent.setDataAndType(_uri, CameraUtils.IMAGE_UNSPECIFIED);
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        FileUtils.getInstance().deleteFile(list.get(position));
        list.remove(position);
        gridViewAdapter.notifyDataSetChanged();
        return true;
    }
}
