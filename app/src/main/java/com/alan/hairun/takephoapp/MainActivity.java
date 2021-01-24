package com.alan.hairun.takephoapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alan.hairun.gen.CheckDataBeanDao;
import com.alan.hairun.gen.DaoSession;
import com.alan.hairun.gen.ProjectBeanDao;
import com.alan.hairun.gen.ProjectBitBeanDao;
import com.alan.hairun.takephoapp.bean.CheckDataBean;
import com.alan.hairun.takephoapp.bean.ProjectBean;
import com.alan.hairun.takephoapp.bean.ProjectBitBean;
import com.alan.hairun.takephoapp.config.MyApplication;
import com.alan.hairun.takephoapp.utils.AlertDialogUtil;
import com.alan.hairun.takephoapp.utils.DateTimeUtil;
import com.alan.hairun.takephoapp.utils.ExcelUtilsOfPoi;
import com.alan.hairun.takephoapp.utils.FileUtils;
import com.alan.hairun.takephoapp.utils.GpsUtils;
import com.alan.hairun.takephoapp.utils.ImportDataProgressUtil;
import com.alan.hairun.takephoapp.utils.LogUtills;
import com.alan.hairun.takephoapp.utils.SelectExcelActivity;
import com.alan.hairun.takephoapp.utils.SpinnerDropdownListManager;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.maps.Map.MapLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv11)
    TextView tv11;
    @BindView(R.id.tv12)
    TextView tv12;
    @BindView(R.id.tv13)
    TextView tv13;
    @BindView(R.id.tv21)
    TextView tv21;
    @BindView(R.id.tv22)
    TextView tv22;
    @BindView(R.id.tv23)
    TextView tv23;
    @BindView(R.id.tv31)
    TextView tv31;
    @BindView(R.id.tv32)
    TextView tv32;
    @BindView(R.id.tv33)
    TextView tv33;
    @BindView(R.id.tv41)
    TextView tv41;
    @BindView(R.id.tv42)
    TextView tv42;
    @BindView(R.id.tv43)
    TextView tv43;
    @BindView(R.id.tv51)
    TextView tv51;
    @BindView(R.id.tv52)
    TextView tv52;
    @BindView(R.id.tv53)
    TextView tv53;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.spBitProject)
    Spinner spBitProject;
    @BindView(R.id.tv14)
    EditText tv14;
    @BindView(R.id.tv15)
    EditText tv15;
    @BindView(R.id.tv16)
    EditText tv16;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.tv24)
    EditText tv24;
    @BindView(R.id.tv25)
    EditText tv25;
    @BindView(R.id.tv26)
    EditText tv26;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.tv34)
    EditText tv34;
    @BindView(R.id.tv35)
    EditText tv35;
    @BindView(R.id.tv36)
    EditText tv36;
    @BindView(R.id.tv44)
    EditText tv44;
    @BindView(R.id.tv45)
    EditText tv45;
    @BindView(R.id.tv46)
    EditText tv46;
    @BindView(R.id.layout4)
    LinearLayout layout4;
    @BindView(R.id.tv54)
    EditText tv54;
    @BindView(R.id.tv55)
    EditText tv55;
    @BindView(R.id.tv56)
    EditText tv56;
    @BindView(R.id.layout5)
    LinearLayout layout5;
    @BindView(R.id.tv61)
    TextView tv61;
    @BindView(R.id.tv62)
    TextView tv62;
    @BindView(R.id.tv63)
    TextView tv63;
    @BindView(R.id.tv64)
    EditText tv64;
    @BindView(R.id.tv65)
    EditText tv65;
    @BindView(R.id.tv66)
    EditText tv66;
    @BindView(R.id.layout6)
    LinearLayout layout6;
    @BindView(R.id.tv71)
    TextView tv71;
    @BindView(R.id.tv72)
    TextView tv72;
    @BindView(R.id.tv73)
    TextView tv73;
    @BindView(R.id.tv74)
    EditText tv74;
    @BindView(R.id.tv75)
    EditText tv75;
    @BindView(R.id.tv76)
    EditText tv76;
    @BindView(R.id.layout7)
    LinearLayout layout7;
    @BindView(R.id.tv81)
    TextView tv81;
    @BindView(R.id.tv82)
    TextView tv82;
    @BindView(R.id.tv83)
    TextView tv83;
    @BindView(R.id.tv84)
    EditText tv84;
    @BindView(R.id.tv85)
    EditText tv85;
    @BindView(R.id.tv86)
    EditText tv86;
    @BindView(R.id.layout8)
    LinearLayout layout8;
    @BindView(R.id.tv91)
    TextView tv91;
    @BindView(R.id.tv92)
    TextView tv92;
    @BindView(R.id.tv93)
    TextView tv93;
    @BindView(R.id.tv94)
    EditText tv94;
    @BindView(R.id.tv95)
    EditText tv95;
    @BindView(R.id.tv96)
    EditText tv96;
    @BindView(R.id.layout9)
    LinearLayout layout9;
    @BindView(R.id.spType)
    Spinner spType;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnExport)
    Button btnExport;
    @BindView(R.id.openLocal)
    ImageButton openLocal;
    private Spinner spProject;
    private ImageButton btnAdd;
    private List<String> projectlist;
    private List<String> list;
    private List<String> listType;
    private String projectName = "";
    private DaoSession daoSession;
    public static String SD = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String APP_PAHT = "/0照片采集系统";
    private String PICTURE = "/照片";
    private ArrayAdapter adapter;
    private ArrayAdapter adapterType;
    private ArrayAdapter arrayAdapter;
    private ImageButton btnDel, imgBtnSeekPic;
    public static final String QQ_FILE_PATH = SD + "/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv";
    public static final String WECHAT_FILE_PATH = SD + "/tencent/MicroMsg/Download";
    public static String FATH = "";
    private ProgressDialog m_progress;
    private String m_filePath;
    private String m_fileName;
    private int[] array = new int[1];
    public static String bitProject = "";
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
                    listType.addAll(queryProjectType());
                    adapterType.notifyDataSetChanged();
                    spType.setSelection(0);
                    list.addAll(queryProjectList());
                    arrayAdapter.notifyDataSetChanged();
                    spBitProject.setSelection(0);
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
    private double x;
    private double y;
    private int count;
    private List<CheckDataBean> checkDataBeans;
    private MapView mapView;
    private MapController mMapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createFolder(SD + APP_PAHT);
        initView();
        initData();
        intEvent();
        initLocal();


    }

    private void initLocal() {
        try {
            mapView.setBuiltInZoomControls(true);
            //得到mMapView的控制权,可以用它控制和驱动平移和缩放
            mMapController = mapView.getController();
            mapView.setMapType(MapView.TMapType.MAP_TYPE_IMG);
            //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
            GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
            //设置地图中心点
            mMapController.setCenter(point);
            initTempLocalXy();
            if (!GpsUtils.isOPen(MainActivity.this)) {
                Toast.makeText(MainActivity.this, "请先打开GPS定位功能", Toast.LENGTH_LONG).show();
                GpsUtils.openGPS(MainActivity.this);
            }
        } catch (Exception e) {
            Log.e("main", "定位error;" + e.toString());
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
        daoSession = MyApplication.getINSTANT().getDaoSession();
        List<ProjectBitBean> list = daoSession.getProjectBitBeanDao().queryBuilder().where(ProjectBitBeanDao.Properties.Remark.eq("1")).list();
        String name = "";
        for (int i = 0; i < list.size(); i++) {
            name = list.get(i).prjName;
        }
        projectlist = queryProjectList1();
        adapter = new ArrayAdapter(this, R.layout.item_spinner, projectlist);
        spBitProject.setAdapter(adapter);
        SpinnerDropdownListManager.setSpinnerItemSelectedByValue(spBitProject, name);
        bitProject = spBitProject.getSelectedItem() != null ? spBitProject.getSelectedItem().toString() : "";

        listType = queryProjectType();
        adapterType = new ArrayAdapter(this, R.layout.text_layout, listType);
        spType.setAdapter(adapterType);
        spType.setSelection(0);

        this.list = queryProjectList();
        arrayAdapter = new ArrayAdapter(this, R.layout.text_layout, this.list);
        spProject.setAdapter(arrayAdapter);
        spProject.setSelection(0);
    }


    private List<String> queryProjectList1() {
        List<ProjectBitBean> projectBeans = daoSession.getProjectBitBeanDao().queryBuilder()
                .orderDesc(ProjectBitBeanDao.Properties.Id)
                .list();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < projectBeans.size(); i++) {
            list.add(projectBeans.get(i).getPrjName());
            //创建文件
            FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + projectBeans.get(i).getPrjName());

        }

        return list;
    }

    private List<String> queryProjectType() {
        List<String> list2 = new ArrayList<>();
        String sql = "SELECT DISTINCT " + ProjectBeanDao.Properties.Type.columnName + " FROM "
                + ProjectBeanDao.TABLENAME + " where " + ProjectBeanDao.Properties.BitName.columnName + " = '" + bitProject + "'";
        LogUtills.i(sql);
        Cursor cursor = daoSession.getDatabase().rawQuery(sql, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    list2.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return list2;
    }

    private List<String> queryProjectList() {
        String type = spType.getSelectedItem() != null ? spType.getSelectedItem().toString() : "";

        List<ProjectBean> projectBeans = daoSession.getProjectBeanDao()
                .queryBuilder()
                .where(ProjectBeanDao.Properties.BitName.eq(bitProject))
                .where(ProjectBeanDao.Properties.Type.eq(type))
                .orderDesc(ProjectBeanDao.Properties.Id)
                .list();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < projectBeans.size(); i++) {
            list.add(projectBeans.get(i).getName());
        }

        return list;
    }

    private void intEvent() {

        openLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GpsUtils.isOPen(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "请先打开GPS定位功能", Toast.LENGTH_LONG).show();
                    GpsUtils.openGPS(MainActivity.this);
                }else {
                    initTempLocalXy();
                }

            }
        });

        spBitProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //创建此文件夹即可
                bitProject = spBitProject.getSelectedItem().toString();
                String path = SD + APP_PAHT + "/" + bitProject + PICTURE;
                //q清空view
              /*  clearView();
                setViewGone();*/
                listType.clear();
                List<String> list2 = new ArrayList<>();
                list2.add("");
                String sql = "SELECT DISTINCT " + ProjectBeanDao.Properties.Type.columnName + " FROM "
                        + ProjectBeanDao.TABLENAME + " where " + ProjectBeanDao.Properties.BitName.columnName + " = '" + bitProject + "'";
                LogUtills.i(sql);
                Cursor cursor = daoSession.getDatabase().rawQuery(sql, null);
                try {
                    if (cursor.moveToFirst()) {
                        do {
                            list2.add(cursor.getString(0));
                        } while (cursor.moveToNext());
                    }
                } finally {
                    cursor.close();
                }
              /*  list.clear();
                arrayAdapter.notifyDataSetChanged();

                clearView();*/
                setViewGone(View.GONE);
                listType.addAll(list2);
                adapterType.notifyDataSetChanged();
                spType.setSelection(0);

                //设置标记  下次打开此项目
                String sql2 = "update " + ProjectBitBeanDao.TABLENAME + " set " + ProjectBitBeanDao.Properties.Remark.columnName + " = '0'";
                daoSession.getDatabase().execSQL(sql2);

                String sql3 = "update " + ProjectBitBeanDao.TABLENAME + " set " + ProjectBitBeanDao.Properties.Remark.columnName
                        + " = '1' where " + ProjectBitBeanDao.Properties.PrjName.columnName + " = '" + bitProject + "'";
                daoSession.getDatabase().execSQL(sql3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //刷新子项目adapter
                String type = spType.getSelectedItem() != null ? spType.getSelectedItem().toString() : "";
                list.clear();
                List<ProjectBean> projectBeans = daoSession.getProjectBeanDao().queryBuilder()
                        .where(ProjectBeanDao.Properties.BitName.eq(bitProject))
                        .where(ProjectBeanDao.Properties.Type.eq(type))
                        .orderDesc(ProjectBeanDao.Properties.Id).list();
                List<String> list1 = new ArrayList<>();
                list1.add("");
                for (int i = 0; i < projectBeans.size(); i++) {
                    list1.add(projectBeans.get(i).getName());

                }
                list.addAll(list1);
                setViewGone(View.GONE);
                arrayAdapter.notifyDataSetChanged();
                spProject.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                arrayAdapter.notifyDataSetChanged();
            }
        });


        spProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (bitProject.isEmpty()) {
                    Toast.makeText(MainActivity.this, "你还没创建项目", Toast.LENGTH_SHORT).show();
                    return;
                }
                //每次选中项目 判断这个项目三个文件夹里是否有照片
                projectName = spProject.getSelectedItem().toString();
                String path = SD + APP_PAHT + "/" + bitProject + "/" + projectName + PICTURE;
                //创建此项目文件夹
