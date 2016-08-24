package com.dongxi.foodie.bean;

/**
 * Created by Administrator on 2016/8/24.
 */
public class QuestionInfo {

    /**
     * count : 193
     * description : 专家解释，餐盘纸及食品包装盒表面的印刷油墨基本都含苯类物质，“此类餐盘纸一般颜色较鲜艳，大多为有机染料，若在体内长期积累，会造成损伤，有致癌风险
     * img : /ask/150801/73d60caa38b5e650f391b46799d1aee9.jpg
     * time : 1438401923000
     * title : 薯条倒在餐盘纸上会致癌吗？
     */

    private int count;
    private String description;
    private String img;
    private long time;
    private String title;

    public QuestionInfo(int count, String description, String img, long time, String title) {
        this.count = count;
        this.description = description;
        this.img = img;
        this.time = time;
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", time=" + time +
                ", title='" + title + '\'' +
                '}';
    }
}
