package com.example.murotal.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murotal.Modal.ModalListSurat;
import java.util.List;

import supriyanto.tampilan.R;

public class AdapterListSurat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ModalListSurat> modalListSurats;

    public AdapterListSurat(Context context, List<ModalListSurat> modalListSurats) {
    this.context = context;
    this.modalListSurats = modalListSurats;

    }

    public class myAdapterListSurat extends RecyclerView.ViewHolder{

        private TextView ar, id, nomor, translate;
        private ImageView share;
        private MediaPlayer player;

        public myAdapterListSurat(@NonNull View itemView) {
            super(itemView);

            ar = (TextView) itemView.findViewById(R.id.ar);
            id = (TextView) itemView.findViewById(R.id.id);
            nomor = (TextView) itemView.findViewById(R.id.nomor);
            translate = (TextView) itemView.findViewById(R.id.translate);
            share = (ImageView) itemView.findViewById(R.id.shares);
            player = new MediaPlayer();
        }
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
}
