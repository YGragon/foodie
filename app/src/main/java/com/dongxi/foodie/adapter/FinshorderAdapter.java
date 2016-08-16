package com.dongxi.foodie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.Finshorder;

import java.util.List;

/**
 * 作者：Aller  2016/7/26 08:50
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class FinshorderAdapter extends ArrayAdapter<Finshorder> {
    private int resourceId;
    public FinshorderAdapter(Context context, int textViewResourceId, List<Finshorder> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Finshorder finshorder = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView finshorderImage = (ImageView) view.findViewById(R.id.finshorder_image);
        TextView finshordername = (TextView) view.findViewById(R.id.finshorder_name);
        TextView finshorderinstruction = (TextView) view.findViewById(R.id.finshorder_instruction);
        finshorderImage.setImageResource(finshorder.getImageID());
        finshordername.setText(finshorder.getName());
        finshorderinstruction.setText(finshorder.getFinshinstruction());
        return view;
    }
}
