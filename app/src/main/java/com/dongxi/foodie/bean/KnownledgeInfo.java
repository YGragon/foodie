package com.dongxi.foodie.bean;

/**
 * Created by Administrator on 2016/8/24.
 */
public class KnownledgeInfo {
    /**
     * count : 15
     * description : 素食减肥
     * id : 20113
     * img : /lore/160823/69b29c50e0b3e6067027c9b61f86bf98.jpg
     * title : 相信很多MM会采用素食减肥法
     */

    private int count;
    private String description;
    private int id;
    private String img;
    private String title;

    public KnownledgeInfo(int count, String description, int id, String img, String title) {
        this.count = count;
        this.description = description;
        this.id = id;
        this.img = img;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "KnownledgeInfo{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
