package com.dongxi.foodie.bean;

import android.widget.Button;

/**
 * 作者：Aller  2016/7/20 09:23
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class Unfinshorder {
    private int imageID;
    private String name;
    private String uninsrtuction;
    private Button add;

    public Unfinshorder(int imageID, String name, String uninsrtuction,Button add) {
        this.imageID = imageID;
        this.name = name;
        this.uninsrtuction = uninsrtuction;
        this.add = add;
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

    public String getUninsrtuction() {
        return uninsrtuction;
    }

    public void setUninsrtuction(String uninsrtuction) {
        this.uninsrtuction = uninsrtuction;
    }

    public Button getAdd() {
        return add;
    }

    public void setAdd(Button add) {
        this.add = add;
    }
}
