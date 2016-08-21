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


    public BeautyAdapter(List<Beauty> list) {
        beautyList=list;
    }

    @Override
    public BeautyView  onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beauty_item_list, viewGroup, false);
        return new BeautyView (view);
    }

    @Override
    public void onBindViewHolder(BeautyView beautyView, int position) {

        beautyInfo = beautyList.get(position);
        String iconUrl = beautyInfo.getUrl();
        x.image().bind(beautyView.beauty_item_img,iconUrl);//设置图片
//        beautyInfo.getPublishedAt().substring(0,10) ;//截取日期字符串
        beautyView.beauty_item_time.setText(beautyInfo.getPublishedAt().substring(0,10) );//设置图片时间
    }

    @Override
    public int getItemCount() {
        return beautyList.size();
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
