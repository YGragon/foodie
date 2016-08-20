package com.dongxi.foodie.dao;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Administrator on 2016/8/19.
 */
@HttpRequest(
        //        path = ""
//        builder = DefaultParamsBuilder.class/*可选参数, 控制参数构建过程, 定义参数签名, SSL证书等*/
        host = "http://www.tngou.net/api/cook/",
        path = "list?page=3&id=2&rows=10"
)

public class FoodsParams extends RequestParams {
    public String wd;
}
