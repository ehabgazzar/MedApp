package com.example.ehab.medapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener{

    private Drawer result;

    private Fragment fragment;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    @BindView(R.id.calendarView)
    MaterialCalendarView widget;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        widget.setOnDateChangedListener(this);



              AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.side_nav_bar)
                .addProfiles(
                        new ProfileDrawerItem().withName("Name" + " " +"last name").withEmail("email").withIcon(R.mipmap.ic_launcher)
                ).withSelectionListEnabledForSingleProfile(false)
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Orders");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Requests");

//            PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Settings");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Logout");

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1, item2, new DividerDrawerItem(),  item4

                ) .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){


                            case 1:
                                fragment = null;
                            //    fragment = new OrdersFragment();
                                if (fragment != null) {

//                                    toolbar.setTitle("Orders");
                                    FragmentTransaction fragmentTransaction =
                                            getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.commit();
                                    onBackPressed();
                                }
                                break;

                            case 2:
                                fragment = null;
                         //       fragment = new RequestsFragment();
                                if (fragment != null) {

//                                    toolbar.setTitle("Requests");
                                    FragmentTransaction fragmentTransaction =
                                            getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.commit();
                                    onBackPressed();
                                }
                                break;
                            case 4:

                                break;

                        }
                        return false;
                    }
                }).build();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        Toast.makeText(this, ""+FORMATTER.format(calendarDay.getDate()), Toast.LENGTH_SHORT).show();
    }
}
