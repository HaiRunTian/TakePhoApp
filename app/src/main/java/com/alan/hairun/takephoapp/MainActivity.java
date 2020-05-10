package com.alan.hairun.takephoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.alan.hairun.gen.DaoSession;
import com.alan.hairun.gen.ProjectBeanDao;
import com.alan.hairun.takephoapp.adapter.SpinnerAdapter;
import com.alan.hairun.takephoapp.bean.ProjectBean;
import com.alan.hairun.takephoapp.config.MyApplication;
import com.alan.hairun.takephoapp.utils.AlertDialogUtil;
import com.alan.hairun.takephoapp.utils.DateTimeUtil;
import com.alan.hairun.takephoapp.utils.FileUtils;
import com.alan.hairun.takephoapp.utils.ImportDataProgressUtil;
import com.alan.hairun.takephoapp.utils.SelectExcelActivity;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import org.apache.poi.hssf.util.HSSFColor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spProject;
    private ImageButton btnAdd;
    private List<String> list;
    private String projectName = "";
    private DaoSession daoSession;
    public static String SD = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    private String APP_PAHT = "/拍照采集系统";
    private String PICTURE = "/照片";
    private ArrayAdapter arrayAdapter;
    private ImageButton btnDel;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    public static final String QQ_FILE_PATH = SD + "/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv";
    public  static  final String WECHAT_FILE_PATH = SD + "/tencent/MicroMsg/Download";
    public static String FATH = "";
    private ProgressDialog m_progress;
    private String m_filePath;
    private String m_fileName;
    private int[] array = new int[1];
    @SuppressLint("HandlerLeak")
    private Handler m_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 4:
                    //点表  读取excel 和生成数据集比较好耗时间 需要在子线程里进行
                    ImportDataProgressUtil.ImportData(m_filePath, new ImportDataProgressUtil.ImportListener() {
                        @Override
                        public void zipStart() {
                            m_handler.sendEmptyMessage(5);
                        }

                        @Override
                        public void zipSuccess() {
                            m_handler.sendEmptyMessage(6);
                        }

                        @Override
                        public void zipProgress(int[] progress) {
                            array = progress;
                            m_handler.sendEmptyMessage(7);
                        }

                        @Override
                        public void zipFail() {
                            m_handler.sendEmptyMessage(8);
                        }
                    });

                    break;
                case 5:
                    //开始初始化数据
                    m_progress.setMessage("数据正常读取统计中……");
                    m_progress.show();
                    break;
                case 6:
                    //数据导入成功
                    m_progress.setMessage("数据导入成功！");
                    list.addAll(queryProjectList());
                    arrayAdapter.notifyDataSetChanged();
                    m_progress.dismiss();
                    break;
                case 7:
                    //数据统计显示
                    m_progress.setMessage("导入的数据有" + array[0] + "条");
                    break;
                case 8:
                    //数据导入失败
                    m_progress.setMessage("数据导入失败！");
                    m_progress.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFolder(SD + APP_PAHT);
        initView();
        initData();
        intEvent();
        initLocal();

    }

    private void initLocal() {
        try {
        //定位初始化
                     mBaiduMap = mMapView.getMap();
            //开启定位图层
                      mBaiduMap.setMyLocationEnabled(true);
                    mLocationClient = new LocationClient(MainActivity.this);
                    //通过LocationClientOption设置LocationClient相关参数
                    LocationClientOption option = new LocationClientOption();
                    option.setOpenGps(true); // 打开gps
                    option.setCoorType("gcj02"); // 设置坐标类型
                    option.setScanSpan(1000);

                    //设置locationClientOption
                    mLocationClient.setLocOption(option);
                    //注册LocationListener监听器
                    MyLocationListener myLocationListener = new MyLocationListener();
                    mLocationClient.registerLocationListener(myLocationListener);
                    //开启地图定位图层
                    mLocationClient.start();
            MyLocationConfiguration locationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,
                    true,BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
            mBaiduMap.setMyLocationConfiguration(locationConfiguration);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.zoom(20.0f);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }catch (Exception e){
            Log.e("main","定位error;" + e.toString());
        }

    }

    /**
     * 创建文件夹
     *
     * @author: Alan
     * created at: 2020/5/10 0010 下午 12:37
     * @deprecated :
     */
    private void createFolder() {
        try {
            //创建app文件夹
            FileUtils.getInstance().mkdirs(SD + APP_PAHT);
        } catch (Exception e) {
            Log.e("Main", "create folder error " + e.toString());
        }
    }

    private void initData() {
        list = queryProjectList();
        arrayAdapter = new ArrayAdapter(this, R.layout.text_layout, list);
        spProject.setAdapter(arrayAdapter);
    }

    private List<String> queryProjectList() {
        daoSession = MyApplication.getINSTANT().getDaoSession();
        List<ProjectBean> projectBeans = daoSession.getProjectBeanDao().queryBuilder().orderDesc(ProjectBeanDao.Properties.Id).list();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < projectBeans.size(); i++) {
            list.add(projectBeans.get(i).getName());
            FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/"+ projectBeans.get(i).getName());
        }

        return list;
    }

    private void intEvent() {

    }

    /**
     * 初始化view
     *
     * @author: Alan
     * created at: 2020/5/9 0009 下午 11:43
     * @deprecated :
     */
    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        spProject = findViewById(R.id.spProject);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDeletet);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0){

                    Toast.makeText(MainActivity.this,"你还没创建项目，不能删除操作",Toast.LENGTH_LONG).show();
                    return;
                }
                showDeletDialog();