//                FileUtils.getInstance().mkdirs(path);
                int folderCount = FileUtils.getInstance().getFoldeCount(path);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < folderCount; i++) {
                    int j = i + 1;
                    String img1 = path + "/" + j;
                    int fileCount1 = FileUtils.getInstance().getFileCount(img1);
                    sb.append("文件" + j + "照片：" + fileCount1 + "张；");
                }

                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                setVialueToVIew(projectName);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                arrayAdapter.notifyDataSetChanged();
            }
        });

        //保存数据
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (count > 0) {
                        CheckDataBean dataBean = checkDataBeans.get(0);
                        dataBean.setOne(tv14.getText().toString() + "");
                        dataBean.setTwo(tv15.getText().toString() + "");
                        dataBean.setThree(tv16.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);

                    }

                    if (count > 1) {
                        CheckDataBean dataBean = checkDataBeans.get(1);
                        dataBean.setOne(tv24.getText().toString() + "");
                        dataBean.setTwo(tv25.getText().toString() + "");
                        dataBean.setThree(tv26.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }

                    if (count > 2) {
                        CheckDataBean dataBean = checkDataBeans.get(2);
                        dataBean.setOne(tv34.getText().toString() + "");
                        dataBean.setTwo(tv35.getText().toString() + "");
                        dataBean.setThree(tv36.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }

                    if (count > 3) {
                        CheckDataBean dataBean = checkDataBeans.get(3);
                        dataBean.setOne(tv44.getText().toString() + "");
                        dataBean.setTwo(tv45.getText().toString() + "");
                        dataBean.setThree(tv46.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }

                    if (count > 4) {
                        CheckDataBean dataBean = checkDataBeans.get(4);
                        dataBean.setOne(tv54.getText().toString() + "");
                        dataBean.setTwo(tv55.getText().toString() + "");
                        dataBean.setThree(tv56.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }

                    if (count > 5) {
                        CheckDataBean dataBean = checkDataBeans.get(5);
                        dataBean.setOne(tv64.getText().toString() + "");
                        dataBean.setTwo(tv65.getText().toString() + "");
                        dataBean.setThree(tv66.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }

                    if (count > 6) {
                        CheckDataBean dataBean = checkDataBeans.get(6);
                        dataBean.setOne(tv74.getText().toString() + "");
                        dataBean.setTwo(tv75.getText().toString() + "");
                        dataBean.setThree(tv76.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }

                    if (count > 7) {
                        CheckDataBean dataBean = checkDataBeans.get(7);
                        dataBean.setOne(tv84.getText().toString() + "");
                        dataBean.setTwo(tv85.getText().toString() + "");
                        dataBean.setThree(tv86.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }

                    if (count > 8) {
                        CheckDataBean dataBean = checkDataBeans.get(8);
                        dataBean.setOne(tv94.getText().toString() + "");
                        dataBean.setTwo(tv95.getText().toString() + "");
                        dataBean.setThree(tv96.getText().toString() + "");
                        daoSession.getCheckDataBeanDao().update(dataBean);
                    }
                    Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void initTempLocalXy() {
        //创建MyLocationOverlay
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay(MainActivity.this, mapView);
        //启用指南针位置更新
        myLocationOverlay.enableCompass();
        //启用我的位置
        myLocationOverlay.enableMyLocation();
        mapView.addOverlay(myLocationOverlay);
        //获得当前位置
        GeoPoint mPoint = myLocationOverlay.getMyLocation();
         x = (double)mPoint.getLongitudeE6() / 1000000.0D;
         y = (double)mPoint.getLatitudeE6()/ 1000000.0D;

        LogUtills.i("x =  " + x + "  y = " + y);

        //动画移动到当前位置
        mMapController.animateTo(mPoint);
        //设置地图zoom级别
        mMapController.setZoom(18);
    }

    private void clearView() {
        tv11.setText("");
        tv12.setText("");
        tv13.setText("");
        tv14.setText("");
        tv15.setText("");
        tv16.setText("");
        tv21.setText("");
        tv22.setText("");
        tv23.setText("");
        tv24.setText("");
        tv25.setText("");
        tv26.setText("");
        tv31.setText("");
        tv32.setText("");
        tv33.setText("");
        tv34.setText("");
        tv35.setText("");
        tv36.setText("");
        tv41.setText("");
        tv42.setText("");
        tv43.setText("");
        tv44.setText("");
        tv45.setText("");
        tv46.setText("");
        tv51.setText("");
        tv52.setText("");
        tv53.setText("");
        tv54.setText("");
        tv55.setText("");
        tv56.setText("");
        tv61.setText("");
        tv62.setText("");
        tv63.setText("");
        tv64.setText("");
        tv65.setText("");
        tv66.setText("");
        tv71.setText("");
        tv72.setText("");
        tv73.setText("");
        tv74.setText("");
        tv75.setText("");
        tv76.setText("");
        tv81.setText("");
        tv82.setText("");
        tv83.setText("");
        tv84.setText("");
        tv85.setText("");
        tv86.setText("");
        tv91.setText("");
        tv92.setText("");
        tv93.setText("");
        tv94.setText("");
        tv95.setText("");
        tv96.setText("");
    }

    private void setVialueToVIew(String projectName) {
        checkDataBeans = MyApplication.getINSTANT().getDaoSession().getCheckDataBeanDao().queryBuilder()
                .where(CheckDataBeanDao.Properties.BitName.eq(bitProject))
                .where(CheckDataBeanDao.Properties.ProjectName.eq(projectName))
                .build()
                .list();
        count = checkDataBeans.size();
        if (checkDataBeans.size() > 0) {
            layout1.setVisibility(View.VISIBLE);
            tv11.setText(checkDataBeans.get(0).getStandard() + "");
            tv12.setText(checkDataBeans.get(0).getUnit() + "");
            tv13.setText(checkDataBeans.get(0).getPlanNum() + "");
            tv14.setText(checkDataBeans.get(0).getOne() == null ? "" : checkDataBeans.get(0).getOne());
            tv15.setText(checkDataBeans.get(0).getTwo() == null ? "" : checkDataBeans.get(0).getTwo());
            tv16.setText(checkDataBeans.get(0).getThree() == null ? "" : checkDataBeans.get(0).getThree());
        } else {
            setViewGone(View.GONE);
        }

        if (checkDataBeans.size() > 1) {
            layout2.setVisibility(View.VISIBLE);
            tv21.setText(checkDataBeans.get(1).getStandard() + "");
            tv22.setText(checkDataBeans.get(1).getUnit() + "");
            tv23.setText(checkDataBeans.get(1).getPlanNum() + "");
            tv24.setText(checkDataBeans.get(1).getOne() == null ? "" : checkDataBeans.get(1).getOne());
            tv25.setText(checkDataBeans.get(1).getTwo() == null ? "" : checkDataBeans.get(1).getTwo());
            tv26.setText(checkDataBeans.get(1).getThree() == null ? "" : checkDataBeans.get(1).getThree());
        } else {
            layout2.setVisibility(View.GONE);
        }

        if (checkDataBeans.size() > 2) {
            layout3.setVisibility(View.VISIBLE);
            tv31.setText(checkDataBeans.get(2).getStandard() + "");
            tv32.setText(checkDataBeans.get(2).getUnit() + "");
            tv33.setText(checkDataBeans.get(2).getPlanNum() + "");
            tv34.setText(checkDataBeans.get(2).getOne() == null ? "" : checkDataBeans.get(2).getOne());
            tv35.setText(checkDataBeans.get(2).getTwo() == null ? "" : checkDataBeans.get(2).getTwo());
            tv36.setText(checkDataBeans.get(2).getThree() == null ? "" : checkDataBeans.get(2).getThree());
        } else {
            layout3.setVisibility(View.GONE);
        }

        if (checkDataBeans.size() > 3) {
            layout4.setVisibility(View.VISIBLE);
            tv41.setText(checkDataBeans.get(3).getStandard() + "");
            tv42.setText(checkDataBeans.get(3).getUnit() + "");
            tv43.setText(checkDataBeans.get(3).getPlanNum() + "");
            tv44.setText(checkDataBeans.get(3).getOne() == null ? "" : checkDataBeans.get(3).getOne());
            tv45.setText(checkDataBeans.get(3).getTwo() == null ? "" : checkDataBeans.get(3).getTwo());
            tv46.setText(checkDataBeans.get(3).getThree() == null ? "" : checkDataBeans.get(3).getThree());
        } else {
            layout4.setVisibility(View.GONE);
        }

        if (checkDataBeans.size() > 4) {
            layout5.setVisibility(View.VISIBLE);
            tv51.setText(checkDataBeans.get(4).getStandard() + "");
            tv52.setText(checkDataBeans.get(4).getUnit() + "");
            tv53.setText(checkDataBeans.get(4).getPlanNum() + "");
            tv54.setText(checkDataBeans.get(4).getOne() == null ? "" : checkDataBeans.get(4).getOne());
            tv55.setText(checkDataBeans.get(4).getTwo() == null ? "" : checkDataBeans.get(4).getTwo());
            tv56.setText(checkDataBeans.get(4).getThree() == null ? "" : checkDataBeans.get(4).getThree());
        } else {
            layout5.setVisibility(View.GONE);
        }

        if (checkDataBeans.size() > 5) {
            layout6.setVisibility(View.VISIBLE);
            tv61.setText(checkDataBeans.get(5).getStandard() + "");
            tv62.setText(checkDataBeans.get(5).getUnit() + "");
            tv63.setText(checkDataBeans.get(5).getPlanNum() + "");
            tv64.setText(checkDataBeans.get(5).getOne() == null ? "" : checkDataBeans.get(5).getOne());
            tv65.setText(checkDataBeans.get(5).getTwo() == null ? "" : checkDataBeans.get(5).getTwo());
            tv66.setText(checkDataBeans.get(5).getThree() == null ? "" : checkDataBeans.get(5).getThree());
        } else {
            layout6.setVisibility(View.GONE);
        }

        if (checkDataBeans.size() > 6) {
            layout7.setVisibility(View.VISIBLE);
            tv71.setText(checkDataBeans.get(6).getStandard() + "");
            tv72.setText(checkDataBeans.get(6).getUnit() + "");
            tv73.setText(checkDataBeans.get(6).getPlanNum() + "");
            tv74.setText(checkDataBeans.get(6).getOne() == null ? "" : checkDataBeans.get(6).getOne());
            tv75.setText(checkDataBeans.get(6).getTwo() == null ? "" : checkDataBeans.get(6).getTwo());
            tv76.setText(checkDataBeans.get(6).getThree() == null ? "" : checkDataBeans.get(6).getThree());
        } else {
            layout7.setVisibility(View.GONE);
        }

        if (checkDataBeans.size() > 7) {
            layout8.setVisibility(View.VISIBLE);
            tv81.setText(checkDataBeans.get(7).getStandard() + "");
            tv82.setText(checkDataBeans.get(7).getUnit() + "");
            tv83.setText(checkDataBeans.get(7).getPlanNum() + "");
            tv84.setText(checkDataBeans.get(7).getOne() == null ? "" : checkDataBeans.get(7).getOne());
            tv85.setText(checkDataBeans.get(7).getTwo() == null ? "" : checkDataBeans.get(7).getTwo());
            tv86.setText(checkDataBeans.get(7).getThree() == null ? "" : checkDataBeans.get(7).getThree());
        } else {
            layout8.setVisibility(View.GONE);
        }

        if (checkDataBeans.size() > 8) {
            layout9.setVisibility(View.VISIBLE);
            tv91.setText(checkDataBeans.get(8).getStandard() + "");
            tv92.setText(checkDataBeans.get(8).getUnit() + "");
            tv93.setText(checkDataBeans.get(8).getPlanNum() + "");
            tv94.setText(checkDataBeans.get(8).getOne() == null ? "" : checkDataBeans.get(8).getOne());
            tv95.setText(checkDataBeans.get(8).getTwo() == null ? "" : checkDataBeans.get(8).getTwo());
            tv96.setText(checkDataBeans.get(8).getThree() == null ? "" : checkDataBeans.get(8).getThree());
        } else {
            layout9.setVisibility(View.GONE);
        }


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
        spProject = findViewById(R.id.spProject);
        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDeletet);
        imgBtnSeekPic = findViewById(R.id.pic);
        mapView = findViewById(R.id.main_mapview);

        imgBtnSeekPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeekPictureActivity.class);
                // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                projectName = spProject.getSelectedItem().toString();
                String path = SD + APP_PAHT + "/" + bitProject + "/" + projectName + PICTURE;
                if (FileUtils.getInstance().getFoldeCount(path) != 0) {
                    initTempLocalXy();
                    intent.putExtra("path", path);
                    intent.putExtra("x",x);
                    intent.putExtra("y",y);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "此项目暂无照片", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    Toast.makeText(MainActivity.this, "你还没创建项目，不能删除操作", Toast.LENGTH_LONG).show();
                    return;
                }
                showDeletDialog();
//                BaiduMapOptions options = new BaiduMapOptions();
//                //设置地图模式为卫星地图
//                options.mapType(BaiduMap.MAP_TYPE_SATELLITE);

            }
        });

        //添加照片
        ImageButton btnAddImg = findViewById(R.id.btnAddPicture);
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() == 0) {
                    Toast.makeText(MainActivity.this, "你还没有创建项目，请先创建项目", Toast.LENGTH_LONG).show();
                    return;
                }
                //展示照片拍照选项
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

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CheckDataBean> checkDataBeans = daoSession.getCheckDataBeanDao().queryBuilder()
                        .where(CheckDataBeanDao.Properties.BitName.eq(bitProject)).list();
                String path = SD + APP_PAHT + "/" + bitProject + "/" + DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS) + ".xls";
                ExcelUtilsOfPoi.initExcel(MainActivity.this, path, checkDataBeans);

            }
        });

        Button btn = findViewById(R.id.btnClose);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout1.getVisibility() == View.VISIBLE){
                   setViewGone(View.GONE);
                   btn.setText("展开");
                }else {
                    setViewGone(View.VISIBLE);
                    btn.setText("收起");
                }
            }
        });
    }

    private void importExcel() {
        AlertDialogUtil.showDialog(MainActivity.this, "导入文件", "请选择导入的文件", false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FATH = QQ_FILE_PATH;
                startActivityForResult(new Intent(MainActivity.this, SelectExcelActivity.class), 1);
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
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
        builder.setMessage("确定删除该项目:" + bitProject + "及相关照片吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                daoSession.getProjectBitBeanDao().queryBuilder()
                        .where(ProjectBeanDao.Properties.BitName.eq(bitProject))
                        .buildDelete()
                        .executeDeleteWithoutDetachingEntities();

                projectlist.remove(bitProject);
                adapter.notifyDataSetChanged();
/*
                daoSession.getProjectBitBeanDao().queryBuilder().where(ProjectBitBeanDao.Properties.PrjName.eq(bitProject))
                        .buildDelete().executeDeleteWithoutDetachingEntities();
                projectlist.remove(bitProject);
                adapter.notifyDataSetChanged();*/

//                FileUtils.getInstance().deleteDir(SD + APP_PAHT + "/" + bitProject + "/" + spProject.getSelectedItem().toString());
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
        int fileIndexMax = FileUtils.getInstance().getFileIndexMax(SD + APP_PAHT + "/" + bitProject + "/" + projectName + PICTURE);
//        fileIndexMax++;
        //创建文件夹爱

        String[] str = new String[fileIndexMax];
        for (int i = 0; i < fileIndexMax; i++) {
            str[i] = String.valueOf(i+1);
        }
        //刷新定位
        initTempLocalXy();
       showDialog(str,"拍照路径");

    }

    private void openTakePhto(int fileIndexMax) {
        TakePhoFragment fragment = new TakePhoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("father", SD + APP_PAHT + "/" + bitProject + "/" + projectName + PICTURE);
        bundle.putString("child", String.valueOf(fileIndexMax));
        bundle.putDouble("x", x);
        bundle.putDouble("y", y);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager().beginTransaction(), "fragment");
    }

    /**
     * 自定义创建项目对话框
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
                List<ProjectBitBean> list2 = daoSession.getProjectBitBeanDao().queryBuilder()
                        .where(ProjectBitBeanDao.Properties.PrjName.eq(prjName)).build().list();

                if (list2.size() != 0 || prjName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "此项目名称已经存在", Toast.LENGTH_LONG).show();
                    return;

                } else {
                    ProjectBitBean projectBean = new ProjectBitBean();
                    projectBean.setDate(DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS));
                    projectBean.setPrjName(prjName);
                    daoSession.getProjectBitBeanDao().insert(projectBean);
                    //创建文件夹
                    FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + prjName);
                   /* //创建三个文件夹
                    FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + prjName + PICTURE + "/1");
                    FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + prjName + PICTURE + "/2");
                    FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + prjName + PICTURE + "/3");*/
                    projectlist.add(0, prjName);
                    bitProject = prjName;
                    spBitProject.setSelection(0);
                    adapter.notifyDataSetChanged();
                    listType.clear();
                    adapterType.notifyDataSetChanged();
                    list.clear();
                    arrayAdapter.notifyDataSetChanged();
                    setViewGone(View.GONE);

                    clearView();
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

    private void setViewGone(int i) {
        layout1.setVisibility(i);
        layout2.setVisibility(i);
        layout3.setVisibility(i);
        layout4.setVisibility(i);
        layout5.setVisibility(i);
        layout6.setVisibility(i);
        layout7.setVisibility(i);
        layout8.setVisibility(i);
        layout9.setVisibility(i);
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
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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

    /**
     * 多选
     * @Params :
     * @author :HaiRun
     * @date :2019/7/10  15:12
     */
    private void showDialog(final String[] data, String title) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, android.app.AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(title);
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        final boolean[] selectItems = new boolean[data.length];
        for (int i = 0; i < data.length; i++) {
            selectItems[i] = false;
            map.put(i, false);
        }

        /**
         * 第一个参数指定我们要显示的一组下拉多选框的数据集合
         * 第二个参数代表哪几个选项被选择，如果是null，则表示一个都不选择，如果希望指定哪一个多选选项框被选择，
         * 需要传递一个boolean[]数组进去，其长度要和第一个参数的长度相同，例如 {true, false, false, true};
         * 第三个参数给每一个多选项绑定一个监听器
         */
        builder.setMultiChoiceItems(data, selectItems, new DialogInterface.OnMultiChoiceClickListener() {
            StringBuffer sb = new StringBuffer(100);
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    //选择的选项保存到sb中
//                    sb.append(data[which] + "+");
                    map.put(which, true);
                }

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });


        builder.setNeutralButton("新增", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int i=  data.length + 1;
                FileUtils.getInstance().mkdirs(SD + APP_PAHT + "/" + bitProject + "/" + projectName + PICTURE + "/" + i);
                LogUtills.i("创建文件夹 = " +i);
                openTakePhto(i);
                dialog.dismiss();
            }
        });


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String path = "";
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)) {
                        path = (data[i]);
                    }
                }
                openTakePhto(Integer.valueOf(path));
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
