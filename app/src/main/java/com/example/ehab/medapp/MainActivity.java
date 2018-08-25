package com.example.ehab.medapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ehab.medapp.fragments.MedsFragment;
import com.example.ehab.medapp.fragments.PressureFragment;
import com.example.ehab.medapp.fragments.TimelineFragment;
import com.example.ehab.medapp.fragments.WeightFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    private Drawer result;

    private Fragment fragment;
    private FirebaseAnalytics mFirebaseAnalytics;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AddMedsFragment();
                toolbarTitle.setText(R.string.add_medicine);
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

              AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .addProfiles(
                        new ProfileDrawerItem().withIcon(R.mipmap.ic_launcher)
                ).withSelectionListEnabledForSingleProfile(false).build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.time_line);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.medicines);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.measurements);


        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        item1, item2,item3, new DividerDrawerItem()

                ) .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){


                            case 1:
                                fragment = null;
                                fragment = new TimelineFragment();
                                if (fragment != null) {

                                    FragmentTransaction fragmentTransaction =
                                            getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.commit();
                                    onBackPressed();

                                }
                                break;

                            case 2:
                                fragment = null;
                                fragment = new MedsFragment();
                                if (fragment != null) {


                                    FragmentTransaction fragmentTransaction =
                                            getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.commit();
                                    onBackPressed();

                                }
                                break;
                            case 3:
                                Intent intent= new Intent(MainActivity.this,MeasurementsActivity.class);
                                startActivity(intent);
                                break;

                        }
                        return false;
                    }
                }).build();
        if(savedInstanceState==null) {
            fragment = new TimelineFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        }

        else {
            if(fab.getVisibility()!=View.VISIBLE)
                fab.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }
}
