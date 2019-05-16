package com.example.murotal.tampilan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murotal.apis.Constant;
import com.example.murotal.asycn.LoadSong;
import com.example.murotal.interfaces.SongListener;
import com.example.murotal.ownmodal.ItemMyPlayList;
import com.example.murotal.ownmodal.ItemSong;
import com.example.murotal.ownmodal.MessageEvent;
import com.example.murotal.utils.DBHelper;
import com.example.murotal.utils.GlobalBus;
import com.example.murotal.utils.Methods;
import com.example.murotal.utils.PauseSableRotateAnimation;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import supriyanto.tampilan.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ActivityBase extends AppCompatActivity implements View.OnClickListener {Boolean expan = false, rotationAnim = false, adsBanner = false;
    SlidingUpPanelLayout slidingUpPanelLayout;
    LinearLayout ll_min_header, ll_max_header;
    ImagePagerAdapter adapter;
    DBHelper dbHelper;
    Animation animation_up, animation_down, animation_up_music, animation_down_music;
    Handler seekHandler = new Handler();
    SeekBar seekBar_min, seekBar_music;
    ViewPager viewPager;
    Methods methods;
    RatingBar ratingBar;
    String deviceId;
    RoundedImageView iv_max_song, iv_min_song, imageView_pager;
    PauseSableRotateAnimation rotateAnimation;
    TextView tv_min_title, tv_min_artist, tv_max_title, tv_max_artist, tv_music_title, tv_music_artist, tv_song_count, tv_current_time, tv_total_time;
    ImageView iv_music_bg, iv_min_previous, iv_min_play, iv_min_next, iv_max_fav, iv_max_option, iv_music_shuffle,
            iv_music_repeat, iv_music_previous, iv_music_next, iv_music_play, iv_music_add2playlist, iv_music_share,
            iv_music_download, iv_music_rate, iv_music_volume, imageView_heart;
    private Handler adHandler = new Handler();
    RelativeLayout rl_music_loading;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Constant.context = this;
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        methods = new Methods(this);
        dbHelper = new DBHelper(this);

        //slidingPanel
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);

        //seek bar
        seekBar_music = findViewById(R.id.seekbar_music);
        seekBar_min = findViewById(R.id.seek_bar);
        seekBar_min.setPadding(0, 0, 0, 0);

        //view pager
        viewPager = findViewById(R.id.audio_list_view);

        //image view
        iv_music_bg = findViewById(R.id.background_image_view);
        iv_music_play = findViewById(R.id.iv_music_play);
        iv_music_next = findViewById(R.id.iv_music_next);
        iv_music_previous = findViewById(R.id.iv_music_previous);
        iv_music_shuffle = findViewById(R.id.iv_music_shuffle);
        iv_music_repeat = findViewById(R.id.iv_music_repeat);
        iv_music_download = findViewById(R.id.tv_songlist_downloads);

        //linear layout
        ll_min_header = findViewById(R.id.ll_min_header);
        ll_max_header = findViewById(R.id.ll_max_header);

        //text view
        tv_current_time = findViewById(R.id.tv_music_time);
        tv_total_time = findViewById(R.id.tv_music_total_time);
        tv_song_count = findViewById(R.id.tv_music_song_count);
        tv_music_title = findViewById(R.id.tv_music_title);
        tv_music_artist = findViewById(R.id.tv_music_artist);
        tv_min_title = findViewById(R.id.tv_min_title);
        tv_min_artist = findViewById(R.id.tv_min_artist);
        tv_max_title = findViewById(R.id.tv_max_title);
        tv_max_artist = findViewById(R.id.tv_max_artist);
        iv_max_fav = findViewById(R.id.iv_max_fav);
        ratingBar = findViewById(R.id.rb_music);

        //adapter
        adapter = new ImagePagerAdapter();

        //relative layout
        rl_music_loading = findViewById(R.id.rl_music_loading);


        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset == 0.0f){
                    expan = false;
                    ll_min_header.setVisibility(View.VISIBLE);
                    ll_max_header.setVisibility(View.INVISIBLE);
                }else if (slideOffset > 0.0f && slideOffset < 1.0f){
                    ll_min_header.setVisibility(View.VISIBLE);
                    ll_max_header.setVisibility(View.VISIBLE);

                    if (expan){
                        ll_min_header.setAlpha(1.0f - slideOffset);
                        ll_max_header.setAlpha(0.0f + slideOffset);
                    }else{
                        ll_min_header.setAlpha(1.0f - slideOffset);
                        ll_max_header.setAlpha(slideOffset);
                    }
                }else{
                    expan = true;

                    ll_min_header.setVisibility(View.INVISIBLE);
                    ll_max_header.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    try{
                        viewPager.setCurrentItem(Constant.playPos);
                    }catch (Exception e){
                        adapter.notifyDataSetChanged();
                        viewPager.setCurrentItem(Constant.playPos);
                    }
                }
            }
        });

        seekBar_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int prog = seekBar.getProgress();
                try{
                    Intent intent = new Intent(ActivityBase.this, PlayerService.class);
                    intent.setAction(PlayerService.ACTION_SEEKTO);
                    intent.putExtra("seekto", methods.getSeekFromPercentage(prog, methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
                    startService(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        newRotationAnimation();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTextPagerAdapter(Constant.arrayList_play.get(position));

                View view = viewPager.findViewWithTag("myview" + position);
                if (view != null) {
                    ImageView iv = view.findViewById(R.id.iv_vp_play);
                    if (Constant.playPos == position) {
                        iv.setVisibility(View.GONE);
                    } else {
                        iv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv_current_time.setText("00:00");

        if (Constant.pushSID.equals("0")) {
            if (Constant.arrayList_play.size() == 0) {
                Constant.arrayList_play.addAll(dbHelper.loadDataRecent(true));
                if (Constant.arrayList_play.size() > 0) {
                    GlobalBus.getBus().postSticky(Constant.arrayList_play.get(Constant.playPos));
                }
            }
        } else {
            new LoadSong(new SongListener() {
                @Override
                public void onStart() {
                    Constant.pushSID = "0";
                }

                @Override
                public void onEnd(String success, ArrayList<ItemSong> arrayList) {
                    if (success.equals("1") && arrayList.size() > 0) {
                        Constant.isOnline = true;
                        Constant.arrayList_play.clear();
                        Constant.arrayList_play.addAll(arrayList);
                        Constant.playPos = 0;

                        Intent intent = new Intent(ActivityBase.this, PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        startService(intent);
                    }
                }
            }).execute(Constant.URL_SONG_1 + Constant.pushSID + Constant.URL_SONG_2 + deviceId);
        }

        startAdTimeCount();
    }

    private void startAdTimeCount() {
        adHandler.removeCallbacks(runnableAd);
        adHandler.postDelayed(runnableAd, Constant.bannerAdShowTime);
    }

    Runnable runnableAd = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    public void onClick(View view) {

    }

    private class ImagePagerAdapter extends PagerAdapter{

        private LayoutInflater inflater;

        public ImagePagerAdapter() {
            this.inflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return Constant.arrayList_play.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View imageLayout = inflater.inflate(R.layout.layout_viewpager, container, false);
            assert  imageLayout !=null;
            RoundedImageView imageview = imageLayout.findViewById(R.id.image);
            final ImageView imageView_play = imageLayout.findViewById(R.id.iv_vp_play);
            final ProgressBar spinner = imageLayout.findViewById(R.id.loading);

            if (Constant.playPos == position) {
                imageView_play.setVisibility(View.GONE);
            }

            if (Constant.isOnline) {
                Picasso.get()
                        .load(Constant.arrayList_play.get(position).getImageBig())
                        .resize(300, 300)
                        .placeholder(R.drawable.placeholder_song)
                        .into(imageview, new Callback() {
                            @Override
                            public void onSuccess() {
                                spinner.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                spinner.setVisibility(View.GONE);
                            }
                        });
            } else {
                Picasso.get()
                        .load(methods.getAlbumArtUri(Integer.parseInt(Constant.arrayList_play.get(position).getImageBig())))
                        .placeholder(R.drawable.placeholder_song)
                        .into(imageview);
                spinner.setVisibility(View.GONE);
            }

            imageView_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.playPos = viewPager.getCurrentItem();
                    rotationAnim = false;
                    if (!Constant.isOnline || methods.isNetworkAvailable()) {
                        Intent intent = new Intent(ActivityBase.this, PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        startService(intent);
                        imageView_play.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (position == 0) {
                rotationAnim = false;
                imageView_pager = imageview;
            }

            imageLayout.setTag("myview" + position);
            container.addView(imageLayout, 0);
            return imageLayout;
        }
    }

    private void newRotationAnimation(){
        if (rotateAnimation != null){
            rotateAnimation.cancel();
        }
        rotateAnimation = new PausableRotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(Constant.rotateSpeed);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
    }

    public void changeImageAnim(Boolean isPlay){
        /*try{
            if (isPlay){
                rotateAnimation.pause();
            }else{
                if (!rotationAnim){
                    rotationAnim = true;
                    if (viewPager)
                }
            }
        }*/
    }

    public void changeTextPagerAdapter(ItemSong itemSong){
        tv_music_artist.setText(itemSong.getArtist());
        tv_music_title.setText(itemSong.getTitle());
        tv_song_count.setText((viewPager.getCurrentItem() + 1) + "/" + Constant.arrayList_play.size());
    }

    public void changeText(ItemSong itemSong, String page){

        tv_min_title.setText(itemSong.getTitle());
        tv_min_artist.setText(itemSong.getArtist());

        tv_max_title.setText(itemSong.getTitle());
        tv_max_artist.setText(itemSong.getArtist());

        ratingBar.setRating(Integer.parseInt(itemSong.getAverageRating()));
        tv_music_title.setText(itemSong.getTitle());
        tv_music_artist.setText(itemSong.getArtist());

        tv_song_count.setText(Constant.playPos + 1 + "/" + Constant.arrayList_play.size());
        tv_total_time.setText(itemSong.getDuration());

        changeFav(dbHelper.checkFav(itemSong.getId()));

        if (Constant.isOnline) {
            Picasso.get()
                    .load(itemSong.getImageSmall())
                    .placeholder(R.drawable.placeholder_artist);

            Picasso.get()
                    .load(itemSong.getImageSmall())
                    .placeholder(R.drawable.placeholder_artist);
            if (ratingBar.getVisibility() == View.GONE) {
                ratingBar.setVisibility(View.VISIBLE);
                iv_max_fav.setVisibility(View.VISIBLE);

                iv_music_rate.setVisibility(View.VISIBLE);
            }

            if (Constant.isSongDownload) {
                iv_music_download.setVisibility(View.VISIBLE);
            } else {
                iv_music_download.setVisibility(View.GONE);
            }
        } else {
            Picasso.get()
                    .load(methods.getAlbumArtUri(Integer.parseInt(itemSong.getImageSmall())))
                    .placeholder(R.drawable.placeholder_artist);

            Picasso.get()
                    .load(methods.getAlbumArtUri(Integer.parseInt(itemSong.getImageSmall())))
                    .placeholder(R.drawable.placeholder_artist);

            if (ratingBar.getVisibility() == View.VISIBLE) {
                ratingBar.setVisibility(View.GONE);
                iv_max_fav.setVisibility(View.GONE);

                iv_music_rate.setVisibility(View.GONE);


                iv_music_download.setVisibility(View.GONE);
            }
        }

        if (!page.equals("")) {
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(Constant.arrayList_play.size());
        }
        viewPager.setCurrentItem(Constant.playPos);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdated();
        }
    };

    public void seekUpdated(){
        try {
            seekBar_min.setProgress(methods.getProgressPercentage(PlayerService.exoPlayer.getCurrentPosition(), methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
            seekBar_music.setProgress(methods.getProgressPercentage(PlayerService.exoPlayer.getCurrentPosition(), methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
            tv_current_time.setText(methods.milliSecondsToTimer(PlayerService.exoPlayer.getCurrentPosition()));
            seekBar_music.setSecondaryProgress(PlayerService.exoPlayer.getBufferedPercentage());
            if (PlayerService.exoPlayer.getPlayWhenReady() && Constant.isAppOpen) {
                seekHandler.removeCallbacks(run);
                seekHandler.postDelayed(run, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playPause() {
        if (Constant.arrayList_play.size() > 0) {
            Intent intent = new Intent(ActivityBase.this, PlayerService.class);
            if (Constant.isPlayed) {
                intent.setAction(PlayerService.ACTION_TOGGLE);
                startService(intent);
            } else {
                if (!Constant.isOnline || methods.isNetworkAvailable()) {
                    intent.setAction(PlayerService.ACTION_PLAY);
                    startService(intent);
                } else {
                    Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void next() {
        if (Constant.arrayList_play.size() > 0) {
            if (!Constant.isOnline || methods.isNetworkAvailable()) {
                rotationAnim = false;
                Intent intent = new Intent(ActivityBase.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_NEXT);
                startService(intent);
            } else {
                Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void previous() {
        if (Constant.arrayList_play.size() > 0) {
            if (!Constant.isOnline || methods.isNetworkAvailable()) {
                rotationAnim = false;
                Intent intent = new Intent(ActivityBase.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_PREVIOUS);
                startService(intent);
            } else {
                Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void setRepeat() {
        if (Constant.isRepeat) {
            Constant.isRepeat = false;
            iv_music_repeat.setImageDrawable(getResources().getDrawable(R.mipmap.ic_repeat));
        } else {
            Constant.isRepeat = true;
            iv_music_repeat.setImageDrawable(getResources().getDrawable(R.mipmap.ic_next_grey));
        }
    }

    public void setShuffle() {
        if (Constant.isSuffle) {
            Constant.isSuffle = false;
            iv_music_shuffle.setColorFilter(ContextCompat.getColor(ActivityBase.this, R.color.grey));
        } else {
            Constant.isSuffle = true;
            iv_music_shuffle.setColorFilter(ContextCompat.getColor(ActivityBase.this, R.color.colorPrimary));
        }
    }

    private void shareSong() {
        if (Constant.arrayList_play.size() > 0) {

            if (Constant.isOnline) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_song));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.listening) + " - " + Constant.arrayList_play.get(viewPager.getCurrentItem()).getTitle() + "\n\nvia " + getResources().getString(R.string.app_name) + " - http://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_song)));
            } else {
                if (checkPer()) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("audio/mp3");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Constant.arrayList_play.get(viewPager.getCurrentItem()).getUrl()));
                    share.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.listening) + " - " + Constant.arrayList_play.get(viewPager.getCurrentItem()).getTitle() + "\n\nvia " + getResources().getString(R.string.app_name) + " - http://play.google.com/store/apps/details?id=" + getPackageName());
                    startActivity(Intent.createChooser(share, getResources().getString(R.string.share_song)));
                }
            }
        } else {
            Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void fav() {
        if (dbHelper.checkFav(Constant.arrayList_play.get(Constant.playPos).getId())) {
            dbHelper.removeFromFav(Constant.arrayList_play.get(Constant.playPos).getId());
            Toast.makeText(ActivityBase.this, getResources().getString(R.string.removed_fav), Toast.LENGTH_SHORT).show();
            changeFav(false);
        } else {
            dbHelper.addToFav(Constant.arrayList_play.get(Constant.playPos));
            Toast.makeText(ActivityBase.this, getResources().getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
            changeFav(true);
        }
    }

    public void changeFav(Boolean isFav) {
        if (isFav) {
            iv_max_fav.setImageDrawable(getResources().getDrawable(R.mipmap.placeholder_song));
        } else {
            iv_max_fav.setImageDrawable(getResources().getDrawable(R.mipmap.placeholder_song));
        }
    }

    public void changePlayPauseIcon(Boolean isPlay) {
        if (!isPlay) {
            iv_music_play.setImageDrawable(getResources().getDrawable(R.mipmap.play));
            iv_min_play.setImageDrawable(getResources().getDrawable(R.mipmap.play));
        } else {
            iv_music_play.setImageDrawable(getResources().getDrawable(R.mipmap.puase));
            iv_min_play.setImageDrawable(getResources().getDrawable(R.mipmap.play));
        }
        seekUpdated();
    }

    public void isBuffering(Boolean isBuffer) {
        Constant.isPlaying = !isBuffer;
        if (isBuffer) {
            rl_music_loading.setVisibility(View.VISIBLE);
            iv_music_play.setVisibility(View.INVISIBLE);
        } else {
            rl_music_loading.setVisibility(View.INVISIBLE);
            iv_music_play.setVisibility(View.VISIBLE);
            changePlayPauseIcon(!isBuffer);
//            seekUpdated()
        }
        iv_music_next.setEnabled(!isBuffer);
        iv_music_previous.setEnabled(!isBuffer);
        iv_min_next.setEnabled(!isBuffer);
        iv_min_previous.setEnabled(!isBuffer);
        iv_music_download.setEnabled(!isBuffer);
        iv_min_play.setEnabled(!isBuffer);
        seekBar_music.setEnabled(!isBuffer);
    }

    public Boolean checkPer() {
        if ((ContextCompat.checkSelfPermission(ActivityBase.this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"}, 1);
            }
            return false;
        } else {
            return true;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSongChange(ItemSong itemSong) {
        changeText(itemSong, "home");
        Constant.context = ActivityBase.this;
        changeImageAnim(PlayerService.getInstance().getIsPlayling());
//        GlobalBus.getBus().removeStickyEvent(itemSong);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBufferChange(MessageEvent messageEvent) {

        if (messageEvent.message.equals("buffer")) {
            isBuffering(messageEvent.flag);
        } else {
            changePlayPauseIcon(messageEvent.flag);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onViewPagerChanged(ItemMyPlayList itemMyPlayList) {
        adapter.notifyDataSetChanged();
        GlobalBus.getBus().removeStickyEvent(itemMyPlayList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        GlobalBus.getBus().unregister(this);
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean canUseExternalStorage = false;

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    canUseExternalStorage = true;
                }

                if (!canUseExternalStorage) {
                    Toast.makeText(ActivityBase.this, getResources().getString(R.string.err_cannot_use_features), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        seekHandler.removeCallbacks(run);
        super.onPause();
    }

}