//                BaiduMapOptions options = new BaiduMapOptions();
//                //设置地图模式为卫星地图
//                options.mapType(BaiduMap.MAP_TYPE_SATELLITE);

            }
        });
        ImageButton btnAddImg = findViewById(R.id.btnAddPicture);
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() == 0){
                    Toast.makeText(MainActivity.this,"你还没有创建项目，请先创建项目",Toast.LENGTH_LONG).show();
                    return;
                }
                showDialog();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });

        //导入excel
        ImageButton btnImport = findViewById(R.id.btnImport);
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importExcel();
            }
        });
        m_progress = new ProgressDialog(this);
        m_progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_progress.setCancelable(false);
        m_progress.setCanceledOnTouchOutside(false);
        m_progress.setIcon(R.mipmap.ic_file_excel);
        m_progress.setTitle("导入数据");
    }

    private void importExcel() {
        AlertDialogUtil.showDialog(MainActivity.this, "导入文件", "请选择导入的文件", false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FATH = QQ_FILE_PATH;
                startActivityForResult(new Intent(MainActivity.this, SelectExcelActivity.class), 1);
                dialog.dismiss();
            }
        },new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FATH = WECHAT_FILE_PATH;
                startActivityForResult(new Intent(MainActivity.this, SelectExcelActivity.class), 1);
                dialog.dismiss();
            }
        });
    }

    private void showDeletDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_question);
        builder.setTitle("删除提示");
        builder.setMessage("确定删除该项目:"+spProject.getSelectedItem().toString()+"及相关照片吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DaoSession daoSession = MyApplication.getINSTANT().getDaoSession();
                daoSession.getProjectBeanDao().queryBuilder().where(ProjectBeanDao.Properties.Name.eq(spProject.getSelectedItem().toString()))
                        .buildDelete().executeDeleteWithoutDetachingEntities();
                FileUtils.getInstance().deleteDir(SD + APP_PAHT + "/" + spProject.getSelectedItem().toString());
                list.remove(spProject.getSelectedItem().toString());
                arrayAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 显示拍照页面
     *
     * @author: Alan
     * created at: 2020/5/10 0010 下午 2:52
     * @deprecated :
     */
    private void showDialog() {
        projectName = spProject.getSelectedItem().toString();
        //获取当前目录有多少个子文件夹最大名称号
        int fileIndexMax = FileUtils.getInstance().getFileIndexMax(SD + APP_PAHT + "/" + projectName + PICTURE);
        fileIndexMax++;
        //创建文件夹爱
        FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + projectName + PICTURE + "/" + fileIndexMax);
        TakePhoFragment fragment = new TakePhoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("father", SD + APP_PAHT + "/" + projectName + PICTURE);
        bundle.putString("child", String.valueOf(fileIndexMax));
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager().beginTransaction(), "fragmen");
    }

    /**
     * 自定义登录对话框
     */
    public void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(MainActivity.this, R.layout.dialog_layout, null);
        dialog.setView(dialogView);
        dialog.show();

        final EditText edtName = dialogView.findViewById(R.id.edtProject);
        final Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        final Button btnSave = dialogView.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prjName = edtName.getText().toString();
                //判断这个工程是否已经存在了
                DaoSession daoSession = MyApplication.getINSTANT().getDaoSession();
                List<ProjectBean> list2 = daoSession.getProjectBeanDao().queryBuilder().where(ProjectBeanDao.Properties.Name.eq(prjName)).build().list();
                if (list2.size() != 0) {
                    Toast.makeText(MainActivity.this, "此项目名称已经存在", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    ProjectBean projectBean = new ProjectBean();
                    projectBean.setDate(DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS));
                    projectBean.setName(prjName);
                    daoSession.getProjectBeanDao().insert(projectBean);
                    //创建文件夹
                    FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + prjName);
                    list.add(0,prjName);
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 创建文件夹
     *
     * @author: Alan
     * created at: 2020/5/10 0010 下午 12:48
     * @deprecated :
     */
    private void createFolder(String file) {
        try {
            if (FileUtils.getInstance().mkdirs(file)) {
                Log.e("main", "crate folder faiure " + file);
            }
        } catch (Exception e) {
            Log.e("main", "crate folder error " + e.toString());
        }

    }

    @Override
    protected void onResume() {

        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {

        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    m_filePath = data.getStringExtra("filePath");
                    m_fileName = data.getStringExtra("fileName");
                    m_handler.sendEmptyMessage(4);
                }
                break;
            default:
                break;
        }
    }
}
