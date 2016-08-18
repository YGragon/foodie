package com.dongxi.foodie.bean;

/**
 * Created by Administrator on 2016/8/18.
 */
public class VedioInfo {

    /**
     * desc : 描述
     * publishedAt : 时间
     * url : 地址
     * who : 姓名
     */

    private String desc;
    private String publishedAt;
    private String url;
    private String who;

    public VedioInfo(String desc, String publishedAt, String url, String who) {
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.url = url;
        this.who = who;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "VedioInfo{" +
                "desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                '}';
    }
}
