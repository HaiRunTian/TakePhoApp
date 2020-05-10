package com.alan.hairun.takephoapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.alan.hairun.takephoapp.utils.CameraUtils;
import com.alan.hairun.takephoapp.utils.DateTimeUtil;
import com.alan.hairun.takephoapp.utils.FileUtils;
import com.alan.hairun.takephoapp.utils.InitWindowSize;
import com.alan.hairun.takephoapp.utils.MyAlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Alan
 * @date: 2020/5/10 0010
 * @time: 下午 1:51
 * @deprecated:
 */
public class TakePhoFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private View view;
    private Button btnAdd;
    private GridView gridView;
    private TextView tvBack;
    private String father;
    private String child;
    private String mPictureName = "";
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
    /**
     * 临时图片文件数组
     */
    private List<File> picFiles;
    private ArrayList<HashMap<String, Object>> imageItem;
    //照片
    private List<String> m_listPicName;
    /**
     * //适配器
     */
    private SimpleAdapter simpleAdapter;
    private TextView tvPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.takepho_fragment_layout, container, false);
        tvBack = view.findViewById(R.id.patrol_return);
        gridView = view.findViewById(R.id.gridView);
        btnAdd = view.findViewById(R.id.btnAddPic);
        tvPath = view.findViewById(R.id.tvText);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTakePicArea();
        btnAdd.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);

        Bundle bundle = getArguments();
        //工程文件夹
        father = bundle.getString("father");
        //工程文件里的子文件夹 存照片
        child = bundle.getString("child");
        String s = father.substring(father.lastIndexOf("拍"));

        tvPath.setText("照片目录：" + s + "/" + child + "/");
    }

    private void initTakePicArea() {
        picFiles = new ArrayList<>();
        picNames = new ArrayList<>();
        imageItem = new ArrayList<HashMap<String, Object>>();
    }

    @Override
    public void onStart() {
        super.onStart();
        InitWindowSize.ins().initWindowSize(getActivity(), getDialog(), 0.9, 0.8);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddPic:
                //拍照逻辑
                openCamera();
                break;
            case R.id.patrol_return:
                getDialog().dismiss();
                break;
        }
    }

    private void openCamera() {
        if (!hasPermission()) {
            return;
        }
        m_picIndex++;
        //照片名字
        String name = father + "/" + child + "/" + DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS) +"_"+ m_picIndex+ ".jpg";
        m_pictureName = new File(name);
        m_pictureName.getParentFile().mkdirs();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //步骤二：Android 7.0及以上获取文件 Uri
            fileUri = FileProvider.getUriForFile(getActivity(), "com.alan.hairun.takephoapp", m_pictureName);
        } else {
            //步骤三：获取文件Uri
            fileUri = Uri.fromFile(m_pictureName);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
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
                        pictureName = m_pictureName.getName();
                        picNames.add(pictureName);
                        picFiles.add(m_pictureName);
                        HashMap<String, Object> _map = new HashMap<>();
                        _map.put("itemImage", picBitmap);
                        _map.put("picName", pictureName);
                        imageItem.add(_map);
                        refreshGridviewAdapter();
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

    /**
     * 更新照片
     *
     * @Params :
     * @author :HaiRun
     * @date :2020/5/3  10:56
     */
    private void refreshGridviewAdapter() {
        if (simpleAdapter == null) {
            simpleAdapter = new SimpleAdapter(getActivity(), imageItem, R.layout.layout_griditem_addpic,
                    new String[]{"itemImage", "picName"}, new int[]{R.id.imageView1, R.id.tvPicName});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(final View view, final Object data, String textRepresentation) {
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {//绑定视图
                                ImageView i = (ImageView) view;
                                i.setImageBitmap((Bitmap) data);
                            }
                        });
                        return true;
                    } else if (view instanceof TextView) {
                        TextView tv = (TextView) view;
                        tv.setText(textRepresentation);
                        return true;
                    }

                    return false;
                }
            });
        }
        //主线程绑定adapter刷新数据
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gridView.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 查看图片
     */
    private void viewPicture(int position) {
        if (picFiles.get(position) != null) {
            File file = picFiles.get(position);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        viewPicture(i);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        MyAlertDialog.showAlertDialog(getActivity(), "删除提示", "确定删除改照片？", "确定", "取消", true,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //从手机卡删除
                        FileUtils.getInstance().deleteFile(picFiles.get(i));
                        imageItem.remove(i);
                        picNames.remove(i);
                        picFiles.remove(i);
                        refreshGridviewAdapter();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return true;
    }
}
