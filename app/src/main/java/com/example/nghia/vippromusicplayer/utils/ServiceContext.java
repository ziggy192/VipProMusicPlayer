package com.example.nghia.vippromusicplayer.utils;

import android.util.Log;


import com.example.nghia.vippromusicplayer.models.MediaTypeHolder;
import com.example.nghia.vippromusicplayer.models.MusicDetailHolder;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.models.PlayableSong;
import com.example.nghia.vippromusicplayer.models.PlayableSongHolder;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.example.nghia.vippromusicplayer.services.MyRetrofitService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nghia on 12/24/2016.
 */

public class ServiceContext {
//    final String HEADER_TOKEN_KEY = "token";
    private static final String TAG = ServiceContext.class.toString();
    private Retrofit genresRetrofit;
    private Retrofit detailRetrofit;
    private Retrofit mp3Retrofit;
    private ServiceContext(String genreUrl,String detailUrl,String downloadUrl) {
        genresRetrofit = new Retrofit.Builder()
                .baseUrl(genreUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        detailRetrofit = new Retrofit.Builder()
                .baseUrl(detailUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mp3Retrofit = new Retrofit.Builder()
                .baseUrl(downloadUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private static  ServiceContext instance ;
    public static void init(String genreUrl,String detailUrl,String downloadUrl){
        instance = new ServiceContext(genreUrl,detailUrl,downloadUrl);
    }

    private Retrofit getGenresRetrofit() {
        return genresRetrofit;
    }

    private Retrofit getDetailRetrofit() {
        return detailRetrofit;
    }

    public Retrofit getMp3Retrofit() {
        return mp3Retrofit;
    }

    public static ServiceContext getInstance() {
        return instance;
    }

    public void getTypes(){
        MyRetrofitService service = getGenresRetrofit().create(MyRetrofitService.class);
        Call<MediaTypeHolder[]> call=  service.getMusicTypes();
        call.enqueue(new Callback<MediaTypeHolder[]>() {
            @Override
            public void onResponse(Call<MediaTypeHolder[]> call, Response<MediaTypeHolder[]> response) {
                MediaTypeHolder[] holders = response.body();
                for (MediaTypeHolder holder : holders){
                    if (holder.getId().equals("34")){
                        Log.d(TAG, String.format("onResponse: genres = %s", holder.getGenres()));
//                        listener.onResponse(holder.getGenres());
                        DBContext.getInstance().putMusicGenreList(holder.getGenres());
                        EventBus.getDefault().post(new OnMusicGenresLoadedEvent());
                        break;
                    }
                }

            }

            @Override
            public void onFailure(Call<MediaTypeHolder[]> call, Throwable t) {
                Log.d(TAG, "onFailure");
                EventBus.getDefault().post(new OnMusicGenresLoadedEvent());
            }
        });
    }

    public void startGetGenreDetail(final String id){
        MyRetrofitService service = getDetailRetrofit().create(MyRetrofitService.class);
        Call<MusicDetailHolder> call = service.getMusicDetail(id);
        call.enqueue(new Callback<MusicDetailHolder>() {
            @Override
            public void onResponse(Call<MusicDetailHolder> call, Response<MusicDetailHolder> response) {
                MusicDetailHolder musicDetailHolder = response.body();
                Log.d(TAG, String.format("on Detail response: %s", musicDetailHolder));
                List<SongsDetail> songsDetails = musicDetailHolder.getSongsDetails();
                for (SongsDetail songsDetail : songsDetails) {
                    songsDetail.setMusicGenreId(id);
                }

                DBContext.getInstance().deleteAllFromResult(DBContext.getInstance().getSongsDetailResult(id));
                DBContext.getInstance().putSongDetailList(songsDetails);

                EventBus.getDefault().postSticky(new OnSongsLoadedEvent(id));
            }

            @Override
            public void onFailure(Call<MusicDetailHolder> call, Throwable t) {
                EventBus.getDefault().postSticky(new OnSongsLoadedEvent(id));
            }
        });
    }

    public void startGetPlayableSong(final String key) {
        String paras = "{\"q\":\""+key+"\", \"sort\":\"hot\", \"start\":\"0\", \"length\":\"30\"}";
        MyRetrofitService service = getMp3Retrofit().create(MyRetrofitService.class);
        Call<PlayableSongHolder> call = service.getPlayableSongs(paras);
        call.enqueue(new Callback<PlayableSongHolder>() {
            @Override
            public void onResponse(Call<PlayableSongHolder> call, Response<PlayableSongHolder> response) {
                PlayableSong song = response.body().getFirstSong();
                EventBus.getDefault().post(new OnPlayableSongLoadedEvent(song));
                Log.d(TAG, String.format("startGetPlayableSong: song =%s", song.getTitle()));
            }

            @Override
            public void onFailure(Call<PlayableSongHolder> call, Throwable t) {

            }
        });
    }



    public class OnAdapterDataSetChangeEvent{

    }
    public class OnMusicGenresLoadedEvent{

        public OnMusicGenresLoadedEvent() {
        }
    }

    public class OnPlayableSongLoadedEvent{
        PlayableSong playableSong;

        public OnPlayableSongLoadedEvent(PlayableSong playableSong) {
            this.playableSong = playableSong;
        }

        public PlayableSong getPlayableSong() {
            return playableSong;
        }
    }

    public class OnSongsLoadedEvent{
        String musicGenreId;

        public OnSongsLoadedEvent(String musicGenreId) {
            this.musicGenreId = musicGenreId;
        }

        public String getMusicGenreId() {
            return musicGenreId;
        }
    }

    public interface ServiceMusicGenresReceivedListener {
         void onResponse(ArrayList<MusicGenre> musicGenres);
    }




}
