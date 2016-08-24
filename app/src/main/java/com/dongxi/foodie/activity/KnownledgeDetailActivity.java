package com.dongxi.foodie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;

import org.xutils.x;

public class KnownledgeDetailActivity extends AppCompatActivity {

    private ImageView iv_knownledge_image;
    private TextView tv_knownledge_material;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knownledge_detail);
        initView() ;
        Intent intent = getIntent() ;
        String knownledge_image = intent.getStringExtra("knownledge_image");
        String knownledge_Description = intent.getStringExtra("knownledge_Description");

        x.image().bind(iv_knownledge_image, "http://tnfs.tngou.net/img" + knownledge_image);
        tv_knownledge_material.setText(knownledge_Description) ;

        //隐藏标题栏，实现类似与沉浸式状态栏
        View decorView=getWindow().getDecorView() ;
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN ;
        decorView.setSystemUiVisibility(option);
    }
    private void initView() {
        iv_knownledge_image = (ImageView) findViewById(R.id.iv_knownledge_image);
        tv_knownledge_material = (TextView) findViewById(R.id.tv_knownledge_material);
    }
}
