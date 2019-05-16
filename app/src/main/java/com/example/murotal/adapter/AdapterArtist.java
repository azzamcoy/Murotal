package com.example.murotal.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.murotal.ownmodal.Artist;

import java.util.ArrayList;

public class AdapterArtist extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<Artist> artists;





    public AdapterArtist(ArrayList<com.example.murotal.ownmodal.Artist> artistArrayList, FragmentActivity activity) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Artist {
    }
}
