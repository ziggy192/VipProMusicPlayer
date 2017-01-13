package com.example.nghia.vippromusicplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.adapters.SongsRecyclerViewAdapter;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.utils.DBContext;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class GenreDetailActivity extends AppCompatActivity {

    private static final String TAG = GenreDetailActivity.class.toString();
    @BindView(R.id.detail_image_view)
    ImageView imageView;
    MusicGenre musicGenre;
    @BindView(R.id.rv_detail_songlist)
    RecyclerView recyclerView;
    @BindView(R.id.tv_playlist_title)
    TextView tvPlaylistTitle;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.tv_playlist_caption)
    TextView tvPlaylistCaption;

    Menu menu;

    SongsRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);
        ButterKnife.bind(this);
        loadReferences();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_genre_detail,menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_change_favorite);
        if (item != null) {
            if (musicGenre.isFavorite()) {
                item.setIcon(R.drawable.ic_favorite_white_24px);
            } else {
                item.setIcon(R.drawable.ic_favorite_border_white_24px);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                onBackPressed();
                return true;
            case (R.id.action_change_favorite):

                DBContext.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        musicGenre.changeFavorite();
                    }
                });
//                DBContext.getInstance().updateMusicGenre(musicGenre);
                invalidateOptionsMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(sticky = true)
    public void updateUI(ServiceContext.OnSongsLoadedEvent event){
        adapter.addSongsDetails(DBContext.getInstance().getSongDetailList(event.getSongsId()));
        EventBus.getDefault().removeStickyEvent(event);
    }

    private void loadReferences() {
        Intent intent = getIntent();
        String genreId = intent.getStringExtra(MainActivity.MUSIC_GENRE_KEY);
        musicGenre = DBContext.getInstance().getMusicGenre(genreId);
    }


    private void setupUI() {
        toolbarLayout.setTitle("");
        //setup favorite:
        tvPlaylistTitle.setText(musicGenre.getTranslationKey());
        int imageResource  = getResources().getIdentifier(musicGenre.getDrawableName(), "drawable", getPackageName());

        Picasso.with(this).load(imageResource).into(imageView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new SongsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "done setting up UI");
    }
}
