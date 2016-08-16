package com.dongxi.foodie.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：Aller  2016/7/20 09:24
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class DB extends SQLiteOpenHelper{

    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table vip(name varchar(10),time int(10),userid int(3))");
        //createTable(db);
        System.out.println("jjjjjjjjjjjjjjjjjj");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(SQLiteDatabase db){
        db.execSQL("create table vip(_id integer primary key autoincrement, name varchar(10),time integer(10),userid integer(3))");
    }

}
