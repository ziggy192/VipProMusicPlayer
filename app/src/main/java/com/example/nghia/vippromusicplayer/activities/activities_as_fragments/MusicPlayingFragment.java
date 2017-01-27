package com.example.nghia.vippromusicplayer.activities.activities_as_fragments;


import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.activities.MainActivity;
import com.example.nghia.vippromusicplayer.events.OnSongItemClickedEvent;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.example.nghia.vippromusicplayer.utils.BackgroundMusicManager;
import com.example.nghia.vippromusicplayer.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicPlayingFragment extends Fragment implements BackgroundMusicManager.MusicController {


    private static final String TAG = MusicPlayingFragment.class.toString() ;
    @BindView(R.id.imv_background)
    ImageView imvBackGround;
    @BindView(R.id.tv_song_title)
    TextView tvTitle;
    @BindView(R.id.tv_song_author)
    TextView tvAuthor;
    @BindView(R.id.tv_playing_time)
    TextView tvPlayingTime;
    @BindView(R.id.fab_pause)
    FloatingActionButton fabPause;
    @BindView(R.id.imv_next)
    ImageView imvNext;

    @BindView(R.id.seek_bar)
    AppCompatSeekBar seekBar;

    private SongsDetail songsDetail;
    private Handler mHandler = new Handler();
    private BackgroundMusicManager musicManager;

    private AppCompatActivity mainActivity;

    public MusicPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicManager = BackgroundMusicManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music_playing, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        BackgroundMusicManager.getInstance().setCurrentMusicController(this);
        addListeners();
        updateProgress();
        Log.d(TAG, "onStart: ");
        updateUI();
    }

    private void addListeners() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //remove message Handler from updating progress bar
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = musicManager.getDuration();
                int currentPosition = (int) Utils.progressToTimer(seekBar.getProgress(), totalDuration);
                // forward or backward to certain seconds
                musicManager.seekTo(currentPosition);
                // update timer progress again
//                    updateProgress();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(sticky = true)
    public void setupUI(MusicPlayingArgumentsBus args) {
        mainActivity = args.getMainActivity();
        bind(args.getSongsDetail());
    }

    @Subscribe(priority = 2)
    public void onSongChanged(OnSongItemClickedEvent event){
        bind(event.getSongsDetail());
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            int totalDuration = musicManager.getDuration();
            int currentDuration = musicManager.getCurrentPosition();

            // Displaying Total Duration time
//            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
//            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));
            int minute = currentDuration/1000/60;
            int seconds = currentDuration/1000%60;
            tvPlayingTime.setText(String.format("%02d:%02d", minute,seconds));
            // Updating progress bar
            int progress = (int) (Utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);

            seekBar.setProgress(progress);


            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    @OnClick(R.id.fab_pause)
    public void fabClicked() {
        musicManager.changePlayingState();
        if (musicManager.isPlaying()) {
            fabPause.setImageResource(R.drawable.ic_pause_white_36px);
        } else {
            fabPause.setImageResource(R.drawable.ic_play_arrow_white_24px);
        }
    }
    @OnClick(R.id.imv_icon_close)
    public void minimize() {
        //todo debugging
        ((MainActivity)mainActivity).minimizeMusicPlayer();
        mainActivity.onBackPressed();
    }

    @OnClick(R.id.imv_next)
    public void next(){
        musicManager.toNextSOng();
    }

    @OnClick(R.id.imv_back)
    public void back(){
        musicManager.toPreviousSong();
    }

    private void bind(SongsDetail songsDetail) {
        this.tvTitle.setText(songsDetail.getSongName());
        this.tvAuthor.setText(songsDetail.getSongArtist());
        Picasso.with(getContext()).load(songsDetail.getLargeImageUrl()).into(imvBackGround);
    }

    public static MusicPlayingFragment create(SongsDetail songsDetail, AppCompatActivity mainActivity) {
        MusicPlayingFragment fragment = new MusicPlayingFragment();
        EventBus.getDefault().postSticky(new MusicPlayingArgumentsBus(songsDetail, mainActivity));
        return fragment;
    }

    @Override
    public void updateProgress() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
        Log.d(TAG, "on updating progress");

    }

    @Override
    public void stopUpdatingProgress() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        Log.d(TAG, "stopUpdatingProgress: ");
    }

    @Override
    public void updateUI() {
        if (musicManager.isPlaying()) {
            fabPause.setImageResource(R.drawable.ic_pause_white_36px);
        } else {
            fabPause.setImageResource(R.drawable.ic_play_arrow_white_24px);
        }
    }

    @Override
    public void errorNoti(String erroMEss) {
        Toast.makeText(mainActivity, erroMEss, Toast.LENGTH_SHORT).show();
    }

    private static class MusicPlayingArgumentsBus {
        private SongsDetail songsDetail;
        private AppCompatActivity mainActivity;

        public MusicPlayingArgumentsBus(SongsDetail songsDetail, AppCompatActivity mainActivity) {
            this.songsDetail = songsDetail;
            this.mainActivity = mainActivity;
        }

        public SongsDetail getSongsDetail() {
            return songsDetail;
        }

        public AppCompatActivity getMainActivity() {
            return mainActivity;
        }
    }


}
