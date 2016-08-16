package com.dongxi.foodie.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：Aller  2016/5/1 21:32
 *
 * 邮箱：1105894953@qq.com
 *
 * 描述：SharedPreferences的封装
 */
public class PrefUtils {

    public static final String PREF_NAME = "config" ;
    public static boolean getBoolean(Context ctx ,String key, boolean defaultValue){
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) ;
        return sp.getBoolean(key, defaultValue) ;
    }
    public static void setBoolean(Context ctx ,String key, boolean value){
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) ;
       sp.edit().putBoolean(key, value).commit() ;
    }
}
