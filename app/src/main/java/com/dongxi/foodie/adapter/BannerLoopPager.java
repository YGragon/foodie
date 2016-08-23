package com.dongxi.foodie.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongxi.foodie.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by Administrator on 2016/8/22.
 */
public class BannerLoopPager extends StaticPagerAdapter {
    private int[] imgs = {
            R.drawable.food1,
            R.drawable.food2,
            R.drawable.food3,
            R.drawable.food4,
            R.drawable.food5,
    };

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }
}
