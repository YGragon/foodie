package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.VedioInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
public class VedioAdapter  extends RecyclerView.Adapter<VedioAdapter.VedioView>{
    private List<VedioInfo> vedioList = new ArrayList<VedioInfo>();
    private VedioInfo vedioInfo;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //定义一个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
        void onItemLongClick(View view , int position);
    }

    public VedioAdapter(List<VedioInfo> list) {
        vedioList=list;
    }
    @Override
    public VedioView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vedio_list, parent, false);
        return new VedioView(v);
    }

    @Override
    public void onBindViewHolder(final VedioView vedioView, final int position) {
        vedioInfo = vedioList.get(position);
        String desc = vedioInfo.getDesc();
        String time = vedioInfo.getPublishedAt().substring(0, 10);
        String url = vedioInfo.getUrl();
        String auhtor = vedioInfo.getWho();

        vedioView.tv_vedio_dec.setText(desc);
        vedioView.tv_vedio_author.setText(auhtor);
        vedioView.tv_vedio_time.setText(time );//设置图片时间


        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            //点击事件
            vedioView.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = vedioView.getLayoutPosition();
                    mOnItemClickListener.onItemClick(vedioView.itemView, pos);
                }
            });

            //长按事件
            vedioView.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = vedioView.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(vedioView.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return vedioList.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class VedioView extends RecyclerView.ViewHolder {
        TextView tv_vedio_dec, tv_vedio_author, tv_vedio_time;

        public VedioView(View itemView) {
            super(itemView);
            tv_vedio_dec = (TextView) itemView.findViewById(R.id.tv_vedio_dec);
            tv_vedio_author = (TextView) itemView.findViewById(R.id.tv_vedio_author);
            tv_vedio_time = (TextView) itemView.findViewById(R.id.tv_vedio_time);
        }
    }
}
