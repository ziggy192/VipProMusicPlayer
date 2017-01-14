package com.example.nghia.vippromusicplayer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.nghia.vippromusicplayer.R;
import com.example.nghia.vippromusicplayer.activities.activities_as_fragments.NewMainActivityFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TestingNewBasicMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_new_basic_main);
        changeFragment(new NewMainActivityFragment(),false);

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
    public void fragmentChanging(DemandFragmentChangingEvent event) {
        changeFragment(event.getFragment(), event.isAddToBackstack());
    }

    private void changeFragment(Fragment desFragment, boolean addToBackstack) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, desFragment);
        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public static class DemandFragmentChangingEvent {
        private Fragment fragment;
        private boolean addToBackstack;

        public DemandFragmentChangingEvent(Fragment fragment, boolean addToBackstack) {
            this.fragment = fragment;
            this.addToBackstack = addToBackstack;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public boolean isAddToBackstack() {
            return addToBackstack;
        }
    }



}
