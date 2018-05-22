package com.example.kopiteshot.buoi15parserxml.myfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Kopiteshot on 5/16/2017.
 */

public class View_Pager extends FragmentPagerAdapter {
    private ArrayList<com.example.kopiteshot.buoi15parserxml.myfragment.FragmentManager> fragmentManagers = new ArrayList<>();

    public View_Pager(FragmentManager fm, ArrayList<com.example.kopiteshot.buoi15parserxml.myfragment.FragmentManager> fragmentManagers) {
        super(fm);
        this.fragmentManagers = fragmentManagers;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentManagers.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentManagers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentManagers.get(position).getName();
    }
}
