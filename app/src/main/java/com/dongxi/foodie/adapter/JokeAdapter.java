package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.JokeInfo;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeView>{
    private List<JokeInfo> jokeList = new ArrayList<JokeInfo>();
    private JokeInfo jokeInfo;

    public JokeAdapter(List<JokeInfo> list) {
        jokeList = list;
    }
    @Override
    public JokeAdapter.JokeView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke_list, parent, false);
        return new JokeView(v);
    }

    @Override
    public void onBindViewHolder(JokeAdapter.JokeView holder, int position) {
        jokeInfo = jokeList.get(position);
        holder.tv_author.setText("作者："+jokeInfo.getAuthor());
        //加载图片
        String iconUrl = jokeInfo.getPicUrl();
        if (iconUrl.equals("")){
            holder.iv_joke.setVisibility(View.GONE);
        }else {
            holder.iv_joke.setVisibility(View.VISIBLE);
            x.image().bind(holder.iv_joke, iconUrl);
        }
        holder.tv_content.setText(jokeInfo.getContent());
    }

    @Override
    public int getItemCount() {
        return jokeList.size();
    }

    public class JokeView extends RecyclerView.ViewHolder {
        TextView tv_author,tv_content;
        ImageView iv_joke ;

        public JokeView(View itemView) {
            super(itemView);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            iv_joke = (ImageView) itemView.findViewById(R.id.iv_joke);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
