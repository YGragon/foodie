package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.Beauty;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.BeautyView>{

    private List<Beauty> beautyList = new ArrayList<Beauty>();
    private Beauty beautyInfo;
    private VedioAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //定义一个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }

    public BeautyAdapter(List<Beauty> list) {
        beautyList=list;
    }

    @Override
    public BeautyView  onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beauty_item_list, viewGroup, false);
        return new BeautyView (view);
    }

    @Override
    public void onBindViewHolder(final BeautyView beautyView, int position) {

        beautyInfo = beautyList.get(position);
        String iconUrl = beautyInfo.getUrl();
        x.image().bind(beautyView.beauty_item_img,iconUrl);//设置图片
//        beautyInfo.getPublishedAt().substring(0,10) ;//截取日期字符串
        beautyView.beauty_item_time.setText(beautyInfo.getPublishedAt().substring(0,10) );//设置图片时间

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            //点击事件
            beautyView.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = beautyView.getLayoutPosition();
                    mOnItemClickListener.onItemClick(beautyView.itemView, pos);
                }
            });

            //长按事件
            beautyView.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = beautyView.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(beautyView.itemView, pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return beautyList.size();
    }
    public void setOnItemClickListener(VedioAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class BeautyView  extends  RecyclerView.ViewHolder{

        ImageView beauty_item_img;
        TextView beauty_item_time;

        public BeautyView (View itemView){
            super(itemView);
            beauty_item_img= (ImageView) itemView.findViewById(R.id.beauty_item_img );
            beauty_item_time= (TextView) itemView.findViewById(R.id.beauty_item_time);
        }

    }
}
