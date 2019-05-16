package com.example.murotal.asycn;

import android.os.AsyncTask;
import android.util.Log;

import com.example.murotal.apis.Constant;
import com.example.murotal.interfaces.HomeRequest;
import com.example.murotal.ownmodal.Album;
import com.example.murotal.ownmodal.Artist;
import com.example.murotal.ownmodal.Category;
import com.example.murotal.ownmodal.ItemSong;
import com.example.murotal.utils.UtilJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestHome extends AsyncTask<String, String, String> {

    private HomeRequest homeListener;
    private ArrayList<Category> arrayCategory = new ArrayList<>();
    private ArrayList<Album> arrayListAlbums = new ArrayList<>();
    private ArrayList<Artist> arrayListArtist = new ArrayList<Artist>();
    private ArrayList<ItemSong> arrayListSongs = new ArrayList<>();

    public RequestHome(HomeRequest homeListener) {
        this.homeListener = homeListener;
    }

    @Override
    protected void onPreExecute() {
        homeListener.onStart();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            JSONObject mainJson = new JSONObject(UtilJson.okHttpGet(strings[0]));
            JSONObject jsonObject = mainJson.getJSONObject(Constant.TAG_ROOT);

            JSONArray jsonArrayArtist = jsonObject.getJSONArray("latest_artist");
            for (int i = 0; i < jsonArrayArtist.length(); i++) {
                JSONObject objJson = jsonArrayArtist.getJSONObject(i);

                String id = objJson.getString(Constant.TAG_ID);
                String name = objJson.getString(Constant.TAG_ARTIST_NAME);
                String image = objJson.getString(Constant.TAG_ARTIST_IMAGE);
                String thumb = objJson.getString(Constant.TAG_ARTIST_THUMB);

                Artist objItem = new Artist(id, name, image, thumb);
                arrayListArtist.add(objItem);

            }

            JSONArray jsonArrayAlbums = jsonObject.getJSONArray("latest_album");
            for (int i = 0; i < jsonArrayAlbums.length(); i++) {
                JSONObject objJson = jsonArrayAlbums.getJSONObject(i);

                String id = objJson.getString(Constant.TAG_AID);
                String name = objJson.getString(Constant.TAG_ALBUM_NAME);
                String image = objJson.getString(Constant.TAG_ALBUM_IMAGE);
                String thumb = objJson.getString(Constant.TAG_ALBUM_THUMB);

                Album objItem = new Album(id, name, image, thumb);
                arrayListAlbums.add(objItem);
            }

            JSONArray jsonArraySongs = jsonObject.getJSONArray("trending_songs");
            for (int i = 0; i < jsonArraySongs.length(); i++) {
                JSONObject objJson = jsonArraySongs.getJSONObject(i);

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
                arrayListSongs.add(objItem);
            }

            JSONArray jsonArrayCategory = jsonObject.getJSONArray("category");
            for (int i = 0; i<jsonArrayCategory.length(); i++){

                JSONObject obj = jsonArrayCategory.getJSONObject(i);

                String cid = obj.getString(Constant.TAG_CID);
                String cat_name = obj.getString(Constant.TAG_CAT_NAME);
                String cat_img = obj.getString(Constant.TAG_CAT_IMAGE);
                String cat_img_thumb = obj.getString(Constant.TAG_CAT_IMG_THUMB);

                Category category = new Category(cid, cat_name, cat_img);
                arrayCategory.add(category);
                Log.d("Category", category + "");
            }

           /* JSONArray jsonArray = jsonObject.getJSONArray("home_banner");

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
                arrayListSongs.add(objItem);
            }*/

            JSONArray allSong = jsonObject.getJSONArray("all_murottal");
            for (int i = 0; i<allSong.length(); i++){

                JSONObject objJson = allSong.getJSONObject(i);

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
                arrayListSongs.add(objItem);

            }

            return "1";

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        homeListener.onEnd(s, arrayCategory, arrayListAlbums, arrayListArtist, arrayListSongs);
        super.onPostExecute(s);
    }
}