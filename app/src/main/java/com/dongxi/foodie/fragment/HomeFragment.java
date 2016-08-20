package com.dongxi.foodie.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
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
import com.dongxi.foodie.dao.FoodsParams;
import com.dongxi.foodie.utils.MyBitmapUtils;
import com.dongxi.foodie.utils.UIUtils;
import com.dongxi.foodie.view.RefreshListVIew;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
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
        //刷新加载监听
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
        FoodsParams params = new FoodsParams();
//        params.addQueryStringParameter("wd", "xUtils");
        params.wd = "xUtils";
// 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
        params.setCacheMaxAge(1000 * 60);
        Callback.Cancelable cancelable
                // 使用CacheCallback, xUtils将为该请求缓存数据.
                = x.http().get(params, new Callback.CacheCallback<String>() {

            private boolean hasError = false;
            private String result = null;

            @Override
            public boolean onCache(String result) {
                // 得到缓存数据, 缓存过期后不会进入这个方法.
                // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
                //
                // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
                //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
                //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
                //
                // * 如果信任该缓存返回 true, 将不再请求网络;
                //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
                //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
                //
                this.result = result;
                return false; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
            }
            @Override
            public void onSuccess(String result) {
                // 注意: 如果服务返回304 或 onCache 选择了信任缓存, 这时result为null.
                parseData(result);//解析数据
                if (result != null) {
                    this.result = result;
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFinished() {
                if (!hasError && result != null) {
                }
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
        private MyBitmapUtils utils;

        public FoodAdapter() {
            //utils = new BitmapUtils(mActivity);
            //utils.configDefaultLoadingImage(R.drawable.news_pic_default);
            utils = new MyBitmapUtils();
        }

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
        String iconUrl = "http://tnfs.tngou.net/img" + foodData.getImg();
        x.image().bind(holder.iv_food,iconUrl);


        utils.display(holder.iv_food, iconUrl);
        return view;
    }
    }

    static class ViewHolder{
        ImageView iv_food;
        TextView tv_name,tv_count;
    }
}

