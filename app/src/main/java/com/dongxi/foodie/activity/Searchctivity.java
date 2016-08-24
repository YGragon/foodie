package com.dongxi.foodie.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongxi.foodie.R;
import com.dongxi.foodie.bean.Food;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


public class Searchctivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_KEY_TYPE = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private EditText mKeywordEt;
    private TextView mOperationTv;
    private String mType;

    List<Food> foodInfos = new ArrayList<Food>();//声明全局的才有效果
    private Food info;

    private ArrayAdapter<String> mArrAdapter;
    private SharedPreferences mPref;

    private LinearLayout mSearchHistoryLl;
    private List<String> mHistoryKeywords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPref =  getSharedPreferences("test",
                Activity.MODE_PRIVATE);

        mType = getIntent().getStringExtra(EXTRA_KEY_TYPE);
        String keyword = getIntent().getStringExtra(EXTRA_KEY_KEYWORD);
        if (!TextUtils.isEmpty(keyword)) {
            mKeywordEt.setText(keyword);
        }
        mHistoryKeywords = new ArrayList<String>();

        setContentView(R.layout.activity_searchctivity);


        ImageView iv_search = (ImageView)findViewById(R.id.iv_search);
        ImageView iv_scan = (ImageView)findViewById(R.id.iv_scan);
        //输入框的监听
        mKeywordEt = (EditText) findViewById(R.id.et_input);

        //搜索的点击
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeywordEt.getText().length() > 0) {
                    save();
                    String inputText = mKeywordEt.getText().toString() ;
//                    http://www.tngou.net/api/search?keyword="+inputText+"&name=info
                    String uriStr = "https://www.baidu.com/s?wd="+inputText+"&rsv_spt=1&rsv_iqid=0xf7bcd045000756be" +
                            "&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8" +
                            "&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=12" +
                            "&rsv_sug1=9&rsv_sug7=100&rsv_t=0d45nHswktgyZdj3RcAe7TJtzERY1GAWl3T7lQEr8IfTj3%2BGdlsXtORaKUeZUM9GvMm3";
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            ( Uri.parse(uriStr))
                    ).addCategory(Intent.CATEGORY_BROWSABLE)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(Searchctivity.this,"正在搜索"+mKeywordEt.getText().toString(),Toast.LENGTH_LONG).show();

                } else {
                    finish();
                }
            }

        });

        //二维码的扫描
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Searchctivity.this,ScanActivity.class));
            }
        });



        mKeywordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    return true;
                }
                return false;
            }
        });

        //EditText输入框的监听
        mKeywordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    if (mHistoryKeywords.size() > 0) {
                        mSearchHistoryLl.setVisibility(View.VISIBLE);
                    } else {
                        mSearchHistoryLl.setVisibility(View.GONE);
                    }
                } else {
                    mSearchHistoryLl.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mKeywordEt.requestFocus();

        initSearchHistory();

        setStatusBar();
    }

    //初始化
    private void initSearchHistory() {
        mSearchHistoryLl = (LinearLayout) findViewById(R.id.search_history_ll);
        ListView listView = (ListView) findViewById(R.id.search_history_lv);
        findViewById(R.id.clear_history_btn).setOnClickListener(this);
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD,"");
        if (!TextUtils.isEmpty(history)){
            List<String> list = new ArrayList<String>();
            for(Object o : history.split(",")) {
                list.add((String)o);
            }
            mHistoryKeywords = list;
        }
        if (mHistoryKeywords.size() > 0) {
            mSearchHistoryLl.setVisibility(View.VISIBLE);
        } else {
            mSearchHistoryLl.setVisibility(View.GONE);
        }
        mArrAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, mHistoryKeywords);
        listView.setAdapter(mArrAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mKeywordEt.setText(mHistoryKeywords.get(i));
                mSearchHistoryLl.setVisibility(View.GONE);
            }
        });
        mArrAdapter.notifyDataSetChanged();
    }

    //保存用户输入
    public void save() {
        String text = mKeywordEt.getText().toString();
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD,"");
        if (!TextUtils.isEmpty(text) && !oldText.contains(text)) {
            if(mHistoryKeywords.size()>4){
                Toast.makeText(this,"最多保存5条历史",Toast.LENGTH_SHORT).show();
                return;
            }
            mPref.edit().putString(KEY_SEARCH_HISTORY_KEYWORD, text + "," + oldText);
            mHistoryKeywords.add(0,text);
        }
        mArrAdapter.notifyDataSetChanged();
    }
    public void cleanHistory() {
        mPref.edit().clear();
        mHistoryKeywords.clear();
        mArrAdapter.notifyDataSetChanged();
        mSearchHistoryLl.setVisibility(View.GONE);
        Toast.makeText(this,"清楚搜索历史成功", Toast.LENGTH_SHORT);
    }

    //清空历史记录按钮
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_history_btn:
                cleanHistory();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String type = intent.getStringExtra(EXTRA_KEY_TYPE);
        String keyword = intent.getStringExtra(EXTRA_KEY_KEYWORD);

        if (!TextUtils.isEmpty(keyword)) {
            mKeywordEt.setText(keyword);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    //沉浸式状态栏
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
