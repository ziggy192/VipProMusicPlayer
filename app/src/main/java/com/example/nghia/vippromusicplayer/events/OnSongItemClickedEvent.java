package com.example.nghia.vippromusicplayer.events;

import com.example.nghia.vippromusicplayer.models.SongsDetail;

/**
 * Created by Nghia on 1/15/2017.
 */

public class OnSongItemClickedEvent {
    private SongsDetail songsDetail;

    public OnSongItemClickedEvent(SongsDetail songsDetail) {
        this.songsDetail = songsDetail;
    }

    public SongsDetail getSongsDetail() {
        return songsDetail;
    }
}
