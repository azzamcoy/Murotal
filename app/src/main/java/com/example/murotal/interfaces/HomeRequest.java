package com.example.murotal.interfaces;

import com.example.murotal.ownmodal.Album;
import com.example.murotal.ownmodal.Artist;
import com.example.murotal.ownmodal.Category;
import com.example.murotal.ownmodal.ItemSong;
import com.example.murotal.ownmodal.MisharyRashid;
import com.example.murotal.ownmodal.MuhammadThaha;

import java.util.ArrayList;


public interface HomeRequest {
    void onStart();

    void onEnd(String s, ArrayList<Category> arrayCategory, ArrayList<Album> arrayListAlbums, ArrayList<Artist> arrayListArtist, ArrayList<ItemSong> arrayListSongs);
}
