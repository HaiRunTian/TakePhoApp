package com.alan.hairun.takephoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alan.hairun.gen.DaoSession;
import com.alan.hairun.takephoapp.bean.UserBean;
import com.alan.hairun.takephoapp.config.MyApplication;
import com.alan.hairun.takephoapp.utils.DateTimeUtil;
import com.alan.hairun.takephoapp.utils.IGetNetTime;
import com.alan.hairun.takephoapp.utils.LimitByTimeUtil;
import com.alan.hairun.takephoapp.utils.LogUtills;
import com.alan.hairun.takephoapp.utils.NetUtils;
import com.alan.hairun.takephoapp.utils.PermissionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Alan
 * @date: 2020/5/8 0008
 * @time: 下午 11:20
 * @deprecated:
 */
public class LoginActivity extends AppCompatActivity implements IGetNetTime {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.cb)
    CheckBox cb;
    private DaoSession daoSession;
    private String nowTime = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        PermissionUtils.initPermission(this, new PermissionUtils.PermissionHolder());
//        LimitByTimeUtil.ins(this).getTimeFromNet(this);

        initData();
        Button button = findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //无网络用系统时间判断
                if (!LimitByTimeUtil.ins(LoginActivity.this).isEffectiveDate(DateTimeUtil.setCurrentTime(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS))) {
                    Toast.makeText(LoginActivity.this, "软件试用期已经过期，请购买此软件", Toast.LENGTH_LONG).show();
                    return;
                }

                List<UserBean> userBeans = daoSession.getUserBeanDao().loadAll();
                String userName = username.getText().toString();
                String password2 = password.getText().toString();

                if (userBeans.size() > 0) {
                    UserBean userBean = userBeans.get(0);
                    String userName2 = userBean.getUserName();
                    String userPasswrod = userBean.getUserPasswrod();
                    if (userName2.equals(userName) && userPasswrod.equals(password2)) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名和密码不正确，请再次确认", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    private void initData() {
        daoSession = MyApplication.getINSTANT().getDaoSession();
        List<UserBean> userBeans = daoSession.getUserBeanDao().loadAll();
        if (userBeans.size() > 0) {
            UserBean userBean = userBeans.get(0);
            username.setText(userBean.getUserName());
            if (userBean.getSex().equals("0")) {
                password.setText(userBean.getUserPasswrod());
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
                password.setText("");
            }
        } else {
            //生成6个随机数字 密码是每个随机数+8 超过10就取个位数 保存数据库
            int a = getRandom();
            int b = getRandom();
            int c = getRandom();
            int d = getRandom();
            int e = getRandom();
            int f = getRandom();

            String loginName = String.valueOf(a) + String.valueOf(b)
                    + String.valueOf(c) + String.valueOf(d)
                    + String.valueOf(e) + String.valueOf(f);

            username.setText(loginName);

            int a1 = a + 8 > 9 ? (a + 8) % 10 : a + 8;
            int b1 = b + 8 > 9 ? (b + 8) % 10 : b + 8;
            int c1 = c + 8 > 9 ? (c + 8) % 10 : c + 8;
            int d1 = d + 8 > 9 ? (d + 8) % 10 : d + 8;
            int e1 = e + 8 > 9 ? (e + 8) % 10 : e + 8;
            int f1 = f + 8 > 9 ? (f + 8) % 10 : f + 8;

            String loginPasword = String.valueOf(a1) + String.valueOf(b1)
                    + String.valueOf(c1) + String.valueOf(d1)
                    + String.valueOf(e1) + String.valueOf(f1);

//            password.setText(loginName);
            UserBean userBean = new UserBean();
            userBean.setUserName(loginName);
            userBean.setUserPasswrod(loginPasword);
            userBean.setSex("0");
            daoSession.getUserBeanDao().insert(userBean);

            LogUtills.i("password = " + loginPasword);

        }
    }

    /**
     * 生成随机数
     *
     * @author: Alan
     * created at: 2020/6/3 0003 下午 9:18
     * @deprecated :
     */
    private int getRandom() {
        int max = 9, min = 0;
        int ran2 = (int) (Math.random() * (max - min) + min);
        return ran2;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getNetTimeOk(String time) {
        nowTime = time;
    }

    @Override
    public void getNetTimeFail(String fail) {

    }
}
