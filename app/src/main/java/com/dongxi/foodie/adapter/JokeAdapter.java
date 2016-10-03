package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.JokeInfo;

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
        holder.tv_data.setText("日期："+jokeInfo.getUpdatetime());
        holder.tv_content.setText(jokeInfo.getContent());
    }

    @Override
    public int getItemCount() {
        return jokeList.size();
    }

    public class JokeView extends RecyclerView.ViewHolder {
        TextView tv_data,tv_content;

        public JokeView(View itemView) {
            super(itemView);
            tv_data = (TextView) itemView.findViewById(R.id.tv_data);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
