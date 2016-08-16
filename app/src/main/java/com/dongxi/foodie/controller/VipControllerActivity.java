package com.dongxi.foodie.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.dao.DB;
import com.jaeger.library.StatusBarUtil;

public class VipControllerActivity extends AppCompatActivity implements View.OnClickListener {

    public static  int  flag = 0;
    //Vipdao vipdao;
    DB vipdb;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_controller);

        vipdb = new DB(VipControllerActivity.this,"vips.db",null,1);
        db = vipdb.getWritableDatabase();

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.vip_tv).setOnClickListener(this);

        setStatusBar();

    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                if(flag > 0){
                    Toast.makeText(this,"您已经开通过会员了！", Toast.LENGTH_SHORT).show();
                }else {
                    insert(1);
                    Toast.makeText(this, "恭喜您开通1个月的会员", Toast.LENGTH_SHORT).show();
                }
                flag = 1;
                break;
            case R.id.btn2:
                if(flag > 0){
                    Toast.makeText(this,"您已经开通过会员了！", Toast.LENGTH_SHORT).show();
                }else {
                    insert(6);
                    Toast.makeText(this, "恭喜您开通半年的会员", Toast.LENGTH_SHORT).show();
                }
                flag = 6;
                break;
            case R.id.btn3:
                if(flag > 0){
                    Toast.makeText(this,"您已经开通过会员了！", Toast.LENGTH_SHORT).show();
                }else {
                    insert(12);
                    Toast.makeText(this, "恭喜您开通1年的会员", Toast.LENGTH_SHORT).show();
                }
                flag=12;
                break;
            case R.id.vip_tv:
                startActivity(new Intent(this,vipinfo.class));

        }



    }

    public void insert(int time){
        ContentValues values = new ContentValues();
        values.put("name","天堑无涯");
        values.put("time",time);
        values.put("userid", 10);
        db.insert("vip", null, values);
    }


    /*public class Vipdao{
        DB vipdb;
        SQLiteDatabase db;

        public Vipdao(){
            vipdb = new DB(getApplicationContext(),"vip.db",null,1);
            db = vipdb.getWritableDatabase();
            //createTable();
        }

        public void close(){
            vipdb.close();
        }

        public void createTable(){
            db.execSQL("create table vip(_id integer primary key autoincrement," +
                    "name char(10),time integer(10),userid integer(3))");
        }

        public void insert(int time){
            ContentValues values = new ContentValues();
            values.put("name","天堑无涯");
            values.put("time",time);
            values.put("userid", 10);
            db.insert("vip", null, values);
        }

        public void search(){
            Cursor cursor = db.query("vip",null,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String userid = cursor.getString(cursor.getColumnIndex("userid"));
                System.out.println(name+":"+time+":"+userid);
            }
        }

        public void searchone(Integer id){
            Cursor cursor = db.query("vip",null,"where _id = "+id.toString(),null,null,null,null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String userid = cursor.getString(cursor.getColumnIndex("userid"));
                System.out.println(name+":"+time+":"+userid);
            }
        }
    }*/
}
