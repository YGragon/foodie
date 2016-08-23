package com.dongxi.foodie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;

import org.xutils.x;

public class FoodDetailActivity extends AppCompatActivity {

    private ImageView iv_food_details;
    private TextView tv_food_material;
    private TextView tv_food_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        initView() ;
        Intent intent = getIntent() ;
        String food_image = intent.getStringExtra("food_image");
        String food_material = intent.getStringExtra("food_Material");
        String food_description = intent.getStringExtra("food_Description");

        x.image().bind(iv_food_details, "http://tnfs.tngou.net/img" + food_image);
        tv_food_material.setText(food_material) ;
        tv_food_desc.setText(food_description);

        //隐藏标题栏，实现类似与沉浸式状态栏
        View decorView=getWindow().getDecorView() ;
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN ;
        decorView.setSystemUiVisibility(option);
    }

    private void initView() {
        iv_food_details = (ImageView) findViewById(R.id.iv_food_details);
        tv_food_material = (TextView) findViewById(R.id.tv_food_material);
        tv_food_desc = (TextView) findViewById(R.id.tv_food_desc);
    }
}
