package com.dongxi.foodie.view;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.dongxi.foodie.BuildConfig;
import com.thinkland.sdk.util.CommonFun;

import org.xutils.x;

import cn.smssdk.SMSSDK;

/**
 * 代表当前应用程序
 * @author Aller
 *
 */
public class BaseApplication extends Application {
	private static BaseApplication application;
	private static int mainTid;
	private static Handler handler;
	@Override
//  在主线程运行的
	public void onCreate() {
		super.onCreate();


		x.Ext.init(this);
		x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

		application=this;
		mainTid = android.os.Process.myTid();
		handler=new Handler();



	}
	public static Context getApplication() {
		return application;
	}
	public static int getMainTid() {
		return mainTid;
	}
	public static Handler getHandler() {
		return handler;
	}


}
