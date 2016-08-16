package com.dongxi.foodie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dongxi.foodie.R;


public class AccountActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        textView = (TextView) findViewById(R.id.dlbutton);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 判断请求码是否是启动活动B所用的请求码
        if (requestCode == 1) {
            // 判断返回码是否成功
            if(resultCode == RESULT_OK) {
                textView.setText("已设置");
                textView.setTextColor(Color.RED);
            }
        }
    }
}
