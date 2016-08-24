package com.dongxi.foodie.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongxi.foodie.R;
import com.dongxi.foodie.adapter.order_tab_Adapter;

import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends Fragment {

    private TabLayout tab_FindFragment_title;                            //定义TabLayout
    private ViewPager vp_FindFragment_pager;                             //定义viewPager
    private FragmentPagerAdapter fAdapter;                               //定义adapter

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private HealthNewsFragment healthNewsFragment;              //已完成订单fragment
    private HealthKnowledgeFragment healthKnowledgeFragment;            //未完成订单fragment
    private HealthQuestionFragment healthQuestionFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initControls(view);
        return view ;
    }

    private void initControls(View view) {
        tab_FindFragment_title = (TabLayout)view.findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (ViewPager)view.findViewById(R.id.vp_FindFragment_pager);

        //初始化各fragment
        healthNewsFragment = new HealthNewsFragment();
        healthKnowledgeFragment = new HealthKnowledgeFragment();
        healthQuestionFragment = new HealthQuestionFragment();

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(healthNewsFragment);
        list_fragment.add(healthKnowledgeFragment);
        list_fragment.add(healthQuestionFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("健康资讯");
        list_title.add("健康知识");
        list_title.add("健康问答");

        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));

        fAdapter = new order_tab_Adapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);
        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);//将TabLayout和ViewPager关联起来

    }

}
