package com.dongxi.foodie.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.utils.UIUtils;
import com.jaeger.library.StatusBarUtil;

import org.xutils.x;

public class ShowOrderActivity extends AppCompatActivity {
    private String name;

    Button btn_add,btn_subtract;
    TextView text_number;

    private static final int min = 0;   //最小值
    private static final int max = 10;  //最大值
    private int value = 0;    //默认值,也是随点击而改变的值

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView textName;
    private TextView textCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        String count = bundle.getString("count");
        String image = bundle.getString("image");

        textName = (TextView)findViewById(R.id.tv_name);
        textName.setText(name);

        textCount = (TextView)findViewById(R.id.tv_count);
        textCount.setText(count);

        text_number = (TextView)findViewById(R.id.text_number);

        ImageView iv_food=(ImageView)findViewById(R.id.iv_food);//给图片设置路径
        x.image().bind(iv_food, "http://tnfs.tngou.net/img" + image);


        Button buy_food = (Button)findViewById(R.id.food_list_take_order_button);
        buy_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay = new Intent(ShowOrderActivity.this, UnfinishActivity.class);
                pay.putExtra("name",name);
                pay.putExtra("number",text_number.getText().toString());
                Toast.makeText(ShowOrderActivity.this,"在未完成订单中可以结算哦！",Toast.LENGTH_LONG).show();
                finish();
            }
        });

        //拿到id
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_subtract =(Button)findViewById(R.id.btn_subtract);
        text_number = (TextView)findViewById(R.id.text_number);


        text_number.setText(String.valueOf(value));
        btn_subtract.setBackgroundResource(R.drawable.rounded_rectangle_left_unclickable);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value == max) {
                    Toast.makeText(UIUtils.getContext(), "已达到最大值", Toast.LENGTH_SHORT).show();
                } else {
                    value++;
                    if (value == max) {
                        btn_add.setBackgroundResource(R.drawable.rounded_rectangle_right_unclickable);
                    } else {

                        text_number.setText(String.valueOf(value));
                        btn_subtract.setBackgroundResource(R.drawable.selecter_left);
                    }
                }
            }
        });
        btn_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value == min) {
                    Toast.makeText(UIUtils.getContext(), "已达到最小值", Toast.LENGTH_SHORT).show();
                } else {
                    value--;
                    if (value == min) {
                        btn_subtract.setBackgroundResource(R.drawable.rounded_rectangle_left_unclickable);
                    } else {

                        text_number.setText(String.valueOf(value));
                        btn_add.setBackgroundResource(R.drawable.selecter_right);
                    }
                }
            }
        });

        //沉浸式状态栏
        setStatusBar();
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
