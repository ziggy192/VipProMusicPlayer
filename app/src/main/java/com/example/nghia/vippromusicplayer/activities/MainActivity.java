package com.example.nghia.vippromusicplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.adapters.MusicFragmentPagerAdapter;
import com.example.nghia.vippromusicplayer.adapters.MusicGenreRecyclerViewAdapter;
import com.example.nghia.vippromusicplayer.events.OnMusicGenreItemClickedEvent;
import com.example.nghia.vippromusicplayer.fragments.MusicGenresListFragment;
import com.example.nghia.vippromusicplayer.fragments.OfflineFragment;
import com.example.nghia.vippromusicplayer.fragments.FavoriteListFragment;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = MainActivity.class.toString();
    public static final String MUSIC_GENRE_KEY = "music_genre";
    @BindView(R.id.main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        setSupportActionBar(toolbar);
        ServiceContext.getInstance().getTypes();

//        ServiceContext.getInstance().startGetGenreDetail("0");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_appbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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

    @Subscribe
    public void setupUI(ServiceContext.OnMusicGenresLoadedEvent event){
        Log.d(TAG, "on Setting up main UI");

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MusicGenresListFragment());
        fragments.add(new FavoriteListFragment());
        fragments.add(new OfflineFragment());
        MusicFragmentPagerAdapter pagerAdapter = new MusicFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe
    public void onMusicGenreClicked(OnMusicGenreItemClickedEvent event) {
        MusicGenre musicGenre = event.getMusicGenre();
//        Toast.makeText(this,musicGenre.toString(), Toast.LENGTH_SHORT).show();
        ServiceContext.getInstance().startGetGenreDetail(musicGenre.getId());
        toGenreDetailActivity(musicGenre);
    }

    private void toGenreDetailActivity(MusicGenre musicGenre) {
        Intent intent = new Intent(MainActivity.this,GenreDetailActivity.class);
        intent.putExtra(MUSIC_GENRE_KEY, musicGenre.getId());
        startActivity(intent);
    }

    private void init() {
        ServiceContext.init("https://rss.itunes.apple.com/data/","https://itunes.apple.com/");
    }

}
