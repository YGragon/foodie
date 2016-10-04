package com.dongxi.foodie.activity;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.adapter.BeautyAdapter;
import com.dongxi.foodie.adapter.VedioAdapter;
import com.dongxi.foodie.bean.Beauty;
import com.nguyenhoanglam.progresslayout.ProgressLayout;

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
    private BeautyAdapter adapter;
    private SpacesItemDecoration decoration;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ProgressBar pb_progress;
    private int lastVisibleItem;
    private int pageSize = 10;
    private int page = 1;
    private List<Integer> skipIds;
    private ProgressLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);

        recyclerView= (RecyclerView) findViewById(R.id.recycler);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("一大波妹子即将来袭...");
        progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        //设置layoutManager
        //设置瀑布流
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //设置adapter
        adapter = new BeautyAdapter(beautyList);
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
                        page++ ;
                        swipeLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        Snackbar.make(swipeLayout,"刷新成功",Snackbar.LENGTH_LONG).show();
                    }
                }, 2000);
            }
        });
        //RecyclerView滑动监听
        //滑动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
//                    swipeLayout.setRefreshing(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            getDataFromServer();
                            swipeLayout.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                            Snackbar.make(swipeLayout,"刷新成功",Snackbar.LENGTH_LONG).show();
                        }
                    }, 2000);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = getLastVisiblePosition();
            }
        });
        adapter.setOnItemClickListener(new VedioAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LayoutInflater inflater = LayoutInflater.from(BeautyActivity.this);
                View imgEntryView = inflater.inflate(R.layout.dialog_fullscreen, null); // 加载自定义的布局文件
                final Dialog dialog = new Dialog(BeautyActivity.this,R.style.Dialog_Fullscreen);
                ImageView img1 = (ImageView)imgEntryView.findViewById(R.id.iv_fullImage);
                x.image().bind(img1,beautyList.get(position).getUrl());
                dialog.setContentView(imgEntryView); // 自定义dialog
                dialog.show();

                // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
                imgEntryView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramView) {
                        dialog.cancel();
                    }
                });
            }

            //长摁事件
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });

        skipIds = new ArrayList<>();
        skipIds.add(R.id.toolbar);
        skipIds.add(R.id.pb_progress);
        progressLayout.showLoading(skipIds);

        getDataFromServer();//从服务器获取数据
    }
    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
            int[] lastPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(
                    new int[staggeredGridLayoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        return position;
    }
    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
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
        pb_progress.setVisibility(View.VISIBLE);
        //数据来自干活集中营
        RequestParams params = new RequestParams("http://gank.io/api/search/query/listview/" +
                "category/%E7%A6%8F%E5%88%A9/count/" +
                String.valueOf(pageSize)+"/page" + "/" +String.valueOf(page));
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                Log.d("BeautyActivity",result);//有结果
                parseData(result);//解析数据
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                progressLayout.showError(ContextCompat.getDrawable(BeautyActivity.this, R.drawable.ic_no_connection), "No connection", "RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(BeautyActivity.this, "Reloading...", Toast.LENGTH_SHORT).show();
                    }
                }, skipIds);
            }
            @Override
            public void onCancelled(CancelledException cex) {
                progressLayout.showEmpty(ContextCompat.getDrawable(BeautyActivity.this, R.drawable.ic_empty), "Empty data", skipIds);
            }
            @Override
            public void onFinished() {
                progressLayout.setVisibility(View.GONE);
                swipeLayout.setVisibility(View.VISIBLE);
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
