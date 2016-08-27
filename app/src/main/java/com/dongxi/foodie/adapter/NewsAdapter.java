package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.NewsInfo;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsView> {

    private List<NewsInfo> newsList = new ArrayList<NewsInfo>();
    private NewsInfo newsInfo;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    //定义一个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public NewsAdapter(List<NewsInfo> list) {
        newsList = list;
    }

    @Override
    public NewsView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_item,parent,false) ;
        return new NewsView(view);
    }

    @Override
    public void onBindViewHolder(final NewsAdapter.NewsView holder, int position) {
        newsInfo = newsList.get(position);
        holder.tv_news_title.setText(newsInfo.getTitle());// 设置问答的标题
        holder.tv_news_count.setText("热度：" + newsInfo.getCount() + " %");
        // 设置问答的时间
        long time = newsInfo.getTime() ;
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
        holder.tv_news_time.setText(simpleDateFormat.format(date));

        String iconUrl = "http://tnfs.tngou.net/img" + newsInfo.getImg();
        x.image().bind(holder.iv_news,iconUrl);

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
        return newsList.size();
    }
    public class NewsView extends RecyclerView.ViewHolder {
    ImageView iv_news;
    TextView tv_news_title, tv_news_count,tv_news_time;
    public NewsView(View itemView) {
        super(itemView);
        iv_news = (ImageView) itemView.findViewById(R.id.iv_news);
        tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
        tv_news_count = (TextView) itemView.findViewById(R.id.tv_news_count);
        tv_news_time = (TextView) itemView.findViewById(R.id.tv_news_time);
        }
    }
}
