package com.example.murotal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.murotal.apis.Constant;
import com.example.murotal.fragment.FragmentHome;
import com.example.murotal.tampilan.PlayerService;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.EventListener;

import supriyanto.tampilan.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class  MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EventListener {

    private SpaceNavigationView space;
    private FrameLayout fragment_base;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout layout = (FrameLayout) findViewById(R.id.fragment_base);
        getLayoutInflater().inflate(R.layout.content_main, layout);

        Constant.isAppOpen = true;

        loadFragment();
    }

    private void loadFragment(){
        FragmentHome home = new FragmentHome();
        showLoadFragment(home);
    }

    private void showLoadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_base, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        Constant.isAppOpen = false;
        if (PlayerService.exoplayer && !PlayerService.exoplayer.getPlayWhenReady()){
            Intent intent = new Intent(getApplicationContext(), PlayerService.class);
            intent.setAction(PlayerService.ACTION_STOP);
            startService(intent);
        }
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
