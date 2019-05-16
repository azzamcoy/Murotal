package com.example.murotal.tampilan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.murotal.Modal.ModalListSurat;
import com.example.murotal.adapter.AdapterListSurat;
import com.example.murotal.server.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import supriyanto.tampilan.R;

public class QuranActivity extends AppCompatActivity {

    int get;
    private RequestQueue queue;
    private GridLayoutManager gm;
    private List<ModalListSurat> suratList;
    private AdapterListSurat adapterListSurat;
    private RecyclerView recyclerView;
    TextView et_type, et_asma, et_ayat;
    String type, asma, ayat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);

        get = getIntent().getExtras().getInt("nomor");
        asma = getIntent().getStringExtra("arti");
        type = getIntent().getStringExtra("type");
        ayat = getIntent().getStringExtra("ayat");

        Log.d("HASILNYA", type + "type" + ayat + "ayat" + asma + "asma");
        queue = Volley.newRequestQueue(this);

        et_type = (TextView) findViewById(R.id.type);
        et_asma = (TextView) findViewById(R.id.asma);
        et_ayat = (TextView) findViewById(R.id.ayat);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        gm = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(gm);
        suratList = new ArrayList<>();

        et_type.setText(type);
        et_asma.setText(asma);
        et_ayat.setText(ayat);

        requestListAyat();

    }

    private void requestListAyat() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constant.LIST_AYAT + get + Constant.JSON_PRETTY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length(); i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        String ar = obj.getString("ar");
                        String id = obj.getString("id");
                        String nomor = obj.getString("nomor");
                        String tr = obj.getString("tr");

                        Log.d("OYeoye", ar + id + nomor + tr);

                        ModalListSurat modalListSurat = new ModalListSurat(ar, id, nomor, tr);
                        suratList.add(modalListSurat);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapterListSurat = new AdapterListSurat(getApplicationContext(), suratList);
                    recyclerView.setAdapter(adapterListSurat);
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
