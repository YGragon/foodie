package com.dongxi.foodie.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.Store;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ConvenienceStoreActivity extends AppCompatActivity {

    List<Store> storeInfos = new ArrayList<Store>();//声明全局的才有效果
    private ListView lv_store_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenience_store);

        lv_store_list = (ListView)findViewById(R.id.lv_store_list);
        lv_store_list.setAdapter(new StoreAdapter());

        setStatusBar() ;

        getDataFromServer();

    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }


    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

        //聚合数据API,通过经纬度和查询半径找
        RequestParams params = new RequestParams("http://apis.juhe.cn/catering/query?key=3ed511c4c356963187e75d258891e157&lng=119.57&lat=39.95&radius=3000");
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params,new Callback.CommonCallback<String>(){

            @Override
            public void onSuccess(String result) {

//                Toast.makeText(ConvenienceStoreActivity.this, result, Toast.LENGTH_LONG).show();//拿到数据

                parseData(result);//解析数据


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 解析网络数据
     * @param json
     */
    public List<Store> parseData(String json) {

//     List<Store> storeInfos=new ArrayList<Store>();//不能在这声明
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String name = jsonObj.getString("name");
                float stars=Float.parseFloat(jsonObj.getString("stars"));
                String photos = jsonObj.getString("photos");
                Store info=new Store(name,stars,photos);
                storeInfos.add(info);
            }

            return storeInfos;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class StoreAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return storeInfos.size();
        }

        @Override
        public Store getItem(int position) {
            return storeInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(ConvenienceStoreActivity.this, R.layout.list_store_item, null);
                holder=new ViewHolder();
                holder.tv_name = (TextView) view.findViewById(R.id.tv_Store_name);
                holder.ratingBar = (RatingBar) view.findViewById(R.id.item_rating);
                holder.iv_store = (ImageView) view.findViewById(R.id.iv_store);

                //还没刷新，不能放这

                view.setTag(holder);
            }else{
                view = convertView ;
                holder = (ViewHolder)view.getTag();
            }
            Store storeData = storeInfos.get(position);

            holder.tv_name.setText(storeData.getName());// 设置美食的名字
            float stars = storeData.getStars();
            holder.ratingBar.setRating(stars); // 设置ratingBar的值

            String photoUrl = storeData.getPhotos();
            x.image().bind(holder.iv_store, photoUrl);

            return view;
        }
    }

    static class ViewHolder{
        ImageView iv_store;
        TextView tv_name;
        RatingBar ratingBar;
    }
}
