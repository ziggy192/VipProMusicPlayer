package com.example.nghia.vippromusicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.events.OnMusicGenreItemClickedEvent;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nghia on 1/9/2017.
 */

public class MusicGenreRecyclerViewAdapter extends RecyclerView.Adapter<MusicGenreRecyclerViewAdapter.MusicGenreViewHolder> {

    private List<MusicGenre> musicGenreArrayList;
    private RecyclerView mRecyclerView;

    public MusicGenreRecyclerViewAdapter(List<MusicGenre> musicGenreArrayList, RecyclerView recyclerView) {
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


    class MusicGenreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_item_music_genre)
        ImageView imageView;
        @BindView(R.id.tv_item_music_genre)
        TextView textView;

        Context context;

        public MusicGenreViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(MusicGenre musicGenre) {
            textView.setText(musicGenre.getTranslationKey());
            int imageResouce = context.getResources().getIdentifier(musicGenre.getDrawableName(), "drawable", context.getPackageName());
            Picasso.with(context).load(imageResouce).into(imageView);
        }
    }
}
