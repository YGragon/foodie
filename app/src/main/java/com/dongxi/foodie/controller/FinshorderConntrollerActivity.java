package com.dongxi.foodie.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.dongxi.foodie.R;
import com.dongxi.foodie.adapter.FinshorderAdapter;
import com.dongxi.foodie.bean.Finshorder;
import com.dongxi.foodie.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class FinshorderConntrollerActivity extends AppCompatActivity {
    private List<Finshorder> finshorderList = new ArrayList<Finshorder>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finshorder_conntroller);

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initFinshorders();
        FinshorderAdapter adapter = new FinshorderAdapter(UIUtils.getContext(),R.layout.list_finshorder,finshorderList);
        ListView listView = (ListView) findViewById(R.id.list_finshorder);
        listView.setAdapter(adapter);

    }

    private void initFinshorders() {

        Finshorder xlzb = new Finshorder(R.mipmap.xlzb,"小笼蒸包 ￥10","小笼蒸包，一般有蟹粉小笼，虾仁小笼等等，做法独特，是南方人爱吃的小吃。");
        finshorderList.add(xlzb);
        Finshorder szrp = new Finshorder(R.mipmap.szrp,"水煮肉片 ￥20","水煮肉片是一道汉族新创名菜，起源于重庆，发扬于西南，属于渝菜、川菜中著名的家常菜 ,肉味香辣，软嫩，易嚼。吃时肉嫩菜鲜 ，汤红油亮，麻辣味浓，最宜下饭，为家常美食之一。特色是\"麻、辣、鲜、香\"。");
        finshorderList.add(szrp);
        Finshorder yxrs = new Finshorder(R.mipmap.yxrs,"鱼香肉丝 ￥15","鱼香肉丝(英文名:Fish deer)是一道汉族传统名菜，以鱼香调味而得名,成菜色红润、肉嫩、质鲜、富鱼香味。其鱼香味并不来自鱼，而是由泡红辣椒、葱、姜、蒜、糖、盐、酱油等调味品调制而成。");
        finshorderList.add(yxrs);
        Finshorder pgf = new Finshorder(R.mipmap.pgf,"排骨饭 ￥15","排骨饭是砂锅饭里的代表作，也是\"先煮饭后放料\"煮法的代表。这种煮法最适合排骨类红肉有荤有素的搭配，而且最好多烧出些汁儿。因为七八成熟的米饭米粒之间空隙大，汁能很快渗入进味。");
        finshorderList.add(pgf);
    }
}
