package com.dongxi.foodie.bean;

/**
 * Created by Administrator on 2016/8/18.
 */
public class JokeInfo {

    /**
     *
     * content : 那次在街上...
     * updatetime : 时间
     */

    private String content;
    private String updatetime;

    public JokeInfo(String content, String updatetime) {
        this.content = content;
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "JokeInfo{" +
                "content='" + content + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
