package com.dongxi.foodie.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.dongxi.foodie.R;
import com.dongxi.foodie.adapter.MasonryAdapter;
import com.dongxi.foodie.bean.Beauty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
public class BeautyActivity extends AppCompatActivity {

    private List<Beauty> beautyList = new ArrayList<Beauty>();
    private RecyclerView recyclerView ;
    private Beauty beautyInfo ;
    private SwipeRefreshLayout swipeLayout;
    private MasonryAdapter adapter;
    private SpacesItemDecoration decoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);

        recyclerView= (RecyclerView) findViewById(R.id.recycler);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        //设置layoutManager
        //设置瀑布流
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) ;
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //设置adapter
        adapter = new MasonryAdapter(beautyList);
        recyclerView.setAdapter(adapter);
        //设置item之间的间隔
        decoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);
        //设置进度条的背景颜色主题
        swipeLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置进度条的颜色主题
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        /*第一个参数scale就是就是刷新那个圆形进度是是否缩放,如果为true表示缩放,圆形进度图像就会从小到大展示出来,为false就不缩放
          第二个参数start和end就是那刷新进度条展示的相对于默认的展示位置,start和end组成一个范围，
            在这个y轴范围就是那个圆形进度ProgressView展示的位置
        */
        swipeLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        //下拉刷新操作
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        Snackbar.make(swipeLayout,"刷新成功",Snackbar.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        getDataFromServer();//从服务器获取数据
    }

    /**
     * 设置 item 间隔
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        //数据来自干活集中营
        RequestParams params = new RequestParams("http://gank.io/api/search/query/listview/category/%E7%A6%8F%E5%88%A9/count/40/page/1");
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                Log.d("BeautyActivity",result);//有结果
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
    public List<Beauty> parseData(String result) {

        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String publishedAt = jsonObj.getString("publishedAt");
                String url=jsonObj.getString("url");

                beautyInfo = new Beauty(publishedAt,url);
                beautyList.add(beautyInfo);
            }
            return beautyList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
