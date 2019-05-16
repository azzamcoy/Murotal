package com.example.murotal.asycn;

import android.os.AsyncTask;

import com.example.murotal.apis.Constant;
import com.example.murotal.interfaces.HomeRequest;
import com.example.murotal.ownmodal.Artist;
import com.example.murotal.ownmodal.MisharyRashid;
import com.example.murotal.ownmodal.MuhammadThaha;
import com.example.murotal.utils.UtilJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RequestMurottal extends AsyncTask<String, String, String> {

    HomeRequest homeRequest;
    ArrayList<Artist> artists = new ArrayList<Artist>();
    ArrayList<MisharyRashid> misharyRashids = new ArrayList<>();
    ArrayList<MuhammadThaha> muhammadThahas = new ArrayList<>();

    public RequestMurottal(HomeRequest homeRequest) {
        this.homeRequest = homeRequest;
    }


    @Override
    protected String doInBackground(String... strings) {

        try {
            JSONObject object = new JSONObject(UtilJson.okHttpGet(strings[0]));
            JSONObject object1 = object.getJSONObject(Constant.TAG_ROOT);

            JSONArray array = object1.getJSONArray("latest_artist");

            for (int i = 0; i < array.length(); i++) {

                JSONObject object2 = array.getJSONObject(i);

                String id = object2.getString(Constant.TAG_ID);
                String name = object2.getString(Constant.TAG_ARTIST_NAME);
                String image = object2.getString(Constant.TAG_ARTIST_IMAGE);
                String thumb = object2.getString(Constant.TAG_ARTIST_THUMB);

                Artist artist = new Artist(id, name, image, thumb);
                artists.add(artist);

            }

            return "1";

        } catch (JSONException e) {
            e.printStackTrace();

            return "0";
        }
    }
}
