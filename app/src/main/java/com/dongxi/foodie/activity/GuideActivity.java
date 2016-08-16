package com.dongxi.foodie.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dongxi.foodie.R;
import com.dongxi.foodie.utils.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    //引导页的图片
    private static final int[] mImages = new int[]{R.drawable.g1,
            R.drawable.g2, R.drawable.g3} ;

    private ArrayList<ImageView> mImageViewList;

    private ViewPager vp_guide;

    private Button btn_start;

    private LinearLayout ll_point_group;// 引导圆点的父控件

    private int mPointWidth ;// 圆点间的距离

    private View view_red_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        setContentView(R.layout.activity_guide);



        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        btn_start = (Button) findViewById(R.id.btn_start);

        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        view_red_point = findViewById(R.id.view_red_point);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新sp
                PrefUtils.setBoolean(GuideActivity.this, "is_userguide_showed", true);

                // 跳转主页面
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

        initView() ;
        vp_guide.setAdapter(new GuideAdapter());

        vp_guide.setOnPageChangeListener(new GuidePageListener());
    }

    /**
     * ViewPaper数据适配器
     */
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }

    /**
     * viewPage的滑动监听
     */
    class GuidePageListener implements ViewPager.OnPageChangeListener{

        //页面滑动
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //参数1：当前位置；参数2：百分比；参数3：移动距离；
            int len = (int) (mPointWidth*positionOffset + position*mPointWidth);
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) view_red_point.getLayoutParams();// 获取当前红点的布局参数
            params.leftMargin = len ;//设置圆点的左边距
            view_red_point.setLayoutParams(params);//重新给小红点设置布局参数
        }

        //页面被选择
        @Override
        public void onPageSelected(int position) {
            if (position == mImages.length - 1){
                btn_start.setVisibility(View.VISIBLE);
            }else{
                btn_start.setVisibility(View.INVISIBLE);
            }

        }

        //页面滑动状态被改变
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private void initView() {

        mImageViewList = new ArrayList<ImageView>();

        //初始化引导页的三个页面
        for (int i = 0; i <mImages.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImages[i]);
            mImageViewList.add(imageView) ;

        }

        //初始化引导页的三个页面
        for (int i = 0; i <mImages.length; i++){
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);

            if (i > 0){
                params.leftMargin = 10 ;//原点间隔
            }
            point.setLayoutParams(params);//设置原点大小，系统默认是充满屏幕
            ll_point_group.addView(point);//将圆点加入布局
        }

        //获取视图树，对layout结束事件进行监听
        ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            // 当layout执行结束后回调此方法
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                ll_point_group.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointWidth = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
            }
        });
    }
}
