package com.dongxi.foodie.bean;

/**
 * Created by Administrator on 2016/8/18.
 */
public class JokeInfo {

    /**
     * author : aller
     * content : 那次在街上...
     * picUrl :
     */

    private String author;
    private String content;
    private String picUrl;

    public JokeInfo(String author, String content, String picUrl) {
        this.author = author;
        this.content = content;
        this.picUrl = picUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "JokeInfo{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
