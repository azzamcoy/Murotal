package com.example.murotal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.murotal.adapter.AdapterAlbumsHome;
import com.example.murotal.adapter.AdapterAllSongListHome;
import com.example.murotal.adapter.AdapterArtist;
import com.example.murotal.adapter.AdapterCategory;
import com.example.murotal.apis.Constant;
import com.example.murotal.asycn.RequestHome;
import com.example.murotal.interfaces.HomeRequest;
import com.example.murotal.ownmodal.Album;
import com.example.murotal.ownmodal.Artist;
import com.example.murotal.ownmodal.Category;
import com.example.murotal.ownmodal.ItemSong;
import com.example.murotal.utils.MethodJava;

import java.util.ArrayList;

import supriyanto.tampilan.R;


public class FragmentHome extends Fragment {

    View view;
    LinearLayout ll_popular, ll_album, ll_category, ll_artist, ll_all;
    TextView tv_popular, tv_album, tv_category, tv_artist, tv_all;
    RecyclerView rv_popular, rv_album, rv_category, rv_artist, rv_all;

    //All ArrayList
    ArrayList<supriyanto.ownmodal.MisharyRashid> misharyRashids;
    ArrayList<ItemSong> songArrayList;
    ArrayList<Album> albumArrayList;
    ArrayList<Artist> artistArrayList;
    ArrayList<Category> categoryArrayList;

    //Component
    MethodJava methodJava;
    GridLayoutManager manager, category, album, song;

    //All Adapter
    AdapterArtist adapterArtist;
    AdapterCategory adapterCategory;
    AdapterAlbumsHome adapterAlbumsHome;
    AdapterAllSongListHome adapterAllSongList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        methodJava = new MethodJava(getActivity());

        //LinearLayout
        ll_popular = view.findViewById(R.id.ll_popular);
        ll_album = view.findViewById(R.id.ll_album);
        ll_category = view.findViewById(R.id.ll_category);
        ll_artist = view.findViewById(R.id.ll_artist);
        ll_all = view.findViewById(R.id.ll_all);

        //TextView
        tv_popular = view.findViewById(R.id.tv_popular);
        tv_album = view.findViewById(R.id.tv_album);
        tv_category = view.findViewById(R.id.tv_category);
        tv_artist = view.findViewById(R.id.tv_artist);
        tv_all = view.findViewById(R.id.tv_all);

        //RecyclerView
        rv_popular = view.findViewById(R.id.rv_popular);
        rv_album = view.findViewById(R.id.rv_album);
        rv_category = view.findViewById(R.id.rv_category);
        rv_artist = view.findViewById(R.id.rv_artist);
        rv_all = view.findViewById(R.id.rv_all);

        //DeclareArrayList
        misharyRashids = new ArrayList<>();
        songArrayList= new ArrayList<>();
        artistArrayList = new ArrayList<Artist>();
        categoryArrayList = new ArrayList<Category>();
        albumArrayList = new ArrayList<>();

        //Layout
        manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        category = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        album = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
        song = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);

        //RecyclerView
        rv_artist.setLayoutManager(manager);
        rv_category.setLayoutManager(category);
        rv_album.setLayoutManager(album);
        rv_all.setLayoutManager(song);

        getAllData();

        return view;

    }

    private void getAllData() {

        if (methodJava.checkInternet()){

            RequestHome requestHome = new RequestHome(new HomeRequest() {
                @Override
                public void onStart() {
                    ll_category.setVisibility(View.GONE);
                    ll_popular.setVisibility(View.GONE);
                    ll_artist.setVisibility(View.GONE);
                    ll_all.setVisibility(View.GONE);
                    ll_album.setVisibility(View.GONE);

                }

                @Override
                public void onEnd(String success, ArrayList<Category> categories, ArrayList<Album> albums, ArrayList<Artist> artists, ArrayList<ItemSong> itemSongs) {

                    if (getActivity() != null){

                        if (success.equals("1")){

                            albumArrayList.addAll(albums);
                            categoryArrayList.addAll(categories);
                            artistArrayList.addAll(artists);
                            songArrayList.addAll(itemSongs);

                            adapterCategory = new AdapterCategory(getActivity(), categoryArrayList);
                            rv_category.setAdapter(adapterCategory);

                            adapterArtist = new AdapterArtist(artistArrayList, getActivity());
                            rv_artist.setAdapter(adapterArtist);

                            adapterAlbumsHome = new AdapterAlbumsHome(getActivity(), albumArrayList);
                            rv_album.setAdapter(adapterAlbumsHome);

                            adapterAllSongList = new AdapterAllSongListHome(songArrayList, getActivity());
                            rv_all.setAdapter(adapterAllSongList);

                            setEmpty();

                        }
                    }
                }
            });
            requestHome.execute(Constant.URL_HOME);
        }
    }

    private void setEmpty(){
        if (artistArrayList.size() == 0){
            ll_artist.setVisibility(View.GONE);
        }else{
            ll_artist.setVisibility(View.VISIBLE);
        }

       if (categoryArrayList.size() == 0){
            ll_category.setVisibility(View.GONE);
       }else{
           ll_category.setVisibility(View.VISIBLE);
       }

       if (albumArrayList.size() == 0){
           ll_album.setVisibility(View.GONE);
       }else{
           ll_album.setVisibility(View.VISIBLE);
       }

       if (songArrayList.size() == 0){
           ll_all.setVisibility(View.GONE);
       }else{
           ll_all.setVisibility(View.VISIBLE);
       }
    }
}
