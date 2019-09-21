package com.nz.favorit;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nz.favorit.Adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout= findViewById(R.id.tablayout);
        ViewPager viewPager= findViewById(R.id.viewpager);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FavoriteMovieFragment(),getResources().getString(R.string.title_movie));
        adapter.AddFragment(new FavoriteTvFragment(), getResources().getString(R.string.title_tv));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
