package com.example.ztz.ponymusic.model.bean;

/**
 * Created by ztz on 2018/1/5.
 */

public class PlayEventMusicBean {

    private String show_link;
    private String music_name;
    private String name;
    private String music_image;
    private String big_music_image;
    private String song_id;

    public PlayEventMusicBean(String show_link, String music_name, String name, String music_image, String big_music_image, String song_id) {
        this.show_link = show_link;
        this.music_name = music_name;
        this.name = name;
        this.music_image = music_image;
        this.big_music_image = big_music_image;
        this.song_id = song_id;
    }

    public String getShow_link() {
        return show_link;
    }

    public void setShow_link(String show_link) {
        this.show_link = show_link;
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

    public String getMusic_image() {
        return music_image;
    }

    public void setMusic_image(String music_image) {
        this.music_image = music_image;
    }

    public String getBig_music_image() {
        return big_music_image;
    }

    public void setBig_music_image(String big_music_image) {
        this.big_music_image = big_music_image;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }
}
