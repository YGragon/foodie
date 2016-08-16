package com.dongxi.foodie.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongxi.foodie.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * 作者：Aller  2016/7/19 17:24
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class HeaderAdapter extends StaticPagerAdapter {

    private int[] imgs = {
            R.drawable.food1,
            R.drawable.food2,
            R.drawable.food3,
            R.drawable.food4,
            R.drawable.food5,
    };




    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;

    }
}

