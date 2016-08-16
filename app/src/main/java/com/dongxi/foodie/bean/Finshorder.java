package com.dongxi.foodie.bean;

/**
 * 作者：Aller  2016/7/20 09:23
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class Finshorder {
    private int imageID;
    private String name;
    private String finshinstruction;

    public Finshorder(int imageID, String name, String finshinstruction) {
        this.imageID = imageID;
        this.name = name;
        this.finshinstruction = finshinstruction;
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

    public String getFinshinstruction() {
        return finshinstruction;
    }

    public void setFinshinstruction(String finshinstruction) {
        this.finshinstruction = finshinstruction;
    }
}
