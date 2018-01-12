package com.example.ztz.ponymusic.model.bean;

/**
 * Created by ztz on 2018/1/5.
 */

public class EventMusicBean {

    private String show_link;
    private String img;
    private String music_name;
    private String name;


    public EventMusicBean(String show_link, String img, String music_name, String name) {
        this.show_link = show_link;
        this.img = img;
        this.music_name = music_name;
        this.name = name;
    }

    public String getShow_link() {
        return show_link;
    }

    public void setShow_link(String show_link) {
        this.show_link = show_link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
