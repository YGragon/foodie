package com.dongxi.foodie.bean;

/**
 * Created by Administrator on 2016/8/20.
 */
public class Beauty {

    /**
     * publishedAt : 图片时间
     * url : http://ww2.sinaimg.cn/large/7a8aed7bjw1exfffnlf2gj20hq0qoju9.jpg
     */

    private String publishedAt;
    private String url;

    public Beauty(String publishedAt, String url) {
        this.publishedAt = publishedAt;
        this.url = url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Beauty{" +
                "publishedAt='" + publishedAt + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
