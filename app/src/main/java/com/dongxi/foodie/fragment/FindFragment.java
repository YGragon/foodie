package com.dongxi.foodie.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dongxi.foodie.R;
import com.dongxi.foodie.activity.CommonScanActivity;
import com.dongxi.foodie.activity.ShakeActivity;
import com.dongxi.foodie.controller.LocationCode;
import com.dongxi.foodie.controller.PoiAroundSearchActivity;
import com.dongxi.foodie.controller.VipControllerActivity;

import java.util.HashMap;
import java.util.Map;

public class FindFragment extends Fragment {
    TextView locate ;
    String location = "正在定位中...";
    public static Map<String,String> locmap = new HashMap<String,String>();

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

        //附近搜索
        findView.findViewById(R.id.poi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PoiAroundSearchActivity.class);
                startActivity(i);
            }
        });

        //更多周边
        findView.findViewById(R.id.findMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MapFragment.class);
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


        locate = (TextView) findView.findViewById(R.id.locate_tv);

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),LocationCode.class);
                getActivity().startService(i);
                locate.setText(location);
                location = locmap.get("city") + locmap.get("district")
                        + locmap.get("street") + locmap.get("streetNum");
                locate.setText(location);

            }
        });
        return findView;
    }

}
