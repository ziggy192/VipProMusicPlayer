package com.example.nghia.vippromusicplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_image_view)
    ImageView imageView;
    MusicGenre musicGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);
        ButterKnife.bind(this);
        loadReferences();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupUI();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void loadReferences() {
        Intent intent = getIntent();
        musicGenre = (MusicGenre) intent.getSerializableExtra(MainActivity.MUSIC_GENRE_KEY);
    }

    private void setupUI() {
        int imageResouce  = getResources().getIdentifier(musicGenre.getDrawableName(), "drawable", getPackageName());
        Picasso.with(this).load(imageResouce).into(imageView);
    }
}
