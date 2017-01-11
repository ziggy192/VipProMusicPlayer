package com.example.nghia.vippromusicplayer.utils;

import android.util.Log;


import com.example.nghia.vippromusicplayer.models.MediaTypeHolder;
import com.example.nghia.vippromusicplayer.models.MusicDetailHolder;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.example.nghia.vippromusicplayer.services.MyRetrofitService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

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
    private ServiceContext(String genreUrl,String detailUrl) {
        genresRetrofit = new Retrofit.Builder()
                .baseUrl(genreUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        detailRetrofit = new Retrofit.Builder()
                .baseUrl(detailUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static  ServiceContext instance ;
    public static void init(String genreUrl,String detailUrl){
        instance = new ServiceContext(genreUrl,detailUrl);
    }

    private Retrofit getGenresRetrofit() {
        return genresRetrofit;
    }

    private Retrofit getDetailRetrofit() {
        return detailRetrofit;
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
                        EventBus.getDefault().postSticky(new OnMusicGenresLoadedEvent(holder.getGenres()));
                        break;
                    }
                }

            }

            @Override
            public void onFailure(Call<MediaTypeHolder[]> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    public void startGetGenreDetail(String id){
        MyRetrofitService service = getDetailRetrofit().create(MyRetrofitService.class);
        Call<MusicDetailHolder> call = service.getMusicDetail(id);
        call.enqueue(new Callback<MusicDetailHolder>() {
            @Override
            public void onResponse(Call<MusicDetailHolder> call, Response<MusicDetailHolder> response) {
                MusicDetailHolder musicDetailHolder = response.body();
                Log.d(TAG, String.format("on Detail response: %s", musicDetailHolder));
                EventBus.getDefault().postSticky(new OnSongsLoadedEvent(musicDetailHolder.getSongsDetails()));
            }

            @Override
            public void onFailure(Call<MusicDetailHolder> call, Throwable t) {

            }
        });
    }

//    public void getTodos(String token) {
//
//        MyRetrofitService service = getGenresRetrofit().create(MyRetrofitService.class);
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put(HEADER_TOKEN_KEY, token);
//        Call<ArrayList<TodoModel>> call=  service.getUser(headers);
//        call.enqueue(new Callback<ArrayList<TodoModel>>() {
//            @Override
//            public void onResponse(Call<ArrayList<TodoModel>> call, Response<ArrayList<TodoModel>> response) {
//                ArrayList<TodoModel> todoModels = response.body();
////                Log.d(TAG, String.format("onResponse: todoModels = %s", todoModels));
//                OnGetTodoListEvent event = new OnGetTodoListEvent(todoModels);
//                EventBus.getDefault().post(event);
////                updateUI(todoModels);
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<TodoModel>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void editTodo(String token, final TodoModel editedTodoModel, final ServiceTodoReceivedListener listener) {
//        MyRetrofitService service = getGenresRetrofit().create(MyRetrofitService.class);
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put(HEADER_TOKEN_KEY, token);
////        headers.put("Content-Type", "application/x-www-form-urlencoded");
//        Call<TodoModel> call = service.editTodo(editedTodoModel
//                , editedTodoModel.getTodoId().getId(), headers);
//        call.enqueue(new Callback<TodoModel>() {
//            @Override
//            public void onResponse(Call<TodoModel> call, Response<TodoModel> response) {
//                Log.d(TAG, "onResponse: Edited success");
////                EventBus.getDefault().post(new OnReceiveNewTodoEvent(response.body()));
////                EventBus.getDefault().post(new OnAdapterDataSetChangeEvent());
//                listener.onResponse(response.body());
//            }
//
//
//            @Override
//            public void onFailure(Call<TodoModel> call, Throwable t) {
//
//            }
//        });
//    }
//    public void createTodo(String token, TodoModel todoModel, final ServiceTodoReceivedListener listener) {
//
//        MyRetrofitService service = getGenresRetrofit().create(MyRetrofitService.class);
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put(HEADER_TOKEN_KEY, token);
//
////        headers.put("Content-Type", "application/x-www-form-urlencoded");
//        Call<TodoModel[]> call=  service.createTodo(todoModel,headers);
//        call.enqueue(new Callback<TodoModel[]>() {
//            @Override
//            public void onResponse(Call<TodoModel[]> call, Response<TodoModel[]> response) {
//                Log.d(TAG, "onResponse: success");
////                EventBus.getDefault().post(new OnReceiveNewTodoEvent(response.body()[0]));
//                listener.onResponse(response.body()[0]);
//            }
//
//            @Override
//            public void onFailure(Call<TodoModel[]> call, Throwable t) {
//
//            }
//        });
//    }


    public class OnAdapterDataSetChangeEvent{

    }
    public class OnMusicGenresLoadedEvent{
        ArrayList<MusicGenre> musicGenres;

        public OnMusicGenresLoadedEvent(ArrayList<MusicGenre> musicGenres) {
            this.musicGenres = musicGenres;
        }

        public ArrayList<MusicGenre> getMusicGenres() {
            return musicGenres;
        }
    }

    public class OnSongsLoadedEvent{
        ArrayList<SongsDetail> songsDetails;

        public OnSongsLoadedEvent(ArrayList<SongsDetail> songsDetails) {
            this.songsDetails = songsDetails;
        }

        public ArrayList<SongsDetail> getSongsDetails() {
            return songsDetails;
        }
    }

    public interface ServiceMusicGenresReceivedListener {
         void onResponse(ArrayList<MusicGenre> musicGenres);
    }




}
