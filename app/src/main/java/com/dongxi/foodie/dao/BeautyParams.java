package com.dongxi.foodie.dao;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Administrator on 2016/8/20.
 */
@HttpRequest(
        host = "http://gank.io/api/search/query/listview/",
        path = "category/%E7%A6%8F%E5%88%A9/count/40/page/1"

)
public class BeautyParams extends RequestParams {
    public String wd;
}
