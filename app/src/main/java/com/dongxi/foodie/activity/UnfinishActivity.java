package com.dongxi.foodie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dongxi.foodie.R;


public class UnfinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfinish);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        TextView textName = (TextView)findViewById(R.id.tv_name);
        TextView tv_food_number = (TextView)findViewById(R.id.tv_food_number);

        textName.setText(name);
        tv_food_number.setText("数量：" + number);

    }

}
