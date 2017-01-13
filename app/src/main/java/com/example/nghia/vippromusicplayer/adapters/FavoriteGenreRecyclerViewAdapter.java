package com.example.nghia.vippromusicplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.events.OnMusicGenreItemClickedEvent;
import com.example.nghia.vippromusicplayer.models.MusicGenre;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nghia on 1/13/2017.
 */

public class FavoriteGenreRecyclerViewAdapter extends RecyclerView.Adapter
        <FavoriteGenreRecyclerViewAdapter.FavoriteGenreViewHolder> {

    private List<MusicGenre> musicGenreList;
    private RecyclerView mRecyclerView;

    public FavoriteGenreRecyclerViewAdapter(List<MusicGenre> musicGenreList,RecyclerView mRecyclerView) {
        this.musicGenreList = musicGenreList;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public FavoriteGenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_favorite_genre, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = mRecyclerView.getChildAdapterPosition(v);
                MusicGenre genre = musicGenreList.get(itemPosition);
                EventBus.getDefault().post(new OnMusicGenreItemClickedEvent(genre));
            }
        });
        FavoriteGenreViewHolder viewHolder = new FavoriteGenreViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteGenreViewHolder holder, int position) {
        holder.bind(musicGenreList.get(position));
    }

    public void updateData(List<MusicGenre> musicGenres) {
        this.musicGenreList.clear();
        this.musicGenreList.addAll(musicGenres);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return musicGenreList.size();
    }

    class FavoriteGenreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_playlist_title)
        TextView tvTitle;
        public FavoriteGenreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(MusicGenre musicGenre){
            tvTitle.setText(musicGenre.getTranslationKey());
        }
    }
}

