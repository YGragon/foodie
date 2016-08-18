package com.dongxi.foodie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.JokeInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class JokeActivity extends AppCompatActivity {

    private ListView lv_joke;
    List<JokeInfo> jokeInfos = new ArrayList<JokeInfo>();
    private JokeInfo jokenfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        lv_joke = (ListView) findViewById(R.id.lv_joke);
        lv_joke.setAdapter(new JokeAdapter());

        getDataFromServer();

    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        //聚合数据API,通过经纬度和查询半径找
        RequestParams params = new RequestParams("http://api.1-blog.com/biz/bizserver/xiaohua/list.do");
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
            JSONArray jsonArray = jsonObject.getJSONArray("detail");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String author = jsonObj.getString("author");
                String content = jsonObj.getString("content");
                String picUrl = jsonObj.getString("picUrl");
                JokeInfo info = new JokeInfo(author, content, picUrl);
                jokeInfos.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class JokeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return jokeInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return jokeInfos.get(position);
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
                view = View.inflate(JokeActivity.this, R.layout.item_joke_list, null);
                viewHolder=new ViewHolder();
                viewHolder.tv_author = (TextView) view.findViewById(R.id.tv_author);
                viewHolder.iv_joke = (ImageView) view.findViewById(R.id.iv_joke);
                viewHolder.tv_content = (TextView) view.findViewById(R.id.tv_content);

                view.setTag(viewHolder);
            }else{
                view = convertView ;
                viewHolder = (ViewHolder) view.getTag();
            }
            jokenfo = jokeInfos.get(position);
            viewHolder.tv_author.setText("作者："+jokenfo.getAuthor());
            //加载图片
            String iconUrl = jokenfo.getPicUrl();
            if (iconUrl.equals("")){
                viewHolder.iv_joke.setVisibility(View.GONE);
            }else {
                viewHolder.iv_joke.setVisibility(View.VISIBLE);
                x.image().bind(viewHolder.iv_joke, iconUrl);
            }
            viewHolder.tv_content.setText(jokenfo.getContent());
            return view;
        }
    }
    static class ViewHolder{
        TextView tv_author,tv_content;
        ImageView iv_joke ;
    }
}
