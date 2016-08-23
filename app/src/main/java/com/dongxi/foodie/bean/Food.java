package com.dongxi.foodie.bean;

/**
 * 作者：Aller  2016/7/20 09:24
 *
 * 邮箱：1105894953@qq.com
 *
 * 描述：
 */
public class Food {
    private int id;
    private String name;//名称
    private String img;//图片
    private String count ;//访问次数
    private String description ;//描述
    private String food ;//食材


    public Food() {
    }

    public Food(int id, String name, String img, String count) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.count = count;
    }

    public Food(int id, String name, String img, String count, String description, String food) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.count = count;
        this.description = description;
        this.food = food;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", count='" + count + '\'' +
                ", description='" + description + '\'' +
                ", food='" + food + '\'' +
                '}';
    }
}
