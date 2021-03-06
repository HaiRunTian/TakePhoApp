package com.alan.hairun.takephoapp.utils;


import android.content.Context;
import android.widget.Toast;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author HaiRun
 * @time 2019/5/16.11:31
 * 软件权限使用控制，通过时间控制
 */

public class LimitByTimeUtil {

    private static LimitByTimeUtil instance = null;
    private Context context;
    public synchronized static LimitByTimeUtil ins(Context context) {
        if (instance == null) {
            instance = new LimitByTimeUtil(context);

        }
        return instance;
    }

    public LimitByTimeUtil(Context context) {
        this.context = context;
    }

    /**
     * 系统时间判断
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @return
     * @author jqlin
     */
    public boolean isEffectiveDate(String currTime) {
        String format = "yyyy-MM-dd HH:mm:ss";
        Date nowTime = null;
        Date startTime = null;
        Date endTime = null;
        try {
            nowTime = new SimpleDateFormat(format).parse(currTime);
            startTime = new SimpleDateFormat(format).parse("2020-01-01 00:00:00");
            endTime = new SimpleDateFormat(format).parse("2021-10-31 23:59:59");

            LogUtills.i("LimitByTimeUtil", nowTime.toString() + "-------" + startTime.toString() + "------" + endTime.toString());
            if (nowTime.getTime() == startTime.getTime()
                    || nowTime.getTime() == endTime.getTime()) {
                return true;
            }

            Calendar date = Calendar.getInstance();
            date.setTime(nowTime);

            Calendar begin = Calendar.getInstance();
            begin.setTime(startTime);

            Calendar end = Calendar.getInstance();
            end.setTime(endTime);

            if (date.after(begin) && date.before(end)) {
                long time = nowTime.getTime();
                long time1 = endTime.getTime();
                long temp = time1 - time;
                LogUtills.i("LimitByTimeUtil",time +"--------" + time1 + "----------" + temp);
                //10天时间
                long jumpTime = 1000 * 60 * 60 * 24 * 3;
                if (temp < jumpTime) {
                    int i = (int) (temp / (60 * 60 * 24 *1000));
//                    LogUtills.i("LimitByTimeUtil","剩余时间 = " + i + "天");
                   Toast.makeText(context, "软件使用时间还有" + i + "天到期，请联系技术人员",Toast.LENGTH_LONG).show();
                }
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取网络时间
     *
     * @param listen
     */
    public void getTimeFromNet(final IGetNetTime listen) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //取得资源对象
                URL url = null;
                try {
                    //http://www.baidu.com 百度
                    // http://www.ntsc.ac.cn 中国科学院国家授时中心
                    url = new URL("http://www.baidu.com");
                    //生成连接对象
                    URLConnection baidu = url.openConnection();
                    baidu.setConnectTimeout(5000);
                    baidu.setReadTimeout(5000);
                    //发出连接
                    baidu.connect();
                    long time13 = baidu.getDate();
                    //取得网站日期时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    String currentTime = sdf.format(new Date(time13));
                    LogUtills.e("当前时间点 = " + currentTime);
                    listen.getNetTimeOk(currentTime);
//                    System.out.println("当前时间：" + currentTime);
                    //  设置系统时间
//                    setTime(currentTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtills.e(e.toString());
                }
            }
        }).start();

    }

}
