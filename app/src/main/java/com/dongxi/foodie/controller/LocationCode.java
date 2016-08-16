package com.dongxi.foodie.controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.dongxi.foodie.fragment.FindFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephen on 2016/7/24.
 */
public class LocationCode extends Service implements AMapLocationListener{
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient = null;
    String city = null; String district = null;
    String street = null; String streetNum = null;

    public static  Map<String,Double> map = new HashMap<String,Double>();
    double latitude ; double longitude ;

    /*private LocalReceiver localReceiver = new LocalReceiver();
    IntentFilter intentFilter = new IntentFilter();
    private LocalBroadcastManager localBroadcastManager;*/
    //Bundle bundle ;
    //Intent intent = new Intent(MY_ACTION);
    //public static final String MY_ACTION = "location";




    /*android.os.Handler handler;
    Message msg;*/

    @Override
    public void onCreate() {
        super.onCreate();
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        mlocationClient.startLocation();
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                latitude = amapLocation.getLatitude();//获取纬度
                longitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                map.put("latitude",latitude);
                map.put("longitude",longitude);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                /*amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息*/
                city = amapLocation.getCity();//城市信息
                district = amapLocation.getDistrict();//城区信息
                street = amapLocation.getStreet();//街道信息
                streetNum = amapLocation.getStreetNum();//街道门牌号信息
                /*amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码*/
                //amapLocation.getAOIName();//获取当前定位点的AOI信息
                System.out.println(city);
                System.out.println(district);
                FindFragment.locmap.put("city", city);
                FindFragment.locmap.put("district",district);
                FindFragment.locmap.put("street",street);
                FindFragment.locmap.put("streetNum",streetNum);
                System.out.println(FindFragment.locmap.isEmpty());
                System.out.println(FindFragment.locmap.get("streetNum"));

                /*intent = new Intent(MY_ACTION);*/
                /*intent = new Intent(LocationCode.this,FindFragment.class);*/
                /*intent.putExtra("city", city);
                intent.putExtra("district", district);
                intent.putExtra("street", street);
                intent.putExtra("streetNum", streetNum);

                localBroadcastManager.sendBroadcast(intent);*/
               /*bundle.putString("city", city);
                bundle.putString("district", district);
                intent.putExtra("address",bundle);*/
                /*msg = new Message();
                msg.what = 1;
                msg.setData(bundle);
                handler.sendMessage(msg);*/
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //localBroadcastManager.unregisterReceiver(localReceiver);
        System.out.println("service finish");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


