package com.dongxi.foodie.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dongxi.foodie.R;
import com.dongxi.foodie.activity.UnfinishActivity;
import com.dongxi.foodie.utils.UIUtils;


public class OrderFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        Button Nofinshbutton = (Button)view.findViewById(R.id.Nofinshbutton);
        Button Finshbutton = (Button)view.findViewById(R.id.Finshbutton);

        Nofinshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UIUtils.getContext(), UnfinishActivity.class));
            }
        });




        return view ;
    }

}
