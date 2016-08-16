package com.dongxi.foodie.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.dongxi.foodie.R;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class PhoneLoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_phone_login);

        SMSSDK.initSDK(this, "1592b90d8e148", "fb566c09d0384f41fe8a1a117f79d4f6");

        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息
                    registerUser(country, phone);
                    //传递数据给侧边栏
//                    Intent post = new Intent(PhoneLoginActivity.this,AccountActivity.class);
//                    post.putExtra("phone", phone);
//                    startActivity(post);
//                    finish();
                }
            }
        });
        registerPage.show(PhoneLoginActivity.this);
    }

    //自己实现的方法
    protected void registerUser(String country, String phone) {
        String uid = "123";
        String nickName = "aller";
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }

}
