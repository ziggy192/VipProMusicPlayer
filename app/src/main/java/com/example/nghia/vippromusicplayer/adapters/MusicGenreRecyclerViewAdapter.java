package com.example.nghia.vippromusicplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.viewholders.MusicGenreViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Nghia on 1/9/2017.
 */

public class MusicGenreRecyclerViewAdapter extends RecyclerView.Adapter<MusicGenreViewHolder> {

    private ArrayList<MusicGenre> musicGenreArrayList;
    private RecyclerView mRecyclerView;
    public MusicGenreRecyclerViewAdapter(ArrayList<MusicGenre> musicGenreArrayList,RecyclerView recyclerView) {
        this.musicGenreArrayList = musicGenreArrayList;
        mRecyclerView = recyclerView;
    }

    @Override
    public MusicGenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_music_genre, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = mRecyclerView.getChildAdapterPosition(v);
                MusicGenre genre = musicGenreArrayList.get(itemPosition);
                EventBus.getDefault().post(new OnMusicGenreItemClickedEvent(genre));
            }
        });

        MusicGenreViewHolder holder = new MusicGenreViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(MusicGenreViewHolder holder, int position) {
        holder.bind(musicGenreArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicGenreArrayList.size();
    }

    public class OnMusicGenreItemClickedEvent{
        MusicGenre musicGenre;

        public OnMusicGenreItemClickedEvent(MusicGenre musicGenre) {
            this.musicGenre = musicGenre;
        }

        public MusicGenre getMusicGenre() {
            return musicGenre;
        }
    }
}
