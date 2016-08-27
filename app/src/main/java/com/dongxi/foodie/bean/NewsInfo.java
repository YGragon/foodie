package com.dongxi.foodie.bean;

/**
 * Created by Administrator on 2016/8/25.
 */
public class NewsInfo {
    /**
     * count : 138
     * description : ...
     * id : 200
     * img : /info/150729/28b01f9a2bc2cb93c2d140cc2f8165ad.jpg
     * message : ...
     * time : 1438141821000
     * title : 要闻快递：误将消毒水当饮料 肯德基致歉
     */

    private int count;
    private String description;
    private int id;
    private String img;
    private String message;
    private long time;
    private String title;

    public NewsInfo(int count, int id, String img, long time, String title) {
        this.count = count;
        this.id = id;
        this.img = img;
        this.time = time;
        this.title = title;
    }

    public NewsInfo(int count, String description, int id, String img,
                    String message, long time, String title) {
        this.count = count;
        this.description = description;
        this.id = id;
        this.img = img;
        this.message = message;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        return "NewsInfo{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", title='" + title + '\'' +
                '}';
    }
}
