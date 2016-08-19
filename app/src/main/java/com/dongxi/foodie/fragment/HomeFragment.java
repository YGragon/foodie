package com.dongxi.foodie.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.activity.ShowOrderActivity;
import com.dongxi.foodie.adapter.HeaderAdapter;
import com.dongxi.foodie.bean.Food;
import com.dongxi.foodie.controller.ConvenienceStoreActivity;
import com.dongxi.foodie.utils.UIUtils;
import com.dongxi.foodie.view.RefreshListVIew;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    public static Map<String,String> locmap = new HashMap<String,String>();

    private Food food;
    private Food info;

    private RollPagerView mRollViewPager;
    private Button btn_convenience;


    private RefreshListVIew lv_food_list;
    private ArrayList<Food> list = new ArrayList<Food>();

    List<Food> foodInfos = new ArrayList<Food>();//声明全局的才有效果

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            //更新UI
            new FoodAdapter().notifyDataSetChanged();
            lv_food_list.completeRefresh();
        };
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        View headView = inflater.inflate(R.layout.header_view,null);

        x.view().inject(this,view);
        x.view().inject(this,headView);

        mRollViewPager = (RollPagerView)headView.findViewById(R.id.roll_view_pager);
        btn_convenience = (Button)headView.findViewById(R.id.btn_convenience);

        //ListView的布局设置
        lv_food_list = (RefreshListVIew) view.findViewById(R.id.lv_food_list);

        //添加头布局
        lv_food_list.addHeaderView(headView);
        //给ListView添加适配器
        lv_food_list.setAdapter(new FoodAdapter());
        lv_food_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListView listView = (ListView) parent;
                Food foodBundle = (Food) listView.getItemAtPosition(position);

                String name = foodBundle.getName();
                String count = foodBundle.getCount();
                String image = foodBundle.getImg();

                Intent intent = new Intent(UIUtils.getContext(), ShowOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("count", count);
                bundle.putString("image", image);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lv_food_list.setOnRefreshListener(new RefreshListVIew.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                //需要联网请求服务器的数据，然后更新UI
                requestDataFromServer(false);
            }

            @Override
            public void onLoadingMore() {
                requestDataFromServer(true);
            }
        });

        btn_convenience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ConvenienceStoreActivity.class));
            }
        });

        //获取服务器数据
        getDataFromServer() ;

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new HeaderAdapter());
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        //Item的点击事件
        mRollViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(UIUtils.getContext(),"Item "+position+" clicked",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    //从服务器请求数据
    private void requestDataFromServer(final boolean b) {
        new Thread(){
            public void run() {
                SystemClock.sleep(3000);//模拟请求服务器的一个时间长度

                if(b){
                    //加载更多
                    list.add(food);
//                    list.add("加载更多的数据-2");
//                    list.add("加载更多的数据-3");
                }else {
                    //下拉刷新
                    list.add(0, food);
                }

                //在UI线程更新UI
                handler.sendEmptyMessage(0);
            };
        }.start();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

        RequestParams params = new RequestParams("http://www.tngou.net/api/cook/list?page=3&id=2&rows=10");
        //params.setSslSocketFactory(...); // 设置ssl
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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
    public List<Food> parseData(String result) {

        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("tngou");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                int id=jsonObj.getInt("id");
                String name = jsonObj.getString("name");
                String img=jsonObj.getString("img");
                String count = jsonObj.getString("count");

                info = new Food(id, name,img,count);
                foodInfos.add(info);
            }
            return foodInfos;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Food的适配器
     */
    private class FoodAdapter extends BaseAdapter{

        @Override
    public int getCount() {
        return foodInfos.size();
    }

    @Override
    public Food getItem(int position) {
        return foodInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        final ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(UIUtils.getContext(), R.layout.list_food_item, null);
            holder=new ViewHolder();
            holder.iv_food = (ImageView) view.findViewById(R.id.iv_food);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_count = (TextView) view.findViewById(R.id.tv_count);

            view.setTag(holder);
        }else{
            view = convertView ;
            holder = (ViewHolder)view.getTag();
        }
        Food foodData = foodInfos.get(position);

        holder.tv_name.setText(foodData.getName());// 设置美食的名字
        holder.tv_count.setText("收藏数：" + foodData.getCount() + " 次");
        String iconUrl = foodData.getImg();
        x.image().bind(holder.iv_food, "http://tnfs.tngou.net/img" + iconUrl);

        return view;
    }
    }

    static class ViewHolder{
        ImageView iv_food;
        TextView tv_name,tv_count;
    }


}

