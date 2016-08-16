package com.dongxi.foodie.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.dongxi.foodie.R;
import com.dongxi.foodie.adapter.unfinshorderAdapter;
import com.dongxi.foodie.bean.Unfinshorder;

import java.util.ArrayList;
import java.util.List;

public class UnfinsheorderControllerActivity extends AppCompatActivity {

    private List<Unfinshorder> unfinshorderList = new ArrayList<Unfinshorder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfinsheorder_controller);

        Button back_all = (Button) findViewById(R.id.back_all);
        back_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initunfinshorders();
        unfinshorderAdapter adapter = new unfinshorderAdapter(UnfinsheorderControllerActivity.this, R.layout.list_unorder, unfinshorderList);
        ListView listView = (ListView) findViewById(R.id.list_unfinshorder);
        listView.setAdapter(adapter);
    }

    private void initunfinshorders() {
        Button button1 = (Button) findViewById(R.id.btn_add);
        Unfinshorder dpj = new Unfinshorder(R.mipmap.dpj, "大盘鸡 ￥25", "大盘鸡是新疆地区名菜，大约起源于80年代后期，主要用料为鸡块和土豆块，配皮带面烹饪而成。新疆大盘鸡色彩鲜艳，有爽滑麻辣的鸡肉和软糯甜润的土豆，辣中有香，粗中带细，而且经济实惠，亲朋聚会食用尚家。", button1);
        unfinshorderList.add(dpj);
        Button button2 = (Button) findViewById(R.id.btn_add);
        Unfinshorder gbjd = new Unfinshorder(R.mipmap.gbjd, "宫保鸡丁 ￥20", "宫保鸡丁是川菜中最具代表性的菜品之一，创始人为贵州织金人时任四川总督丁宝桢，在任四川总督时创制该菜，流传至今。", button2);
        unfinshorderList.add(gbjd);
        Button button3 = (Button) findViewById(R.id.btn_add);
        Unfinshorder hmj = new Unfinshorder(R.mipmap.hmj, "黄焖鸡 ￥15", "黄焖鸡米饭又叫香鸡煲，浓汁鸡煲饭。是历史传统名吃，起源于济南名店\"吉玲园\"。是山东济南汉族传统名菜之一，属于鲁菜系家常菜品，主要食材是鸡腿肉，配以青椒、香菇等焖制而成，味道美妙，具有肉质鲜美嫩滑的特点。", button3);
        unfinshorderList.add(hmj);
        Button button4 = (Button) findViewById(R.id.btn_add);
        Unfinshorder hsjk = new Unfinshorder(R.mipmap.hsjk, "红烧鸡块 ￥20", "红烧鸡块是日常生活中常见的菜肴。炒制程序复杂，用料要求严格，操作工艺精细，是餐厅和饭店必备的一道菜肴，鸡肉肉质细嫩，滋味鲜美，由于其味较淡，因此可使用于各种料理中。", button4);
        unfinshorderList.add(hsjk);
        Button button5 = (Button) findViewById(R.id.btn_add);
        Unfinshorder szy = new Unfinshorder(R.mipmap.szy, "水煮鱼 ￥25", "水煮鱼又称江水煮江鱼，是一道重庆市的汉族创新名菜，属于重庆渝北风味,选新鲜生猛活鱼，又充分发挥辣椒御寒、益气养血功效，烹调出来的肉质一点也不会变韧，口感滑嫩，油而不腻。", button5);
        unfinshorderList.add(szy);
    }
}
