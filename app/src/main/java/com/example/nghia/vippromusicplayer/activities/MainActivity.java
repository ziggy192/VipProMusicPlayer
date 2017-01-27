package com.example.nghia.vippromusicplayer.activities;

import android.os.Bundle;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.activities.activities_as_fragments.MainScreenFragment;
import com.example.nghia.vippromusicplayer.activities.activities_as_fragments.MusicPlayingFragment;
import com.example.nghia.vippromusicplayer.events.OnSongItemClickedEvent;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.example.nghia.vippromusicplayer.utils.BackgroundMusicManager;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;
import com.example.nghia.vippromusicplayer.utils.Utils;
import com.example.nghia.vippromusicplayer.viewholders.BottomSheetViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements BackgroundMusicManager.MusicController{

    private static final String TAG = MainActivity.class.toString();
    @BindView(R.id.imv_item_song_thumbail)
    ImageView imvBottomSongThumbail;
    @BindView(R.id.fab_pause)
    FloatingActionButton fabPause;

    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;

    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetViewHolder btsViewHolder;
    private Handler mHandler = new Handler();
//    private Hybrid hybrid;

    private SongsDetail playingSongDetail;

    private BackgroundMusicManager musicManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicManager = BackgroundMusicManager.getInstance();

        setContentView(R.layout.activity_testing_new_basic_main);
        ButterKnife.bind(this);
        setupUI();
        changeFragment(new MainScreenFragment(), false);

    }

    private void setupUI() {
//        hybrid = Hybrid.getInstance(this);

        RelativeLayout rlBottomSheet = (RelativeLayout) findViewById(R.id.bts_music_player);

        this.btsViewHolder = new BottomSheetViewHolder(rlBottomSheet);

        this.bottomSheetBehavior = BottomSheetBehavior.from(rlBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        imvBottomSongThumbail.startAnimation(rotateAnimation);

    }

    public void minimizeMusicPlayer() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }

    @OnClick(R.id.bts_music_player)
    public void maximizeMusicPlayer() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        changeFragment(MusicPlayingFragment.create(playingSongDetail, this), true);
    }



    @OnClick(R.id.fab_pause)
    public void fabClicked() {
        musicManager.changePlayingState();
        if (musicManager.isPlaying()) {
            fabPause.setImageResource(R.drawable.ic_pause_white_36px);
        } else {
            fabPause.setImageResource(R.drawable.ic_play_arrow_white_24px);
        }
    }

    /**
     * Update timer on seekbar
     */
    @Override
    public void updateProgress() {
        mHandler.postDelayed(mUpdateTimeTask, 100);

    }


    @Override
    public void stopUpdatingProgress() {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void updateUI() {
        if (musicManager.getPlayingSong()!=null)
        {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        Toast.makeText(this, "updating UI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorNoti(String erroMEss) {
        Toast.makeText(this, erroMEss, Toast.LENGTH_SHORT).show();
    }


    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            int totalDuration = musicManager.getDuration();
            int currentDuration = musicManager.getCurrentPosition();

            // Displaying Total Duration time
//            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
//            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (Utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            progressBar.setProgress(progress);



            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };



    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        BackgroundMusicManager.getInstance().setCurrentMusicController(this);
        updateUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void fragmentChanging(DemandFragmentChangingEvent event) {
        changeFragment(event.getFragment(), event.isAddToBackstack());
    }


//    @Subscribe
//    public void onSongLoaded(ServiceContext.OnPlayableSongLoadedEvent event) {
////        Toast.makeText(mainActivity,
////                String.format("onSongLoaded: %s", event.getPlayableSong().getTitle()),
////                Toast.LENGTH_SHORT).show();
//
//        if (event.getPlayableSong() == null) {
//            Log.d(TAG, "SONG NOT FOUND: ");
//            Toast.makeText(this, "SONG NOT FOUND", Toast.LENGTH_SHORT).show();
//
//        } else {
//            Log.d(TAG, String.format("onSongLoaded: %s", event.getPlayableSong().getTitle()));
//            musicManager.play(event.getPlayableSong().getLink().getLink128());
//
//        }
//
//
//    }

    @Subscribe
    public void onChangingSongClicked(OnSongItemClickedEvent event) {

        musicManager.startGetPlayableSong(event);
//        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        }
//        hybrid.release();
        fabPause.setImageResource(R.drawable.ic_pause_white_36px);
        btsViewHolder.bindSong(event.getSongsDetail());
        playingSongDetail = event.getSongsDetail();
    }

    private void changeFragment(Fragment desFragment, boolean addToBackstack) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, desFragment);
        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    public Handler getmHandler() {
        return mHandler;
    }


    public static class DemandFragmentChangingEvent {
        private Fragment fragment;
        private boolean addToBackstack;

        public DemandFragmentChangingEvent(Fragment fragment, boolean addToBackstack) {
            this.fragment = fragment;
            this.addToBackstack = addToBackstack;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public boolean isAddToBackstack() {
            return addToBackstack;
        }
    }

    public static class OnChangingSongEvent {
        private SongsDetail songsDetail;

        public OnChangingSongEvent(SongsDetail songsDetail) {
            this.songsDetail = songsDetail;
        }

        public SongsDetail getSongsDetail() {
            return songsDetail;
        }
    }


}
