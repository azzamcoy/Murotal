package com.example.murotal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.murotal.interfaces.ClickListenerPlayList;
import com.example.murotal.ownmodal.ItemMyPlayList;
import com.example.murotal.utils.DBHelper;

import java.util.ArrayList;

import supriyanto.tampilan.R;

public class AdapterPlaylistDialog extends RecyclerView.Adapter<AdapterPlaylistDialog.MyViewHolder> {

    private DBHelper dbHelper;
    private Context context;
    private ArrayList<ItemMyPlayList> arrayList;
    private ArrayList<ItemMyPlayList> filteredArrayList;
    private AdapterCategory.NameFilter filter;
    private ClickListenerPlayList clickListenerPlayList;



    public AdapterPlaylistDialog(Context context, ArrayList<ItemMyPlayList> arrayList_playlist, ClickListenerPlayList clickListenerPlayList) {
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView_playlist_dialog);
        }
    }


    @NonNull
    @Override
    public AdapterPlaylistDialog.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPlaylistDialog.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
