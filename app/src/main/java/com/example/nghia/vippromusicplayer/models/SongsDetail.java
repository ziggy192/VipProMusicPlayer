package com.example.nghia.vippromusicplayer.models;

import com.example.nghia.vippromusicplayer.models.inner_models.SongArtist;
import com.example.nghia.vippromusicplayer.models.inner_models.SongImage;
import com.example.nghia.vippromusicplayer.models.inner_models.SongName;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Nghia on 1/10/2017.
 */

public class SongsDetail extends RealmObject {
    @SerializedName("im:name")
    private SongName songName;
    @SerializedName("im:image")
    private RealmList<SongImage>  songImages;
    @SerializedName("im:artist")
    private SongArtist songArtist;

    private String musicGenreId;

    public void setMusicGenreId(String musicGenreId) {
        this.musicGenreId = musicGenreId;
    }

    public SongsDetail() {
    }

    public SongsDetail(SongName songName, RealmList<SongImage>  songImages, SongArtist songArtist) {
        this.songName = songName;
        this.songImages = songImages;
        this.songArtist = songArtist;
    }

    public String getSongName() {
        return songName.getName();
    }

    public RealmList<SongImage>  getSongImages() {
        return songImages;
    }


    public String getSongArtist() {
        return songArtist.getName();
    }

    public String getSmallImageUrl(){
        String url;
        for (SongImage image : songImages) {
            if (image.isSmall()) {
                return image.getUrl();
            }
        }
        return null;
    }

    public String getLargeImageUrl(){
        String url;
        for (SongImage image : songImages) {
            if (image.isLarge()) {
                return image.getUrl();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SongsDetail{" +
                "songName=" + songName +
                ", songImages=" + songImages +
                ", songArtist=" + songArtist +
                '}';
    }

}
