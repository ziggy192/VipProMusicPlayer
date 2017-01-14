package com.example.nghia.vippromusicplayer.activities.activities_as_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.activities.GenreDetailActivity;
import com.example.nghia.vippromusicplayer.activities.MainActivity;
import com.example.nghia.vippromusicplayer.activities.TestingNewBasicMainActivity;
import com.example.nghia.vippromusicplayer.adapters.MusicFragmentPagerAdapter;
import com.example.nghia.vippromusicplayer.events.OnMusicGenreItemClickedEvent;
import com.example.nghia.vippromusicplayer.fragments.FavoriteListFragment;
import com.example.nghia.vippromusicplayer.fragments.MusicGenresListFragment;
import com.example.nghia.vippromusicplayer.fragments.OfflineFragment;
import com.example.nghia.vippromusicplayer.models.MusicGenre;
import com.example.nghia.vippromusicplayer.utils.ServiceContext;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewMainActivityFragment extends Fragment {
    private static final String TAG = MainActivity.class.toString();
    public static final String MUSIC_GENRE_KEY = "music_genre";
    @BindView(R.id.main_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    AppCompatActivity mainActivity;



    public NewMainActivityFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (AppCompatActivity) context;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater = mainActivity.getMenuInflater();
        menuInflater.inflate(R.menu.main_appbar_menu,menu);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_new_basic_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        ServiceContext.getInstance().getTypes();
        toolbar.setTitle("Explore");
        mainActivity.setSupportActionBar(toolbar);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
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
        MusicFragmentPagerAdapter pagerAdapter = new MusicFragmentPagerAdapter(this.getChildFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe
    public void onMusicGenreClicked(OnMusicGenreItemClickedEvent event) {
        MusicGenre musicGenre = event.getMusicGenre();
        NewGenreDetailFragment newGenreDetailFragment = new NewGenreDetailFragment();
        Bundle args = new Bundle();
        args.putString(MUSIC_GENRE_KEY, event.getMusicGenre().getId());
        newGenreDetailFragment.setArguments(args);
        EventBus.getDefault().post(
                new TestingNewBasicMainActivity.DemandFragmentChangingEvent
                        (newGenreDetailFragment,true));
    }


}
