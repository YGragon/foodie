package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.Food;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodView> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //定义一个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }
    //HeaderView, FooterView
    private static View mHeaderView;
    private View mFooterView;

    private List<Food> foodList = new ArrayList<Food>();
    private Food foodInfo;

    public FoodAdapter(List<Food> list) {
        foodList = list;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }
    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
    @Override
    public FoodAdapter.FoodView onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new FoodView(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new FoodView(mFooterView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food_item, parent, false);
        return new FoodView(view);

//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food_item, parent, false);
//        return new FoodView(view);
    }
    @Override
    public void onBindViewHolder(final FoodAdapter.FoodView holder,final int position) {

        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof FoodView) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                foodInfo = foodList.get(position-1) ;
                Log.e("..........",foodInfo.toString()) ;
                holder.tv_name.setText(foodInfo.getName());// 设置美食的名字
                holder.tv_count.setText("收藏数：" + foodInfo.getCount() + " 次");
                String iconUrl = "http://tnfs.tngou.net/img" + foodInfo.getImg();
                x.image().bind(holder.iv_food,iconUrl);

                // 如果设置了回调，则设置点击事件
                if (mOnItemClickListener != null) {
                    //点击事件
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = holder.getLayoutPosition();
                            mOnItemClickListener.onItemClick(holder.itemView, pos);
                        }
                    });

                    //长按事件
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int pos = holder.getLayoutPosition();
                            mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                            return false;
                        }
                    });
                }
                return;
            }
            return;
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return foodList.size();
        }else if(mHeaderView == null && mFooterView != null){
            return foodList.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return foodList.size() + 1;
        }else {
            return foodList.size() + 2;
        }
//        return foodList.size();
    }
    public class FoodView  extends  RecyclerView.ViewHolder {

        ImageView iv_food;
        TextView tv_name, tv_count;
        public FoodView(View itemView) {
            super(itemView);

            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }

            iv_food = (ImageView) itemView.findViewById(R.id.iv_food);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);

        }
    }
}
