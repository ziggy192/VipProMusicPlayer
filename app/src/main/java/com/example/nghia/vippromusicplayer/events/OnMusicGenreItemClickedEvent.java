package com.example.nghia.vippromusicplayer.events;

import com.example.nghia.vippromusicplayer.models.MusicGenre;

/**
 * Created by Nghia on 1/13/2017.
 */

public class OnMusicGenreItemClickedEvent {
    MusicGenre musicGenre;

    public OnMusicGenreItemClickedEvent(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
    }

    public MusicGenre getMusicGenre() {
        return musicGenre;
    }
}