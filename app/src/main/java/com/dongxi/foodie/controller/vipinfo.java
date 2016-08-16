package com.dongxi.foodie.controller;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class vipinfo extends Activity {

    Date date ;
    Date date1 ;
    SimpleDateFormat sdf;
    String  fdate = null;
    String  fdate1 = null;
    long ms ;

    String t = null;
    long time ;
    long totol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vipinfo);

        TextView kai_tv = (TextView) findViewById(R.id.kait);
        TextView start_tv = (TextView) findViewById(R.id.start);
        TextView end_tv = (TextView) findViewById(R.id.end);

        ms = System.currentTimeMillis();
        date = new Date (ms);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fdate = sdf.format(date);
        start_tv.setText("起始时间： "+fdate);

        /*t = search(name);*/
        t= String.valueOf(VipControllerActivity.flag);
        kai_tv.setText("开通时长： "+t+"个月");

        time = Long.parseLong(t);
        totol = ms + time * 30 * 24 * 3600000;
        fdate1 = sdf.format(totol);
        end_tv.setText("到期时间： "+fdate1);

        setStatusBar();
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
