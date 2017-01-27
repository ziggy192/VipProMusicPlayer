package com.example.nghia.vippromusicplayer.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.nghia.vippromusicplayer.events.OnSongItemClickedEvent;
import com.example.nghia.vippromusicplayer.models.SongsDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import hybridmediaplayer.Hybrid;


/**
 * Created by Nghia on 1/17/2017.
 */

public class BackgroundMusicManager {
    private static final String TAG = BackgroundMusicManager.class.toString();
    private Hybrid hybrid;
    private boolean isPlaying;
    private static BackgroundMusicManager instance;
    private MusicController currentMusicController;
    public Hybrid getHybrid() {
        return hybrid;
    }

    private SongsDetail playingSong;
    private int songPosition;


    public BackgroundMusicManager() {
        playingSong = null;
        songPosition = -1;
        isPlaying = false;
    }

    public static BackgroundMusicManager getInstance() {
        return instance;
    }

    public static void init(Context context){
        instance = new BackgroundMusicManager();
        instance.hybrid = Hybrid.getInstance(context);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getDuration() {
        return hybrid.getDuration();
    }

    public int getCurrentPosition() {
        return hybrid.getCurrentPosition();
    }

    public void setCurrentMusicController(MusicController currentMusicController) {
        this.currentMusicController = currentMusicController;
        currentMusicController.updateUI();
    }

    public void play(OnSongItemClickedEvent clickedSong, String link128) {

        playingSong = clickedSong.getSongsDetail();
        songPosition = clickedSong.getSongPosition();
        hybrid.setDataSource(link128);
        hybrid.prepare();
        hybrid.setOnCompletionListener(new Hybrid.OnCompletionListener() {
            @Override
            public void onCompletion(Hybrid hybrid) {
                toNextSOng();
            }
        });
        hybrid.setOnPreparedListener(new Hybrid.OnPreparedListener() {
            @Override
            public void onPrepared(Hybrid hybrid) {
                hybrid.play();
                currentMusicController.updateUI();
                currentMusicController.updateProgress();
                isPlaying = true;

            }
        });
    }

    public void toNextSOng(){
        int nextPosition;
        SongsDetail nextSong;
        ArrayList<SongsDetail> songsDetailList =
                DBContext.getInstance().getSongDetailList(playingSong.getMusicGenreId());
        if (songsDetailList.size()-1>songPosition){
            nextPosition = songPosition +1;
        }else{
            nextPosition = 0;
        }
        nextSong = songsDetailList.get(nextPosition);
        EventBus.getDefault().post(new OnSongItemClickedEvent(nextSong,nextPosition));
    }

    public void toPreviousSong(){
        int previousPosition;
        SongsDetail previousSong;
        ArrayList<SongsDetail> songsDetailList =
                DBContext.getInstance().getSongDetailList(playingSong.getMusicGenreId());
        if (songPosition>0){
            previousPosition = songPosition -1;
        }else{
            previousPosition = songsDetailList.size()-1;
        }
        previousSong = songsDetailList.get(previousPosition);
        EventBus.getDefault().post(new OnSongItemClickedEvent(previousSong,previousPosition));
    }


    public void errorNoti(String errorMess){
        currentMusicController.errorNoti(errorMess);
    }

    public void changePlayingState() {
        if (!isPlaying) {
                play();
        } else {
                pause();
        }
    }

    public SongsDetail getPlayingSong() {
        return playingSong;
    }

    public void play() {
        hybrid.play();
        isPlaying = true;
        currentMusicController.updateProgress();
        Log.d(TAG, "start playing");
    }

    public void seekTo(int time){
        hybrid.seekTo(time);
        if (!isPlaying){
            pause();
        }
    }

    public void pause() {
        hybrid.pause();
        isPlaying = false;
        currentMusicController.stopUpdatingProgress();
        Log.d(TAG, "pause playing");
    }

    public void startGetPlayableSong(OnSongItemClickedEvent event) {
        ServiceContext.getInstance().startGetPlayableSong(event,this);
        currentMusicController.updateUI();
    }

    public MusicController getCurrentMusicController() {
        return currentMusicController;
    }


    public interface MusicController{
        void updateProgress();

        void stopUpdatingProgress();

        void updateUI();

        void errorNoti(String erroMEss);


    }
}
