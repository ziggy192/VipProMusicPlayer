package com.example.nghia.vippromusicplayer.activities.activities_as_fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.activities.TestingNewBasicMainActivity;
import com.example.nghia.vippromusicplayer.adapters.SongsRecyclerViewAdapter;
import com.example.nghia.vippromusicplayer.events.OnSongItemClickedEvent;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.models.SongsDetail;
import com.example.nghia.vippromusicplayer.utils.DBContext;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import hybridmediaplayer.Hybrid;
import io.realm.Realm;

import static com.example.nghia.vippromusicplayer.activities.activities_as_fragments.NewMainActivityFragment.MUSIC_GENRE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGenreDetailFragment extends Fragment {

    private static final String TAG = NewGenreDetailFragment.class.toString();
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    AppCompatActivity mainActivity;


    SongsRecyclerViewAdapter adapter;


    public NewGenreDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadReferences();

        ServiceContext.getInstance().startGetGenreDetail(musicGenre.getId());
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mainActivity.getMenuInflater().inflate(R.menu.menu_genre_detail, menu);
        onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_change_favorite);
        if (item != null) {
            if (musicGenre.isFavorite()) {
                item.setIcon(R.drawable.ic_favorite_white_24px);
            } else {
                item.setIcon(R.drawable.ic_favorite_border_white_24px);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_genre_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        //todo debugging
        toolbar.setTitle("");
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                mainActivity.onBackPressed();
                return true;
            case (R.id.action_change_favorite):

                DBContext.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        musicGenre.changeFavorite();
                    }
                });
                mainActivity.invalidateOptionsMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Subscribe
    public void onSongItemClicked(OnSongItemClickedEvent event) {
        SongsDetail songsDetail = event.getSongsDetail();
        Log.d(TAG, String.format("onSongItemClicked: songDetail = %s", songsDetail.getSongName()));
        EventBus.getDefault().post(new TestingNewBasicMainActivity.OnChangingSongEvent(songsDetail));
        ServiceContext.getInstance().startGetPlayableSong(songsDetail.getNameAndArtist());
    }



    @Subscribe(sticky = true)
    public void updateUI(ServiceContext.OnSongsLoadedEvent event) {
        adapter.addSongsDetails(DBContext.getInstance().getSongDetailList(event.getMusicGenreId()));
        EventBus.getDefault().removeStickyEvent(event);
    }

    private void loadReferences() {

        String genreId = getArguments().getString(MUSIC_GENRE_KEY);
        musicGenre = DBContext.getInstance().getMusicGenre(genreId);
    }


    private void setupUI() {
        toolbarLayout.setTitle("");
        //setup favorite:
        tvPlaylistTitle.setText(musicGenre.getTranslationKey());
        int imageResource = getResources()
                .getIdentifier(musicGenre.getDrawableName(),
                        "drawable", mainActivity.getPackageName());

        Picasso.with(mainActivity).load(imageResource).into(imageView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false));
        adapter = new SongsRecyclerViewAdapter(recyclerView);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "done setting up UI");
    }
}
