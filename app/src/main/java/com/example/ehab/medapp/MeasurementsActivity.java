package com.example.ehab.medapp;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ehab.medapp.fragments.AddMeasureFragment;
import com.example.ehab.medapp.fragments.PressureFragment;
import com.example.ehab.medapp.fragments.TimelineFragment;
import com.example.ehab.medapp.fragments.WeightFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ehab.medapp.fragments.AddMeasureFragment.Fragment_key;

public class MeasurementsActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fab_m)
    FloatingActionButton fab;
    private AddMeasureFragment fragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);

        ButterKnife.bind(this);
        toolbar.setTitle(R.string.measurements);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
                Fragment_key=null;
            }
        });

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        if(Fragment_key!=null) {

                fab.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);



        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AddMeasureFragment();

                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                fab.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PressureFragment(), getResources().getString(R.string.blood_pressure));
        adapter.addFragment(new WeightFragment(), getResources().getString(R.string.weight));
//        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment_key=null;
    }
}
