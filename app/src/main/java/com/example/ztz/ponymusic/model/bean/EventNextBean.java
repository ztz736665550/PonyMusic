package com.example.ztz.ponymusic.model.bean;

/**
 * Created by ztz on 2018/1/12.
 */

public class EventNextBean {

    private String auther;
    private String title;
    private String big_iamge;

    public EventNextBean(String auther, String title, String big_iamge) {
        this.auther = auther;
        this.title = title;
        this.big_iamge = big_iamge;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBig_iamge() {
        return big_iamge;
    }

    public void setBig_iamge(String big_iamge) {
        this.big_iamge = big_iamge;
    }
}
