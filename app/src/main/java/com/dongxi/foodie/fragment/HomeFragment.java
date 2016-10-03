package com.dongxi.foodie.fragment;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.activity.FoodDetailActivity;
import com.dongxi.foodie.adapter.BannerLoopPager;
import com.dongxi.foodie.adapter.FoodAdapter;
import com.dongxi.foodie.bean.Food;
import com.dongxi.foodie.utils.UIUtils;
import com.dongxi.foodie.view.DividerItemDecoration;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private Food info;

    private SwipeRefreshLayout swipelayout ;

    private RecyclerView lv_food_list;
    private ProgressBar pb_progress;
    private LinearLayoutManager linearLayoutManager;
    List<Food> foodInfos = new ArrayList<Food>();//声明全局的才有效果
    List<Food> foodLists = new ArrayList<Food>();//声明全局的才有效果
    private FoodAdapter foodAdapter;
    private int lastVisibleItem;
    private int pageSize = 30;
    private int page = 1;
    private RollPagerView loop_view_pager;
    private BannerLoopPager bannerLoopPager ;
    private View header;
    private String img;
    private String name;
    private String count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        x.view().inject(this,view);

        //ListView的布局设置
        swipelayout = (SwipeRefreshLayout)view.findViewById(R.id.swipelayout);
        lv_food_list = (RecyclerView) view.findViewById(R.id.lv_food_list);


        //设置RecyclerView的格式
        linearLayoutManager = new LinearLayoutManager(UIUtils.getContext());
        lv_food_list.setLayoutManager(linearLayoutManager);

        foodAdapter = new FoodAdapter(foodInfos);
        lv_food_list.setAdapter(foodAdapter);

        //RecyclerView的item点击事件
        foodAdapter.setOnItemClickListener(new FoodAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(UIUtils.getContext(), FoodDetailActivity.class) ;
                //position-1 是因为headerView 占用了第一个position
                String food_image = foodInfos.get(position-1).getImg() ;
                String food_Material = foodInfos.get(position-1).getFood() ;
                String food_Description = foodInfos.get(position-1).getDescription() ;

                intent.putExtra("food_image",food_image) ;
                intent.putExtra("food_Material",food_Material) ;
                intent.putExtra("food_Description",food_Description) ;

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        //为RecyclerView添加HeaderView和FooterView
        setHeaderView(lv_food_list);
        setFooterView(lv_food_list);

        //配置RecyclerView 可以提高执行效率, 前提你要知道有多少不变的item
        lv_food_list.setHasFixedSize(true);

        //设置item之间的间隔,分割线等
        lv_food_list.addItemDecoration(new DividerItemDecoration(UIUtils.getContext(),
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
                        foodInfos.add(0,info) ;//在头部加入数据
                        foodAdapter.notifyDataSetChanged();
                        swipelayout.setRefreshing(false);// 通知RecyclerView刷新数据完毕,让listview停止刷新
                    }
                }, 2000);
            }
        });
        //滑动监听
        lv_food_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == foodAdapter.getItemCount()) {
                    swipelayout.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    getDataFromServer();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            swipelayout.setRefreshing(false);
                            foodAdapter.notifyDataSetChanged();
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
        getDataFromServer() ;

        return view;
    }

    //设置头布局
    private void setHeaderView(RecyclerView view){
        header = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.header_view, view, false);

        loop_view_pager =  (RollPagerView)header.findViewById(R.id.loop_view_pager) ;
        bannerLoopPager = new BannerLoopPager();
        loop_view_pager.setAdapter(bannerLoopPager);
        loop_view_pager.setHintView(new ColorPointHintView(UIUtils.getContext(), Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(null);
        loop_view_pager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(UIUtils.getContext(),"Item "+position+" clicked",Toast.LENGTH_SHORT).show();
            }
        });
        foodAdapter.setHeaderView(header);
    }

    //设置脚布局
    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.layout_footer, view, false);
        foodAdapter.setFooterView(footer);
    }
    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        RequestParams params = new RequestParams("http://www.tngou.net/api/cook/" +
                "list?page="+String.valueOf(page)+"&id=2&rows="+String.valueOf(pageSize));
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                parseData(result);//解析数据
                foodAdapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                swipelayout.setVisibility(View.VISIBLE);
            }
        });
    }
    /**
     * 解析网络数据
     * @param result
     */
    public List<Food> parseData(String result) {

        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("tngou");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                int id=jsonObj.getInt("id");
                //菜名
                name = jsonObj.getString("name");
                img = jsonObj.getString("img");
                count = jsonObj.getString("count");
                String description = jsonObj.getString("description");
                String food = jsonObj.getString("food");//食材


                info = new Food(id, name, img, count,description,food);
                foodInfos.add(info);
            }
            return foodInfos;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

