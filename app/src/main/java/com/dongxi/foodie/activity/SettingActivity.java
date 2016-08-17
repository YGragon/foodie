package com.dongxi.foodie.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.controller.DataCleanManager;
import com.jaeger.library.StatusBarUtil;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //滑动开关更新
        CompoundButton switch_update = (CompoundButton) findViewById(R.id.switch_update);
        switch_update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingActivity.this, "开启自动更新", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SettingActivity.this, "关闭自动更新", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //关于我们按钮的监听器
        TextView about = (TextView)findViewById(R.id.tv_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutUSActivity.class));
            }
        });

        //联系我们监听器
        findViewById(R.id.tv_advice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                //    设置Title的图标
                builder.setTitle("联系方式");
                //    设置Content来显示一个信息
                builder.setMessage("邮箱：Aller_Dong@163.com");
                //    设置一个PositiveButton
                builder.show();
            }
        });

        //清理缓存
        CompoundButton switch_clean = (CompoundButton) findViewById(R.id.switch_clean);
        String size = null;
        try {
            size = new DataCleanManager().getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String finalSize = size;
        switch_clean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!isChecked) {
                        DataCleanManager.clearAllCache(SettingActivity.this);
                        Toast.makeText(SettingActivity.this, "共清理了" + finalSize, Toast.LENGTH_SHORT).show();
                    }
            }
        });

        //返回
        TextView setbackbtn = (TextView)findViewById(R.id.setbackbtn);
        setbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //退出登录
        findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(SettingActivity.this,LoginActivity.class));
            }
        });

        //沉浸式状态栏
        setStatusBar();
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
