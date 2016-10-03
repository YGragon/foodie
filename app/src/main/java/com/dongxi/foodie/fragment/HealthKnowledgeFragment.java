package com.dongxi.foodie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dongxi.foodie.R;
import com.dongxi.foodie.activity.KnownledgeDetailActivity;
import com.dongxi.foodie.adapter.KnownledgeAdapter;
import com.dongxi.foodie.bean.KnownledgeInfo;
import com.dongxi.foodie.utils.UIUtils;
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


public class HealthKnowledgeFragment extends Fragment {
    private KnownledgeInfo knownledgeInfo;

    private SwipeRefreshLayout swipelayout ;

    private RecyclerView recyclerView_Knownledge;
    private ProgressBar pb_progress;
    private LinearLayoutManager linearLayoutManager;
    List<KnownledgeInfo> knownledgeInfos = new ArrayList<KnownledgeInfo>();//声明全局的才有效果
    private KnownledgeAdapter knownledgeAdapter;
    private int lastVisibleItem;
    private int pageSize = 20;
    private int page = 1;
    private List<Integer> skipIds;
    private ProgressLayout progressLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_knownledge, container, false);
        x.view().inject(this,view);

        //ListView的布局设置
        swipelayout = (SwipeRefreshLayout)view.findViewById(R.id.swipelayout);
        recyclerView_Knownledge = (RecyclerView) view.findViewById(R.id.recyclerView_Knownledge);

        progressLayout = (ProgressLayout) view.findViewById(R.id.progressLayout);
        pb_progress = (ProgressBar)view.findViewById(R.id.pb_progress);


        //设置RecyclerView的格式
        linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        recyclerView_Knownledge.setLayoutManager(linearLayoutManager);

        knownledgeAdapter = new KnownledgeAdapter(knownledgeInfos);
        recyclerView_Knownledge.setAdapter(knownledgeAdapter);



        //RecyclerView的item点击事件
        knownledgeAdapter.setOnItemClickListener(new KnownledgeAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(UIUtils.getContext(), KnownledgeDetailActivity.class) ;
                //position-1 是因为headerView 占用了第一个position
                String knownledge_image = knownledgeInfos.get(position).getImg() ;
                String knownledge_Desc = knownledgeInfos.get(position).getDescription() ;

                intent.putExtra("knownledge_image",knownledge_image) ;
                intent.putExtra("knownledge_Description",knownledge_Desc) ;

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
//配置RecyclerView 可以提高执行效率, 前提你要知道有多少不变的item
        recyclerView_Knownledge.setHasFixedSize(true);

        //设置item之间的间隔,分割线等
        recyclerView_Knownledge.addItemDecoration(new DividerItemDecoration(UIUtils.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        //设置进度条的背景颜色主题
        swipelayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置进度条的颜色主题
        swipelayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        // 这句话是为了，第一次进入页面的时候显示加载进度条,第一个参数表示加载动画是否是缩放动画，true是false否
        swipelayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //下拉刷新操作
        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        swipelayout.setRefreshing(false);
                        knownledgeAdapter.notifyDataSetChanged();
                        Snackbar.make(swipelayout,"刷新成功",Snackbar.LENGTH_LONG).show();
                    }
                }, 2000);
            }
        });
        //滑动监听
        recyclerView_Knownledge.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == knownledgeAdapter.getItemCount()) {
                    swipelayout.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    getDataFromServer();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            swipelayout.setRefreshing(false);
                            knownledgeAdapter.notifyDataSetChanged();
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

        skipIds = new ArrayList<>();
        skipIds.add(R.id.pb_progress);
        progressLayout.showLoading(skipIds);

        getDataFromServer() ;


        return view ;
    }
    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        pb_progress.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams("http://www.tngou.net/api/lore/" +
                "list?page="+String.valueOf(page)+"&rows="+String.valueOf(pageSize));
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                parseData(result);//解析数据
                knownledgeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                progressLayout.setVisibility(View.GONE);
                swipelayout.setVisibility(View.VISIBLE);
            }
        });
    }
    /**
     * 解析网络数据
     * @param result
     */
    public List<KnownledgeInfo> parseData(String result) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("tngou");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                int id=jsonObj.getInt("id");
                String title = jsonObj.getString("title");//菜名
                String img=jsonObj.getString("img");
                int count=jsonObj.getInt("count");
                String description = jsonObj.getString("description");


                knownledgeInfo = new KnownledgeInfo(count, description,id,img,title);
                knownledgeInfos.add(knownledgeInfo);
            }
            return knownledgeInfos;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
