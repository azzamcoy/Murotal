package com.example.murotal.asycn;

import android.os.AsyncTask;

import com.example.murotal.apis.Constant;
import com.example.murotal.interfaces.SongListener;
import com.example.murotal.ownmodal.ItemSong;
import com.example.murotal.utils.UtilJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadSong extends AsyncTask<String, String, String> {



    private SongListener songListener;
    private ArrayList<ItemSong> arrayList = new ArrayList<>();

    public LoadSong(SongListener songListener) {
        this.songListener = songListener;
    }

    @Override
    protected void onPreExecute() {
        songListener.onStart();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            JSONObject mainJson = new JSONObject(UtilJson.okHttpGet(strings[0]));
            JSONArray jsonArray = mainJson.getJSONArray(Constant.TAG_ROOT);

            if (jsonArray.length() > 0 && jsonArray.getJSONObject(0).has("songs_list")) {
                jsonArray = jsonArray.getJSONObject(0).getJSONArray("songs_list");
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objJson = jsonArray.getJSONObject(i);

                String id = objJson.getString(Constant.TAG_ID);
                String cid = objJson.getString(Constant.TAG_CAT_ID);
                String cname = objJson.getString(Constant.TAG_CAT_NAME);
                String artist = objJson.getString(Constant.TAG_ARTIST);
                String name = objJson.getString(Constant.TAG_SONG_NAME);
                String url = objJson.getString(Constant.TAG_MP3_URL);
                String desc = objJson.getString(Constant.TAG_DESC);
                String duration = objJson.getString(Constant.TAG_DURATION);
                String thumb = objJson.getString(Constant.TAG_THUMB_B).replace(" ", "%20");
                String thumb_small = objJson.getString(Constant.TAG_THUMB_S).replace(" ", "%20");
                String total_rate = objJson.getString(Constant.TAG_TOTAL_RATE);
                String avg_rate = objJson.getString(Constant.TAG_AVG_RATE);
                String views = objJson.getString(Constant.TAG_VIEWS);
                String downloads = objJson.getString(Constant.TAG_DOWNLOADS);

                ItemSong objItem = new ItemSong(id, cid, cname, artist, url, thumb, thumb_small, name, duration, desc, total_rate, avg_rate, views, downloads);
                arrayList.add(objItem);
            }
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        songListener.onEnd(s, arrayList);
        super.onPostExecute(s);
    }
}
