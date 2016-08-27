package com.dongxi.foodie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;

import org.xutils.x;

public class QuestionDetailActivity extends AppCompatActivity {
    private ImageView iv_question_image ;
    private TextView tv_question_title;
    private TextView tv_question_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        initView() ;
        Intent intent = getIntent() ;
        String question_image = intent.getStringExtra("question_image");
        String question_title = intent.getStringExtra("question_title");
        String question_description = intent.getStringExtra("question_Desc");

        tv_question_title.setText(question_title);
        tv_question_description.setText(question_description);
        x.image().bind(iv_question_image, "http://tnfs.tngou.net/img" + question_image);

        //隐藏标题栏，实现类似与沉浸式状态栏
        View decorView=getWindow().getDecorView() ;
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN ;
        decorView.setSystemUiVisibility(option);

    }
    private void initView() {
        iv_question_image = (ImageView)findViewById(R.id.iv_question_image) ;
        tv_question_title = (TextView) findViewById(R.id.tv_question_title);
        tv_question_description = (TextView) findViewById(R.id.tv_question_description);
    }
}
