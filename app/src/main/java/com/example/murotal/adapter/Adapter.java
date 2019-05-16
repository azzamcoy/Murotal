package com.example.murotal.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murotal.Modal.Modal;

import com.example.murotal.server.Constant;
import com.example.murotal.tampilan.QuranActivity;
import com.example.murotal.tampilan.QuranPlayer;

import java.io.IOException;
import java.util.List;

import supriyanto.tampilan.R;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Modal> modalList;

    public Adapter(Context context, List<Modal> modalList) {
        this.context = context;
        this.modalList = modalList;
    }

    public class myAdapter extends RecyclerView.ViewHolder {

        TextView arti, keterangan, nama, asma;
        ImageView audio1;
        MediaPlayer player;
        Button audio;
        RelativeLayout cover_rel;

        public myAdapter(@NonNull View itemView) {
            super(itemView);

            arti = (TextView) itemView.findViewById(R.id.arti);
            audio = (Button) itemView.findViewById(R.id.audio);
            audio1 = (ImageView) itemView.findViewById(R.id.audio1);
            keterangan = (TextView) itemView.findViewById(R.id.keterangan);
            nama = (TextView) itemView.findViewById(R.id.nama);
            asma = (TextView) itemView.findViewById(R.id.asma);
            cover_rel = (RelativeLayout) itemView.findViewById(R.id.cover_rel);
            player = new MediaPlayer();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_data, parent, false);

        return  new myAdapter(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final Modal yoi = modalList.get(position);

        ((myAdapter) holder).nama.setText(yoi.getArti());
        ((myAdapter) holder).arti.setText(yoi.getNomor());
        ((myAdapter) holder).keterangan.setText(yoi.getNama());
        ((myAdapter) holder).asma.setText(yoi.getAsma());

        ((myAdapter) holder).cover_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holder.getAdapterPosition() + 1;
                String audio = yoi.getAudio();
                String arti = yoi.getArti();
                String type = yoi.getType();
                String ayat = yoi.getAyat();

                Log.d("NOMOR", Constant.LIST_AYAT+i+Constant.JSON_PRETTY);
                Intent goScreen = new Intent(v.getContext(), QuranActivity.class);
                goScreen.putExtra("nomor", i);
                goScreen.putExtra("audio", audio);
                goScreen.putExtra("arti", arti);
                goScreen.putExtra("type", type);
                goScreen.putExtra("ayat", ayat);
                v.getContext().startActivity(goScreen);
            }
        });

        ((myAdapter) holder).audio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (((myAdapter) holder).player.isPlaying() && ((myAdapter) holder).player != null){

                    Toast.makeText(context, "stoped", Toast.LENGTH_LONG).show();
                    ((myAdapter) holder).player.stop();
                    ((myAdapter) holder).audio1.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                }else{
                    ((myAdapter) holder).player.reset();
                    try {
                        final String i = yoi.getAudio();
                        ((myAdapter) holder).player.setDataSource(i);
                        ((myAdapter) holder).player.prepareAsync();
                        Log.d("AYAH", i);
                        ((myAdapter) holder).player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                ((myAdapter) holder).player.start();
                                ((myAdapter) holder).audio1.setImageResource(R.drawable.ic_pause_black_24dp);
                            }
                        });
                        Intent intent = new Intent(v.getContext(), QuranPlayer.class);
                        intent.putExtra("songs", i);
                        Log.d("kamuapa", i);
                        v.getContext().startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, "yuhu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
