package com.example.ztz.ponymusic.model.bean;

/**
 * Created by ztz on 2018/1/6.
 */

public class EventMineBean {

    private String path;
    private String song;
    private String songer;

    public EventMineBean(String path, String song, String songer) {
        this.path = path;
        this.song = song;
        this.songer = songer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSonger() {
        return songer;
    }

    public void setSonger(String songer) {
        this.songer = songer;
    }
}
