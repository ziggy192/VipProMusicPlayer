package com.example.nghia.vippromusicplayer.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.activities.activities_as_fragments.NewMainActivityFragment;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;
import com.example.nghia.vippromusicplayer.utils.Utils;
import com.example.nghia.vippromusicplayer.viewholders.BottomSheetViewHolder;
import com.google.android.exoplayer2.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hybridmediaplayer.Hybrid;

public class TestingNewBasicMainActivity extends AppCompatActivity {

    private static final String TAG = TestingNewBasicMainActivity.class.toString();
    @BindView(R.id.imv_item_song_thumbail)
    ImageView imvBottomSongThumbail;
    @BindView(R.id.fab_pause)
    FloatingActionButton fabPause;

    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;

    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetViewHolder btsViewHolder;
    private Handler mHandler = new Handler();
    private Hybrid hybrid;

    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_new_basic_main);
        ButterKnife.bind(this);
        setupUI();
        changeFragment(new NewMainActivityFragment(), false);

    }

    private void setupUI() {
        hybrid = Hybrid.getInstance(this);

        RelativeLayout rlBottomSheet = (RelativeLayout) findViewById(R.id.bts_music_player);

        this.btsViewHolder = new BottomSheetViewHolder(rlBottomSheet);

        this.bottomSheetBehavior = BottomSheetBehavior.from(rlBottomSheet);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        imvBottomSongThumbail.startAnimation(rotateAnimation);

    }

    @OnClick(R.id.fab_pause)
    public void fabClicked() {
        if (!isPlaying) {
            if (hybrid != null) {
                isPlaying = true;
                hybrid.play();
                updateProgressBar();
            }
            fabPause.setImageResource(R.drawable.ic_pause_white_36px);
        } else {
            if (hybrid != null) {
                hybrid.pause();
                mHandler.removeCallbacks(mUpdateTimeTask);

                isPlaying = false;
            }
            fabPause.setImageResource(R.drawable.ic_play_arrow_white_24px);
        }
    }

    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            int totalDuration = hybrid.getDuration();
            int currentDuration = hybrid.getCurrentPosition();

            // Displaying Total Duration time
//            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
//            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(Utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            progressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     *
     * */
//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
//
//    }
//
//    /**
//     * When user starts moving the progress handler
//     * */
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//        // remove message Handler from updating progress bar
//        mHandler.removeCallbacks(mUpdateTimeTask);
//    }

    /**
     * When user stops moving the progress hanlder
     * */
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//        mHandler.removeCallbacks(mUpdateTimeTask);
//        int totalDuration = mp.getDuration();
//        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
//
//        // forward or backward to certain seconds
//        mp.seekTo(currentPosition);
//
//        // update timer progress again
//        updateProgressBar();
//    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
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

    @Subscribe
    public void onSongLoaded(ServiceContext.OnPlayableSongLoadedEvent event) {
//        Toast.makeText(mainActivity,
//                String.format("onSongLoaded: %s", event.getPlayableSong().getTitle()),
//                Toast.LENGTH_SHORT).show();
        Log.d(TAG, String.format("onSongLoaded: %s", event.getPlayableSong().getTitle()));


        hybrid.setDataSource(event.getPlayableSong().getLink().getLink128());
        hybrid.prepare();
        hybrid.setOnPreparedListener(new Hybrid.OnPreparedListener() {
            @Override
            public void onPrepared(Hybrid hybrid) {
                hybrid.play();
                updateProgressBar();
                isPlaying = true;
                Toast.makeText(TestingNewBasicMainActivity.this, "prepared", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Subscribe
    public void onChangingSong(OnChangingSongEvent event) {
        isPlaying = true;
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
//        hybrid.release();
        btsViewHolder.bindSong(event.getSongsDetail());
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
