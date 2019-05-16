package com.example.murotal.ownmodal;

import java.io.Serializable;

public class MuhammadThaha implements Serializable {

    private String gambar, judul, deskripsi, durasi, url;

    public MuhammadThaha(String gambar, String judul, String deskripsi, String durasi, String url) {
        this.gambar = gambar;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.durasi = durasi;
        this.url = url;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
