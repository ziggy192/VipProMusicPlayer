package com.example.nghia.vippromusicplayer.events;

import com.example.nghia.vippromusicplayer.models.SongsDetail;

/**
 * Created by Nghia on 1/15/2017.
 */

public class OnSongItemClickedEvent {
    private SongsDetail songsDetail;
    private int songPosition;

    public OnSongItemClickedEvent(SongsDetail songsDetail, int songPosition) {
        this.songsDetail = songsDetail;
        this.songPosition = songPosition;
    }

    public int getSongPosition() {
        return songPosition;
    }


    public SongsDetail getSongsDetail() {
        return songsDetail;
    }
}
