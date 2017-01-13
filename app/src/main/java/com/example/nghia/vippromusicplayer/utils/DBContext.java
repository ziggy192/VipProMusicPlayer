package com.example.nghia.vippromusicplayer.utils;

import android.content.Context;

import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.models.SongsDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Nghia on 1/12/2017.
 */

public class DBContext {
    private Realm realm;
    private static DBContext instance;

    private DBContext() {
        realm = Realm.getDefaultInstance();
    }

    public static DBContext getInstance() {
        return instance;
    }

    public void putMusicGenreList(RealmList<MusicGenre> musicGenres) {
        realm.beginTransaction();
        for (MusicGenre genre : musicGenres) {
            MusicGenre oldGenre = getMusicGenre(genre.getId());
            if (oldGenre != null) {
                genre.setFavorite(oldGenre.isFavorite());
            }
        }
        realm.copyToRealmOrUpdate(musicGenres);
        realm.commitTransaction();
    }

    public void putMusicGenreList(List<MusicGenre> musicGenres) {
        RealmList<MusicGenre> musicGenreRealmList = new RealmList<>();
        musicGenreRealmList.addAll(musicGenres);
        this.putMusicGenreList(musicGenreRealmList);
    }

    public void updateMusicGenre(MusicGenre musicGenre) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(musicGenre);
        realm.commitTransaction();
    }


    public Realm getRealm() {
        return realm;
    }

    public ArrayList<MusicGenre> getMusicGenreList() {

        RealmResults<MusicGenre> realmResults = realm.where(MusicGenre.class).findAll();
        ArrayList<MusicGenre> musicGenres = new ArrayList<>();
        musicGenres.addAll(realmResults);
        return musicGenres;
    }

    public ArrayList<MusicGenre> getFavoriteList() {
        RealmResults<MusicGenre> realmResults = realm.where(MusicGenre.class)
                .equalTo("isFavorite", true).findAll();
        ArrayList<MusicGenre> musicGenres = new ArrayList<>();
        musicGenres.addAll(realmResults);
        return musicGenres;

    }

    public void putSongDetailList(RealmList<SongsDetail> songsDetails) {
        realm.beginTransaction();
        realm.copyToRealm(songsDetails);
        realm.commitTransaction();
    }

    public void putSongDetailList(List<SongsDetail> songsDetails) {
        RealmList<SongsDetail> realmList = new RealmList<>();
        realmList.addAll(songsDetails);
        putSongDetailList(realmList);
    }

    public MusicGenre getMusicGenre(String genreId) {
        return realm.where(MusicGenre.class).equalTo("id", genreId).findFirst();
    }

    public ArrayList<SongsDetail> getSongDetailList(String genreId) {
        RealmResults<SongsDetail> songsDetails = realm.where(SongsDetail.class).equalTo("musicGenreId", genreId).findAll();
        ArrayList<SongsDetail> detailArrayList = new ArrayList<>();
        detailArrayList.addAll(songsDetails);
        return detailArrayList;
    }

    public RealmResults<SongsDetail> getSongsDetailResult(String genreId) {
        return realm.where(SongsDetail.class).equalTo("musicGenreId", genreId).findAll();
    }

    public void deleteAllFromResult(RealmResults<?> result) {
        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void init(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        instance = new DBContext();

    }


}
