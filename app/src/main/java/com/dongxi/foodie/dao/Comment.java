package com.dongxi.foodie.dao;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by Administrator on 2016/8/23.
 */
public class Comment {
    private Spanned comment;

    public Comment(String comment) {
        this.comment = Html.fromHtml("<font color='#4A766E'>zhaizu: </font>" + comment);
    }

    public Spanned getComment() {
        return comment;
    }
}
