package com.dongxi.foodie.activity;


import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.fragment.FindFragment;
import com.dongxi.foodie.fragment.HomeFragment;
import com.dongxi.foodie.fragment.CommunityFragment;
import com.jaeger.library.StatusBarUtil;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends ActionBarActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private TextView txt_waimai;
    private TextView txt_find;
    private TextView txt_order;
    private FrameLayout ly_content;

    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private CommunityFragment communityFragment;
    private android.support.v4.app.FragmentManager fManager ;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fManager = getSupportFragmentManager();
        bindViews();
        txt_waimai.performClick();   //模拟一次点击，既进去后选择第一项


        findViews(); //获取控件
        toolbar.setTitle("健康菜谱");//设置Toolbar标题

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //设置标题颜色
        setSupportActionBar(toolbar) ;//支持ActionBar


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        //不写这句话，是没有按钮显示的
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        startActivity(new Intent(getApplicationContext(), Searchctivity.class));
                        break;
                }
                return false;
            }
        });

        setStatusBar();
    }
    /**
     * 设置沉浸式状态栏，需4.4版本以上
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    //将ActionBar添加进来
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = new MenuInflater(this) ;
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isCheckable()){
            item.setChecked(true);
        }
        return super.onOptionsItemSelected(item);
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_waimai = (TextView) findViewById(R.id.txt_waimai);
        txt_find = (TextView) findViewById(R.id.txt_find);
        txt_order = (TextView) findViewById(R.id.txt_order);

        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_waimai.setOnClickListener(this);
        txt_find.setOnClickListener(this);
        txt_order.setOnClickListener(this);
    }
    //重置所有文本的选中状态
    private void setSelected(){
        txt_waimai.setSelected(false);
        txt_find.setSelected(false);
        txt_order.setSelected(false);
    }
    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(homeFragment != null)fragmentTransaction.hide(homeFragment);
        if(findFragment != null)fragmentTransaction.hide(findFragment);
        if(communityFragment != null)fragmentTransaction.hide(communityFragment);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_waimai:
                setSelected();
                txt_waimai.setSelected(true);
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    fTransaction.add(R.id.ly_content,homeFragment);
                }else{
                    fTransaction.show(homeFragment);
                }
                break;
            case R.id.txt_find:
                setSelected();
                txt_find.setSelected(true);
                if(findFragment == null){
                    findFragment = new FindFragment();
                    fTransaction.add(R.id.ly_content,findFragment);
                }else{
                    fTransaction.show(findFragment);
                }
                break;
            case R.id.txt_order:
                setSelected();
                txt_order.setSelected(true);
                if(communityFragment == null){
                    communityFragment = new CommunityFragment();
                    fTransaction.add(R.id.ly_content, communityFragment);
                }else{
                    fTransaction.show(communityFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    //侧边栏的Item选择事件
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_video) {
            // 点击侧边栏第一项打开爽看视频
            startActivity(new Intent(MainActivity.this,VedioActivity.class));
        } else if (id == R.id.nav_music) {
            // 点击侧边栏第一项打开听个音乐
            startActivity(new Intent(MainActivity.this,MusicActivity.class));
        } else if (id == R.id.nav_gallery) {
            // 点击侧边栏第一项打开撩个妹子
            startActivity(new Intent(MainActivity.this,BeautyActivity.class));
        } else if (id == R.id.nav_duanzi) {
            // 点击侧边栏第一项打开看个段子
            startActivity(new Intent(MainActivity.this,JokeActivity.class));
        } else if (id == R.id.nav_setting) {
            // 点击侧边栏第一项打开系统设置
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
        } else if (id == R.id.nav_share) {
            // 点击侧边栏第一项打开分享
            showShare() ;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //分享
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        oks.setTitle("吃货宝App");
        oks.setTitleUrl("http://dongxi520.com/");
        oks.setText("测试分享功能");
        oks.setUrl("http://dongxi520.com/");
        oks.setComment("我是测试评论文本");
        oks.setSite(getString(R.string.app_name));
        oks.setSiteUrl("http://dongxi520.com/");

        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 获取控件
     */
    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
    }

    /**
     * 双击退出，根据时间来判断
     */
    long waitTime = 2000;
    long touchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if((currentTime-touchTime)>=waitTime) {
                //让Toast的显示时间和等待时间相同
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            }else {
                System.exit(0);//会跳屏，关闭时会出现黑屏，需要优化，不能用finish()
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
