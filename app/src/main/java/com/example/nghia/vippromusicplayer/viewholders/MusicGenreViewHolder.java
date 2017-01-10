package com.example.nghia.vippromusicplayer.viewholders;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.squareup.picasso.Picasso;

import javax.annotation.Resource;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nghia on 1/9/2017.
 */

public class MusicGenreViewHolder extends RecyclerView.ViewHolder {
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
        int imageResouce  = context.getResources().getIdentifier(musicGenre.getDrawableName(), "drawable", context.getPackageName());
        Log.d("ahihi", musicGenre.getDrawableName());
        Picasso.with(context).load(imageResouce).into(imageView);
    }
}
