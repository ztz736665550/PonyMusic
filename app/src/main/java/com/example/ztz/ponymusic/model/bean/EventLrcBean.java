package com.example.ztz.ponymusic.model.bean;

/**
 * Created by ztz on 2018/1/7.
 */

public class EventLrcBean {

    private String lrcContent;
    private String title;

    public EventLrcBean(String lrcContent, String title) {
        this.lrcContent = lrcContent;
        this.title = title;
    }

    public String getLrcContent() {
        return lrcContent;
    }

    public void setLrcContent(String lrcContent) {
        this.lrcContent = lrcContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
