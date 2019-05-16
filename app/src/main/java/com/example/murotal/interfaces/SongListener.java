package com.example.murotal.interfaces;

import com.example.murotal.ownmodal.Album;
import com.example.murotal.ownmodal.ItemSong;

import java.util.ArrayList;


public interface SongListener {
    void onStart();
    void onEnd(String success, ArrayList<ItemSong> arrayList);
}
