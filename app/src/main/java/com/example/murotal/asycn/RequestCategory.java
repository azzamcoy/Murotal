package com.example.murotal.asycn;

import android.os.AsyncTask;

import com.example.murotal.apis.Constant;
import com.example.murotal.interfaces.CategoryRequest;
import com.example.murotal.ownmodal.Category;
import com.example.murotal.utils.UtilJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RequestCategory extends AsyncTask<String, String, String> {

    CategoryRequest categoryRequest;
    ArrayList<Category> arrayList = new ArrayList<>();

    public RequestCategory(CategoryRequest categoryRequest) {
        this.categoryRequest = categoryRequest;
    }

    @Override
    protected void onPreExecute() {
        categoryRequest.onStart();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            JSONObject object = new JSONObject(UtilJson.okHttpGet(strings[0]));
            JSONArray array = object.getJSONArray(Constant.TAG_ROOT);

            for (int index = 0; index<array.length(); index++){

                JSONObject obj = array.getJSONObject(index);
                String id = obj.getString(Constant.TAG_CID);
                String name = obj.getString(Constant.TAG_CAT_NAME);
                String image = obj.getString(Constant.TAG_CAT_IMAGE);

                Category category = new Category(id, name, image);
                arrayList.add(category);
            }
            return "1";
        } catch (JSONException e) {
            e.printStackTrace();
            return "0";
        }

    }

    @Override
    protected void onPostExecute(String s) {
        categoryRequest.onEndt(s, arrayList);
        super.onPostExecute(s);
    }
}
