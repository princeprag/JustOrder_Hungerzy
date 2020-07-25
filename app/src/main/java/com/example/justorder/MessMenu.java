package com.example.justorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class MessMenu extends AppCompatActivity {
    private ViewPager mViewPager;
    private PagerAdapter mSectionsPagerAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mSectionsPagerAdapter.addFragments(new FragmentVeg(),"A");
        mSectionsPagerAdapter.addFragments(new FragmentNonVeg(),"b");

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setViewPager(mViewPager);






    }
}
