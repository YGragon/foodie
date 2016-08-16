package com.dongxi.foodie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.jaeger.library.StatusBarUtil;
import com.lljjcoder.citypickerview.widget.CityPickerView;

public class PayActivity extends AppCompatActivity {


    private EditText et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        TextView textName = (TextView)findViewById(R.id.tv_name);
        TextView tv_food_number = (TextView)findViewById(R.id.tv_food_number);

        textName.setText("美食名字："+name);
        tv_food_number.setText("数量："+number);

        et_address = (EditText)findViewById(R.id.et_address);

        findViewById(R.id.btn_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPickerView cityPickerView = new CityPickerView(PayActivity.this);
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县
                        String district = citySelected[2];

                        et_address.setText(province + city + district );

                    }
                });
                cityPickerView.setTextColor(Color.BLUE);//新增文字颜色修改
                cityPickerView.setTextSize(20);//新增文字大小修改
                cityPickerView.setVisibleItems(5);//新增滚轮内容可见数量
                cityPickerView.setIsCyclic(true);//滚轮是否循环滚动
                cityPickerView.show();
            }
        });

        Button buy_food = (Button)findViewById(R.id.btn_list);
        buy_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PayActivity.this,"正在结算...",Toast.LENGTH_LONG).show();
            }
        });

        setStatusBar();
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
