package com.example.murotal.interfaces;

import android.provider.MediaStore;

import com.example.murotal.apis.Constant;

import java.util.ArrayList;

interface ArtistRequest {
    void onStart();
    void onEnd(ArrayList<MediaStore.Audio.Artists> artists);
}

