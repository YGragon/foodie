package com.dongxi.foodie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.Unfinshorder;

import java.util.List;

/**
 * 作者：Aller  2016/7/26 08:56
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class unfinshorderAdapter extends ArrayAdapter<Unfinshorder> {
    private int resourceID;

    public unfinshorderAdapter(Context context, int textViewResourceId, List<Unfinshorder> objects) {
        super(context, textViewResourceId, objects);
        resourceID = textViewResourceId;
    }
    public View getView(int position,View converView,ViewGroup parent){

        Unfinshorder unfinshorder = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID, null);
        ImageView unorderimage = (ImageView)view.findViewById(R.id.unorder_image);
        TextView unordername = (TextView) view.findViewById(R.id.unorder_name);
        TextView uninstruction = (TextView) view.findViewById(R.id.unorder_instruction);
        Button unorderadd = (Button) view.findViewById(R.id.btn_add);
        unorderimage.setImageResource(unfinshorder.getImageID());
        unordername.setText(unfinshorder.getName());
        uninstruction.setText(unfinshorder.getUninsrtuction());

        return view;
    }
}
