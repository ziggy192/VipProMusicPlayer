package com.example.nghia.vippromusicplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.adapters.MusicGenreRecyclerViewAdapter;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicGenresListFragment extends Fragment {

    private static final String TAG = MusicGenresListFragment.class.toString();
    @BindView(R.id.rv_music_genres)
    RecyclerView recyclerView;

    ArrayList<MusicGenre> musicGenres;



    public MusicGenresListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music_genres_list, container, false);
        ButterKnife.bind(this, rootView);
//        setupUI();
        return rootView;
    }

    @Subscribe(sticky = true)
    public void setupUI(ServiceContext.OnMusicGenresLoadedEvent event) {
        Log.d(TAG, "on setting up fragments");
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MusicGenreRecyclerViewAdapter(event.getMusicGenres(),recyclerView));

    }

    @Override
    public String toString() {
        return "Genres";
    }
}
