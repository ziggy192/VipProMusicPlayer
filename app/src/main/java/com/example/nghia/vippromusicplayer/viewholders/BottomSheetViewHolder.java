package com.example.nghia.vippromusicplayer.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nghia on 1/17/2017.
 */

public class BottomSheetViewHolder {

    @BindView(R.id.imv_item_song_thumbail)
    ImageView imvThumbail;
    @BindView(R.id.tv_item_song_title)
    TextView tvTitle;
    @BindView(R.id.tv_item_song_author)
    TextView tvAuthor;
    private Context context;

    public BottomSheetViewHolder(View itemView) {
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    public void bindSong(SongsDetail songsDetail) {
        tvTitle.setText(songsDetail.getSongName());
        tvAuthor.setText(songsDetail.getSongArtist());
        Picasso.with(context).load(songsDetail.getSmallImageUrl()).into(imvThumbail);
    }

}
