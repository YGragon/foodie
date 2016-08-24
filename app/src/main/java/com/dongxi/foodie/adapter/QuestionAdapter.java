package com.dongxi.foodie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.QuestionInfo;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionView> {
    private List<QuestionInfo> questionList = new ArrayList<QuestionInfo>();
    private QuestionInfo questionInfo;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    //定义一个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public QuestionAdapter(List<QuestionInfo> list) {
        questionList = list;
    }
    @Override
    public QuestionView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_question_item,parent,false) ;
        return new QuestionView(view);
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.QuestionView holder, int position) {
        questionInfo = questionList.get(position);
        holder.tv_question_title.setText(questionInfo.getTitle());// 设置问答的标题
        holder.tv_question_count.setText("热度：" + questionInfo.getCount() + " %");
//        holder.tv_question_title.setText((int) questionInfo.getTime());// 设置问答的时间

        String iconUrl = "http://tnfs.tngou.net/img" + questionInfo.getImg();
        x.image().bind(holder.iv_question,iconUrl);

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
        return questionList.size();
    }

    public class QuestionView extends RecyclerView.ViewHolder {

        ImageView iv_question;
        TextView tv_question_title, tv_question_count,tv_question_time;
        public QuestionView(View itemView) {
            super(itemView);
            iv_question = (ImageView) itemView.findViewById(R.id.iv_question);
            tv_question_title = (TextView) itemView.findViewById(R.id.tv_question_title);
            tv_question_count = (TextView) itemView.findViewById(R.id.tv_question_count);
            tv_question_time = (TextView) itemView.findViewById(R.id.tv_question_time);

        }
    }
}
