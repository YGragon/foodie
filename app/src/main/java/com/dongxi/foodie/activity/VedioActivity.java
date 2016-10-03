package com.dongxi.foodie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.adapter.VedioAdapter;
import com.dongxi.foodie.adapter.VedioAdapter.OnRecyclerViewItemClickListener;
import com.dongxi.foodie.bean.VedioInfo;
import com.dongxi.foodie.view.DividerItemDecoration;
import com.nguyenhoanglam.progresslayout.ProgressLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 爽看视频模块，数据采用干活集中营的数据
 */
public class VedioActivity extends AppCompatActivity {

    private RecyclerView rl_vedio;
    private SwipeRefreshLayout swipelayout ;
    List<VedioInfo> vedioInfos = new ArrayList<VedioInfo>();
    private VedioInfo vedioInfo;
    private ProgressBar pb_progress;
    private VedioAdapter vedioAdapter;
//    private SpacesItemDecoration decoration;
    private Handler handler = new Handler();

    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private int pageSize = 20;
    private int page = 1;
    private ProgressLayout progressLayout;
    private List<Integer> skipIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);

        swipelayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        rl_vedio = (RecyclerView) findViewById(R.id.rl_vedio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("看个视频");
        progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);

        linearLayoutManager = new LinearLayoutManager(this);
        rl_vedio.setLayoutManager(linearLayoutManager);

        //设置adapter
        vedioAdapter = new VedioAdapter(vedioInfos);
        rl_vedio.setAdapter(vedioAdapter);

        //配置RecyclerView 可以提高执行效率, 前提你要知道有多少不变的item
        rl_vedio.setHasFixedSize(true);

        //设置item之间的间隔,分割线等
        rl_vedio.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        //设置进度条的背景颜色主题
        swipelayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置进度条的颜色主题
        swipelayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        /*第一个参数scale就是就是刷新那个圆形进度是是否缩放,如果为true表示缩放,圆形进度图像就会从小到大展示出来,为false就不缩放
          第二个参数start和end就是那刷新进度条展示的相对于默认的展示位置,start和end组成一个范围，
            在这个y轴范围就是那个圆形进度ProgressView展示的位置
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        */
        swipelayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //滑动监听
        rl_vedio.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == vedioAdapter.getItemCount()) {
                    swipelayout.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            getDataFromServer();
                            Toast.makeText(VedioActivity.this,"加载更多:page = " + page,Toast.LENGTH_LONG).show();
                            swipelayout.setRefreshing(false);
                            vedioAdapter.notifyDataSetChanged();
                            Snackbar.make(swipelayout,"刷新成功",Snackbar.LENGTH_LONG).show();
                        }
                    }, 2000);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        //将视频地址添加给每个item然后跳转网页
        vedioAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                String uriStr = vedioInfos.get(position).getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        ( Uri.parse(uriStr))
                ).addCategory(Intent.CATEGORY_BROWSABLE)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            //item的长按事件
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(VedioActivity.this,"别长摁太久哦",Toast.LENGTH_LONG).show();
            }
        });

        //下拉刷新操作
        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        swipelayout.setRefreshing(false);
                        vedioAdapter.notifyDataSetChanged();
                        Snackbar.make(swipelayout,"刷新成功",Snackbar.LENGTH_LONG).show();
                    }
                }, 2000);
            }
        });

        skipIds = new ArrayList<>();
        skipIds.add(R.id.toolbar);
        skipIds.add(R.id.pb_progress);
        progressLayout.showLoading(skipIds);

        getDataFromServer();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        pb_progress.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams("http://gank.io/api/search/query/" +
                "listview/category/%E4%BC%91%E6%81%AF%E8%A7%86%E9%A2%91/count/" +
                String.valueOf(pageSize)+"/page" + "/" +String.valueOf(page));
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                parseData(result);//解析数据
                vedioAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                progressLayout.showError(ContextCompat.getDrawable(VedioActivity.this, R.drawable.ic_no_connection), "No connection", "RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(VedioActivity.this, "Reloading...", Toast.LENGTH_SHORT).show();
                    }
                }, skipIds);
            }
            @Override
            public void onCancelled(CancelledException cex) {
                progressLayout.showEmpty(ContextCompat.getDrawable(VedioActivity.this, R.drawable.ic_empty), "Empty data", skipIds);
            }
            @Override
            public void onFinished() {
                pb_progress.setVisibility(View.GONE);
                swipelayout.setVisibility(View.VISIBLE);
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

}