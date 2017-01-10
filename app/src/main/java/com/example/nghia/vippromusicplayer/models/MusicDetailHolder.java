package com.example.nghia.vippromusicplayer.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Nghia on 1/10/2017.
 */

public class MusicDetailHolder {
    @SerializedName("feed")
    private SmallMusicDetailHolder smallMusicDetailHolder;

    public MusicDetailHolder(SmallMusicDetailHolder smallMusicDetailHolder) {
        this.smallMusicDetailHolder = smallMusicDetailHolder;
    }

    public ArrayList<SongsDetail> getSongsDetails() {
        return smallMusicDetailHolder.getSongsDetails();
    }

    @Override
    public String toString() {
        return smallMusicDetailHolder.toString();
    }

    public class SmallMusicDetailHolder {
        @SerializedName("entry")
        private ArrayList<SongsDetail> songsDetails;

        public SmallMusicDetailHolder(ArrayList<SongsDetail> songsDetails) {
            this.songsDetails = songsDetails;
        }

        public ArrayList<SongsDetail> getSongsDetails() {
            return songsDetails;
        }

        @Override
        public String toString() {
            return "SmallMusicDetailHolder{" +
                    "songsDetails=" + songsDetails +
                    '}';
        }
    }




}
