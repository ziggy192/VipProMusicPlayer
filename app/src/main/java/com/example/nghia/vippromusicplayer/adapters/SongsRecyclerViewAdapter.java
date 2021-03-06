package com.example.nghia.vippromusicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.events.OnMusicGenreItemClickedEvent;
import com.example.nghia.vippromusicplayer.events.OnSongItemClickedEvent;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nghia on 1/10/2017.
 */

public class SongsRecyclerViewAdapter extends RecyclerView.Adapter<SongsRecyclerViewAdapter.SongViewHolder> {

    private List<SongsDetail> songsDetails;
    private RecyclerView mRecyclerView;

    public SongsRecyclerViewAdapter(List<SongsDetail> songsDetails, RecyclerView recyclerView) {
        this.songsDetails = songsDetails;
        mRecyclerView = recyclerView;
    }

    public SongsRecyclerViewAdapter(RecyclerView recyclerView) {
        this.songsDetails = new ArrayList<>();
        mRecyclerView = recyclerView;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_song, parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = mRecyclerView.getChildAdapterPosition(v);
                SongsDetail songsDetail = songsDetails.get(itemPosition);
                EventBus.getDefault().post(new OnSongItemClickedEvent(songsDetail,itemPosition));
            }
        });
        SongViewHolder holder = new SongViewHolder(itemView);
        return holder;
    }

    public void addSongsDetails(List<SongsDetail> songsDetails) {
        int beginningSize = this.songsDetails.size();
        this.songsDetails.addAll(songsDetails);
//        this.notifyDataSetChanged();
        this.notifyItemRangeInserted(beginningSize,songsDetails.size());
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.bind(songsDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return songsDetails.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_song_title)
        TextView tvTitle;
        @BindView(R.id.tv_item_song_author)
        TextView tvAuthor;
        @BindView(R.id.imv_item_song_thumbail)
        ImageView imvThumbail;

        Context context;

        public SongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
        }

        public void bind(SongsDetail songsDetail) {
            tvTitle.setText(songsDetail.getSongName());
            tvAuthor.setText(songsDetail.getSongArtist());
            Picasso.with(context).load(songsDetail.getSmallImageUrl())
                    .into(imvThumbail);
        }
    }
}
