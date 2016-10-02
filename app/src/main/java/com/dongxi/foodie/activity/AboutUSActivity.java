package com.dongxi.foodie.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.jaeger.library.StatusBarUtil;
import com.nguyenhoanglam.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

public class AboutUSActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressLayout progressLayout;
    private ProgressBar pb_progress;
    private List<Integer> skipIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        init();
        setStatusBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("作者比较害羞，正在来的路上...");
        progressLayout = (ProgressLayout) findViewById(R.id.progressLayout);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        skipIds = new ArrayList<>();
        skipIds.add(R.id.toolbar);
        skipIds.add(R.id.pb_progress);
        progressLayout.showLoading(skipIds);
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    private void init(){
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        settings.setUseWideViewPort(true);//设置webview推荐使用的窗口
        settings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);//显示无连接
                progressLayout.setVisibility(View.GONE);
                progressLayout.showError(ContextCompat.getDrawable(AboutUSActivity.this, R.drawable.ic_no_connection), "No connection", "RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(AboutUSActivity.this, "Reloading...", Toast.LENGTH_SHORT).show();
                    }
                }, skipIds);//显示无连接
            }
        });
        //WebView加载web资源
        webView.loadUrl("http://dongxi520.com/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;//返回webview的上一页
        }
        return super.onKeyDown(keyCode, event);
    }
}
