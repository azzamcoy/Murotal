package com.example.murotal.ownmodal;

import java.io.Serializable;

public class Album implements Serializable {
    private String id,name, image, thumb, artist = "";

    public Album(String id, String name, String image, String thumb) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.thumb = thumb;
    }

    public Album(String id, String name, String image, String thumb, String artist) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.thumb = thumb;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
