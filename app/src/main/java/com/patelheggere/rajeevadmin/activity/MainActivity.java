package com.patelheggere.rajeevadmin.activity;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.patelheggere.rajeevadmin.R;
import com.patelheggere.rajeevadmin.base.BaseActivity;
import com.patelheggere.rajeevadmin.fragments.EventsFragment;
import com.patelheggere.rajeevadmin.fragments.GallryFragment;
import com.patelheggere.rajeevadmin.fragments.JobFragment;
import com.patelheggere.rajeevadmin.fragments.NewsFragment;

public class MainActivity extends BaseActivity implements
        JobFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        EventsFragment.OnFragmentInteractionListener,
        GallryFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            Fragment curFrag = fragmentManager.getPrimaryNavigationFragment();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d(TAG, "onNavigationItemSelected: ");
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                    Fragment fragment = fragmentManager.findFragmentByTag("NEWS");
                    if (fragment == null) {
                        fragment = new NewsFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "NEWS");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();

                    return true;
                /*case R.id.navigation_dashboard:
                    Log.d(TAG, "onNavigationItemSelected: ");
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                   fragment = fragmentManager.findFragmentByTag("JOB");
                    if (fragment == null) {
                        fragment = new JobFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "JOB");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();

                    return true;*/
                case R.id.navigation_notifications:
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                    fragment = fragmentManager.findFragmentByTag("EVENTS");
                    if (fragment == null) {
                        fragment = new EventsFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "EVENTS");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();
                    return true;
                case R.id.navigation_gallery:
                    if (curFrag != null) {
                        fragmentTransaction.detach(curFrag);
                    }
                    fragment = fragmentManager.findFragmentByTag("GALLERY");
                    if (fragment == null) {
                        fragment = new GallryFragment();
                        fragmentTransaction.add(R.id.contentFrame, fragment, "GALLERY");
                    } else {
                        fragmentTransaction.attach(fragment);
                    }
                    fragmentTransaction.setPrimaryNavigationFragment(fragment);
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commitNowAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };



    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

