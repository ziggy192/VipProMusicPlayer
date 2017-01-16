package com.example.nghia.vippromusicplayer.services;


import com.example.nghia.vippromusicplayer.models.MediaTypeHolder;
import com.example.nghia.vippromusicplayer.models.MusicDetailHolder;
import com.example.nghia.vippromusicplayer.models.PlayableSongHolder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nghia on 12/10/2016.
 */

public interface MyRetrofitService {
    @GET("media-types.json")
    Call<MediaTypeHolder[]> getMusicTypes();

    @GET("us/rss/topsongs/limit=50/genre={id}/explicit=true/json")
    Call<MusicDetailHolder> getMusicDetail(@Path("id") String id);

    @GET("search/song")
    Call<PlayableSongHolder> getPlayableSongs(@Query("requestdata") String paras);


//    @POST("login")
//    Call<LoginResult> startLogin(@Body Account account);
//
//    @POST("register")
//    Call<RegisterResult> startRegister(@Body Account account);
//
//    @GET("todos")
//    Call<ArrayList<TodoModel>> getUser(@HeaderMap Map<String, String> headers);
//
//    @POST("todos")
//    Call<TodoModel[]> createTodo(@Body TodoModel todoModel, @HeaderMap Map<String, String> headers);
//
//    @PUT("todos/{todo_id}")
//    Call<TodoModel> editTodo(@Body TodoModel editedTodoModel, @Path("todo_id") String todoId
//            , @HeaderMap Map<String, String> headers);


}
