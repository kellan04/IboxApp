package com.iboxapp.ibox.module;

/**
 * Created by gongchen on 2016/4/6.
 */
public class CommentInfo {
    private String name;//评论来自
    private String date;//评论日期
    private String comments;//评论内容

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comment) {
        this.comments = comment;
    }

    public CommentInfo(String name, String date, String text) {
        super();
        this.name = name;
        this.date = date;
        this.comments = text;
    }
}
