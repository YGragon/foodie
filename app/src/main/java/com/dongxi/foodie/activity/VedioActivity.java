package com.dongxi.foodie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.VedioInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 爽看视频，数据采用干活集中营的数据
 */
public class VedioActivity extends AppCompatActivity {

    private ListView lv_vedio;
    List<VedioInfo> vedioInfos = new ArrayList<VedioInfo>();
    private VedioInfo vedioInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);

        lv_vedio = (ListView) findViewById(R.id.lv_vedio);
        lv_vedio.setAdapter(new VedioAdapter());

        getDataFromServer();

        lv_vedio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将视频地址添加给每个item然后跳转网页
                String uriStr = vedioInfos.get(position).getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        ( Uri.parse(uriStr))
                ).addCategory(Intent.CATEGORY_BROWSABLE)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        //聚合数据API,通过经纬度和查询半径找
        RequestParams params = new RequestParams("http://gank.io/api/search/query/listview/category/%E4%BC%91%E6%81%AF%E8%A7%86%E9%A2%91/count/20/page/1");
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                Log.d("VedioActivity",result);//有结果
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
     * @param result
     */
    private void parseData(String result) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String desc = jsonObj.getString("desc");
                String publishedAt = jsonObj.getString("publishedAt");
                String url = jsonObj.getString("url");
                String who = jsonObj.getString("who");
                VedioInfo info = new VedioInfo(desc, publishedAt, url, who);
                vedioInfos.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        private class VedioAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return vedioInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return vedioInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder viewHolder ;
            if (convertView == null) {
                view = View.inflate(VedioActivity.this, R.layout.item_vedio_list, null);
                viewHolder=new ViewHolder();
                viewHolder.tv_vedio_dec = (TextView) view.findViewById(R.id.tv_vedio_dec);
                viewHolder.tv_vedio_author = (TextView) view.findViewById(R.id.tv_vedio_author);
                viewHolder.tv_vedio_time = (TextView) view.findViewById(R.id.tv_vedio_time);

                view.setTag(viewHolder);
            }else{
                view = convertView ;
                viewHolder = (ViewHolder) view.getTag();
            }
            vedioInfo = vedioInfos.get(position);
            viewHolder.tv_vedio_dec.setText(vedioInfo.getDesc());
            viewHolder.tv_vedio_author.setText("[ "+ vedioInfo.getWho()+" ]");
            viewHolder.tv_vedio_time.setText(vedioInfo.getPublishedAt());
            return view;
        }
    }
    static class ViewHolder{
        TextView tv_vedio_dec,tv_vedio_author,tv_vedio_time;

    }
}