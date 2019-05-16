package com.example.murotal.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.murotal.Modal.Modal;
import com.example.murotal.adapter.Adapter;
import com.example.murotal.server.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import supriyanto.tampilan.R;

public class MainActivityCopy extends AppCompatActivity {

    private RequestQueue queue;
    Adapter adapter;
    List<Modal> list;
    RecyclerView recyclerView;
    GridLayoutManager gm;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        player = new MediaPlayer();
        gm = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gm);
        list = new ArrayList<>();

        queue = Volley.newRequestQueue(this);
        requestJson(Constant.QURAN_INDO);


    }

    private void requestJson(String url) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Hasil", response + "MUNCUL");
                for (int i = 0; i<response.length(); i++){

                    try {
                        JSONObject obj  = response.getJSONObject(i);
                        String arti = obj.getString("arti");
                        String type = obj.getString("type");
                        String ayat = obj.getString("ayat");
                        String asma = obj.getString("asma");
                        String audio = obj.getString("audio");
                        String nama = obj.getString("nama");
                        String nomor= obj.getString("nomor");

                        Modal modal = new Modal(arti, type, ayat, asma, audio, nama, nomor);
                        list.add(modal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter = new Adapter(getApplicationContext(), list);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

}
