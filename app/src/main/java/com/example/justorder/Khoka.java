package com.example.justorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class Khoka extends AppCompatActivity {
    private ViewPager mViewPager;
    private PagerAdapter mSectionsPagerAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoka);
        mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager1);

        mSectionsPagerAdapter.addFragments(new FragmentChicken(), "A");
        mSectionsPagerAdapter.addFragments(new FragmentSpectrum1(), "b");
        mSectionsPagerAdapter.addFragments(new FragmentSpectrum2(), "C");

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts1);
        navigationTabStrip.setViewPager(mViewPager);
    }}







