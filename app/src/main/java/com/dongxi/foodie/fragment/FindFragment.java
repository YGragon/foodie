package com.dongxi.foodie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongxi.foodie.R;
import com.dongxi.foodie.activity.CommonScanActivity;
import com.dongxi.foodie.activity.ShakeActivity;
import com.dongxi.foodie.controller.VipControllerActivity;

public class FindFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View findView = inflater.inflate(R.layout.fragment_find, container, false);

        //VIP开通
        findView.findViewById(R.id.tv_vip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),VipControllerActivity.class);
                startActivity(i);
            }
        });


        //摇一摇
        findView.findViewById(R.id.tv_shake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ShakeActivity.class);
                startActivity(i);
            }
        });

        //扫一扫
        findView.findViewById(R.id.tv_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CommonScanActivity.class);
                startActivity(i);
            }
        });

        return findView;
    }

}
