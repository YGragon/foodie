package com.dongxi.foodie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dongxi.foodie.R;
import com.jaeger.library.StatusBarUtil;

public class RegistActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        setStatusBar();
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

}
