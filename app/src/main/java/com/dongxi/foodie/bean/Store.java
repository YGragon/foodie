package com.dongxi.foodie.bean;

/**
 * 作者：Aller  2016/7/20 09:24
 * <p/>
 * 邮箱：1105894953@qq.com
 * <p/>
 * 描述：
 */
public class Store {
    private String name ;
    private float stars ;
    private String photos ;

    public Store(String name,float stars,String photos) {
        this.name = name;
        this.stars = stars;
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", stars=" + stars +
                ", photos='" + photos + '\'' +
                '}';
    }
}
