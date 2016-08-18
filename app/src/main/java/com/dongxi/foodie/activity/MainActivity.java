package com.dongxi.foodie.activity;

import android.app.FragmentTransaction;
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
import com.dongxi.foodie.fragment.OrderFragment;
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
    private OrderFragment orderFragment;
    private android.app.FragmentManager fManager ;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        //sputils是對SharedPreferences的封裝，代碼就不上了，大家理解意思就行了
//        if(PrefUtils.get(this,"theme","dayTheme").equals("dayTheme"))
//        {
//            //默認是白天主題
//            setTheme(R.style.dayTheme);
//        }else
//        {
//            //否则是晚上主題
//            setTheme(R.style.nightTheme);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fManager = getFragmentManager();
        bindViews();
        txt_waimai.performClick();   //模拟一次点击，既进去后选择第一项


        findViews(); //获取控件
        toolbar.setTitle("Foodie");//设置Toolbar标题

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
                    case R.id.toolBar_btn_login:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
        if(orderFragment != null)fragmentTransaction.hide(orderFragment);
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
                if(orderFragment == null){
                    orderFragment = new OrderFragment();
                    fTransaction.add(R.id.ly_content,orderFragment);
                }else{
                    fTransaction.show(orderFragment);
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
            Intent intent = new Intent(MainActivity.this,VedioActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_music) {
            // 点击侧边栏第一项打开听个音乐
            startActivity(new Intent(MainActivity.this,MusicActivity.class));

        } else if (id == R.id.nav_gallery) {
            // 点击侧边栏第一项打开撩个妹子

        } else if (id == R.id.nav_duanzi) {
            // 点击侧边栏第一项打开看个段子
            startActivity(new Intent(MainActivity.this,JokeActivity.class));
        } else if (id == R.id.nav_setting) {
            // 点击侧边栏第一项打开系统设置
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
        } else if (id == R.id.nav_share) {
            // 点击侧边栏第一项打开分享
            showShare() ;

        } else if (id == R.id.nav_mode) {
            //点击完成模式的切换
//            if(PrefUtils.get(MainActivity.this,"theme","dayTheme").equals("dayTheme"))
//            {
//                PrefUtils.put(MainActivity.this,"theme","nightTheme");
//
//            }else
//            {
//                PrefUtils.put(MainActivity.this, "theme", "dayTheme");
//            }
//            this.recreate();
            Toast.makeText(MainActivity.this,"后期载实现换肤",Toast.LENGTH_SHORT).show();
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

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("吃货宝App");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://dongxi520.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("测试分享功能");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://dongxi520.com/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
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
