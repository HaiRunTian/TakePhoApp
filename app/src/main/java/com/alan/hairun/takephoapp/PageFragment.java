package com.alan.hairun.takephoapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.alan.hairun.takephoapp.utils.CameraUtils;
import com.alan.hairun.takephoapp.utils.DateTimeUtil;
import com.alan.hairun.takephoapp.utils.FileUtils;
import com.alan.hairun.takephoapp.utils.LogUtills;

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
 * @time: 上午 9:54
 * @deprecated:
 */
public class PageFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    private static final String COLORS = "colors";
    private static final String POSITION = "position";
    Button btnAddPic;
    private Unbinder bind;
    private List<String> list;
    private GridViewAdapter gridViewAdapter;
    private View inflate;
    private ListView listView;
    private int m_picIndex = 0;
    private File m_pictureName;
    private Uri fileUri;
    private Bitmap picBitmap;
    //单张照片名字
    private String pictureName;
    /**
     * 临时图片文件名数组
     */
    private List<String> picNames;
    private double x;
    private double y;
    private String path;

    public static PageFragment newInstance(String path, double x, double y) {
        Bundle args = new Bundle();
        args.putString("path", path);
        args.putDouble("x", x);
        args.putDouble("y", y);
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
        btnAddPic = inflate.findViewById(R.id.btnAddPic);
        return inflate;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        if (getArguments() != null) {
            path = getArguments().getString("path");
            x = getArguments().getDouble("x");
            y = getArguments().getDouble("y");
            int fileCount = FileUtils.getInstance().getFileCount(path);
            File file = new File(path);
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    list.add(files[i].getAbsolutePath());
                }
                m_picIndex = files.length;
            }

            gridViewAdapter = new GridViewAdapter(getActivity(), list);
            listView.setAdapter(gridViewAdapter);
        }

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        btnAddPic.setOnClickListener(this);
    }



    private void openCamera() {
        if (!hasPermission()) {
            return;
        }
        m_picIndex++;
        //照片名字
        String name = path + "/" + DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS) + "_" + m_picIndex + "_(" + x + " : " + y + ").jpg";
        m_pictureName = new File(name);
        m_pictureName.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //步骤二：Android 7.0及以上获取文件 Uri
            fileUri = FileProvider.getUriForFile(getActivity(), "com.alan.hairun.takephoapp", m_pictureName);
        } else {
            //步骤三：获取文件Uri
            fileUri = Uri.fromFile(m_pictureName);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CameraUtils.PHOTO_REQUEST_TAKEPHOTO);

    }

    /**
     * 请求权限
     *
     * @author: Alan
     * created at: 2020/5/10 0010 下午 2:04
     * @deprecated :
     */
    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CameraUtils.PHOTO_REQUEST_TAKEPHOTO);
            return false;
        } else {
            return true;
        }
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

    //手机拍照回调
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case CameraUtils.PHOTO_REQUEST_TAKEPHOTO:
                    picBitmap = BitmapFactory.decodeFile(m_pictureName.getAbsolutePath());
                    picBitmap = CameraUtils.comp(picBitmap);
                    if (picBitmap != null) {
                        //拍摄返回的图片name
                        list.add(0,m_pictureName.getAbsolutePath());
                        gridViewAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "图片名不允许带特殊符号", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "拍照失败", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddPic:
                openCamera();
                break;
                default:break;
        }

    }
}
