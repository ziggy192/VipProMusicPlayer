package com.example.nghia.vippromusicplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.adapters.FavoriteGenreRecyclerViewAdapter;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.utils.DBContext;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteListFragment extends Fragment {

    @BindView(R.id.rv_music_genres)
    RecyclerView recyclerView;
    FavoriteGenreRecyclerViewAdapter adapter;

    public FavoriteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_favorite_genre, container, false);
        ButterKnife.bind(this,rootView);
        setupUI();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {
        List<MusicGenre> list = DBContext.getInstance().getFavoriteList();
        adapter.updateData(list);
    }

    private void setupUI() {
        List<MusicGenre> list = DBContext.getInstance().getFavoriteList();
        adapter = new FavoriteGenreRecyclerViewAdapter(list, recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public String toString() {
        return "Playlist";
    }
}
