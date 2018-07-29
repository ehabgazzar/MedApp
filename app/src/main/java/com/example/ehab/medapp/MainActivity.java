package com.example.ehab.medapp;

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
import com.example.ehab.medapp.fragments.TimelineFragment;
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

              AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .addProfiles(
                        new ProfileDrawerItem().withName("Name" + " " +"last name").withEmail("email")
                                .withIcon(R.mipmap.ic_launcher)
                ).withSelectionListEnabledForSingleProfile(false)
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Timeline");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Medicines");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Measurements");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Settings");

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1, item2,item3, new DividerDrawerItem(),item4

                ) .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){


                            case 1:
                                fragment = null;
                                fragment = new TimelineFragment();
                                if (fragment != null) {

                                    toolbarTitle.setText("Timeline");
                                    FragmentTransaction fragmentTransaction =
                                            getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.commit();
                                    onBackPressed();
                                    fab.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Replace with your own action",
                                                    Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    });
                                }
                                break;

                            case 2:
                                fragment = null;
                                fragment = new MedsFragment();
                                if (fragment != null) {

                                    toolbarTitle.setText("Medicines");
                                    FragmentTransaction fragmentTransaction =
                                            getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.commit();
                                    onBackPressed();
                                    fab.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar.make(view, "Na action", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    });
                                }
                                break;
                            case 4:

                                break;

                        }
                        return false;
                    }
                }).build();
        fragment= new TimelineFragment();
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
