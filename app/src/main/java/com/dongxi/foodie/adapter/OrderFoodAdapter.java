package com.dongxi.foodie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.Order;

import java.util.List;

/**
 * 作者：Aller  2016/7/26 08:40
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class OrderFoodAdapter extends ArrayAdapter {

    private int resourceId;
    public OrderFoodAdapter(Context context, int textViewResourceId,List<Order> objects) {
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = (Order) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView orderImage = (ImageView) view.findViewById(R.id.order_image);
        TextView ordername = (TextView) view.findViewById(R.id.order_name);
        TextView orderinstruction = (TextView) view.findViewById(R.id.order_instruction);
        orderImage.setImageResource(order.getImageID());
        ordername.setText(order.getName());
        orderinstruction.setText(order.getInsrtuction());
        return view;
    }
}
