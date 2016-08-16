package com.dongxi.foodie.bean;

/**
 * 作者：Aller  2016/7/26 08:37
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class Order {
    private int imageID;
    private String name;
    private String insrtuction;

    public Order(int imageID, String name, String insrtuction) {
        this.imageID = imageID;
        this.name = name;
        this.insrtuction = insrtuction;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsrtuction() {
        return insrtuction;
    }

    public void setInsrtuction(String insrtuction) {
        this.insrtuction = insrtuction;
    }
}
