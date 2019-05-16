package com.example.murotal.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.murotal.interfaces.ClickListenerPlayList;
import com.example.murotal.ownmodal.ItemSong;
import com.example.murotal.utils.DBHelper;
import com.example.murotal.utils.Methods;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import supriyanto.tampilan.R;


public class AdapterAllSongListHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> { Methods methods;
    ArrayList<ItemSong> itemSongArrayList;
    ArrayList<ItemSong> filteredArrayList;
    Context context;
    DBHelper dbHelper;
    String type;
    ClickListenerPlayList clickListenerPlayList;

    public AdapterAllSongListHome(ArrayList<ItemSong> itemSongArrayList, Context context, ClickListenerPlayList clickListenerPlayList, String type) {
        this.itemSongArrayList = itemSongArrayList;
        this.context = context;
        this.clickListenerPlayList = clickListenerPlayList;
        this.filteredArrayList = itemSongArrayList;
        this.type = type;
        methods = new Methods(context);
        dbHelper = new DBHelper(context);
    }


    private class myAdapterSong extends RecyclerView.ViewHolder{

        private TextView title, duration, artist;
        private RoundedImageView image;
        private RelativeLayout click;

        public myAdapterSong(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            duration = (TextView) itemView.findViewById(R.id.duration);
            artist = (TextView) itemView.findViewById(R.id.artist);
            image = (RoundedImageView) itemView.findViewById(R.id.image);
            click = (RelativeLayout) itemView.findViewById(R.id.android_pay);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_song_home, parent, false);

        return new myAdapterSong(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        ItemSong itemSong = itemSongArrayList.get(position);
        ((myAdapterSong) holder).title.setText(itemSong.getTitle());
        ((myAdapterSong) holder).duration.setText(itemSong.getDuration());
        ((myAdapterSong) holder).artist.setText(itemSong.getArtist());

        Picasso.get().load(itemSong.getImageBig()).into(((myAdapterSong)holder).image);

        ((myAdapterSong) holder).click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerPlayList.onClick(getPosition(itemSongArrayList.get(holder.getAdapterPosition()).getId()));
                Log.d("halo", "Halo gan");
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemSongArrayList.size();
    }

    private int getPosition(String id) {
        int count=0;
        for(int i=0;i<filteredArrayList.size();i++) {
            if(id.equals(filteredArrayList.get(i).getId())) {
                count = i;
                break;
            }
        }
        return count;
    }
}
