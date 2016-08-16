package com.dongxi.foodie.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dongxi.foodie.R;

import shem.com.materiallogin.MaterialLoginView;
import shem.com.materiallogin.MaterialLoginViewListener;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
        if (login != null) {
            login.setListener(new MaterialLoginViewListener() {
                @Override
                public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                    String user = registerUser.getEditText().getText().toString();
                    if (user.isEmpty()) {
                        registerUser.setError("用户名不能为空哟");
                        return;
                    }
//                    registerUser.setError("");

                    String pass = registerPass.getEditText().getText().toString();
                    if (pass.isEmpty()) {
                        registerPass.setError("密码不能为空哟");
                        return;
                    }
//                    registerPass.setError("");

                    String passRep = registerPassRep.getEditText().getText().toString();
                    if (!pass.equals(passRep)) {
                        registerPassRep.setError("两次密码不一致");
                        return;
                    }

                    Toast.makeText(LoginActivity.this, "注册成功!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                }

                @Override
                public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                    String user = loginUser.getEditText().getText().toString();
                    if (user.isEmpty()) {
                        loginUser.setError("用户名不能为空哟");
                        return;
                    }
//                    loginUser.setError("");

                    String pass = loginPass.getEditText().getText().toString();
                    if (!pass.equals(user)) {
                        loginPass.setError("密码和用户名不一致");
                        return;
                    }
//                    loginPass.setError("");

                    Snackbar.make(login, "登录成功!", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            });
        }
    }
}
