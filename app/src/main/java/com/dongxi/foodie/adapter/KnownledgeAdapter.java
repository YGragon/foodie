package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.KnownledgeInfo;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class KnownledgeAdapter extends RecyclerView.Adapter<KnownledgeAdapter.KnownledgeView> {
    private List<KnownledgeInfo> knownledgeList = new ArrayList<KnownledgeInfo>();
    private KnownledgeInfo knownledgeInfo;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    //定义一个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }

    public KnownledgeAdapter(List<KnownledgeInfo> list) {
        knownledgeList = list;
    }
    @Override
    public KnownledgeView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_knownledge_item,parent,false) ;
        return new KnownledgeView(view);
    }

    @Override
    public void onBindViewHolder(final KnownledgeAdapter.KnownledgeView holder, int position) {
        knownledgeInfo = knownledgeList.get(position);
        holder.tv_knownledge_title.setText(knownledgeInfo.getTitle());// 设置知识的标题
        holder.tv_knownledge_count.setText("热度：" + knownledgeInfo.getCount() + " %");
        String iconUrl = "http://tnfs.tngou.net/img" + knownledgeInfo.getImg();
        x.image().bind(holder.iv_knownledge,iconUrl);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            //点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            //长按事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return knownledgeList.size();
    }

    public class KnownledgeView extends RecyclerView.ViewHolder {

        ImageView iv_knownledge;
        TextView tv_knownledge_title, tv_knownledge_count;
        public KnownledgeView(View itemView) {
            super(itemView);
            iv_knownledge = (ImageView) itemView.findViewById(R.id.iv_knownledge);
            tv_knownledge_title = (TextView) itemView.findViewById(R.id.tv_knownledge_title);
            tv_knownledge_count = (TextView) itemView.findViewById(R.id.tv_knownledge_count);

        }
    }
}
