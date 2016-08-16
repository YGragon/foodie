package com.dongxi.foodie.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.controller.DataCleanManager;
import com.jaeger.library.StatusBarUtil;

public class SettingActivity extends AppCompatActivity {

    String size;
    TextView cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //显示缓存大小
        cache=(TextView)findViewById(R.id.cachesize);
        try {
            size = new DataCleanManager().getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cache.setText(size);

        //关于我们按钮的监听器
        Button about = (Button)findViewById(R.id.aboutbtn);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutUSActivity.class));
            }
        });

        //联系我们监听器
        findViewById(R.id.advicebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                //    设置Title的图标
                builder.setTitle("联系方式");
                //    设置Content来显示一个信息
                builder.setMessage("邮箱：xu__kunfeng@163.com" + "\n" + "电话：0335-88888");
                //    设置一个PositiveButton
                builder.show();
            }
        });

        //清理缓存监听器
        findViewById(R.id.cleancache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(SettingActivity.this);
                try {
                    size = new DataCleanManager().getTotalCacheSize(SettingActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cache.setText(size);
            }
        });

        //返回
        Button setbackbtn = (Button)findViewById(R.id.setbackbtn);
        setbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setStatusBar();
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.set:
//                /**
//                 * 判断两次密码的输入是否一致，一致则设置成功
//                 *
//                 */
//                if(passwd.getText().toString().equals(comPasswd.getText().toString())) {
//                    Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent();
//                    this.setResult(RESULT_OK, intent);
//                    finish();
//                }else{
//                    Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
//                }
//                break ;
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case 0:
//                if (resultCode==RESULT_OK){
//                    String returnData = data.getStringExtra("data_return") ;
//                }
//                break;
//        }
//    }
}
