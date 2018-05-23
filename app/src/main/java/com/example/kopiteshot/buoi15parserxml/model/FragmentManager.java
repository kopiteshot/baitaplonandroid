package com.example.kopiteshot.buoi15parserxml.model;


import android.support.v4.app.Fragment;

/**
 * Created by Kopiteshot on 5/16/2017.
 */

public class FragmentManager {
    private String name;
    private Fragment fragment;

    public FragmentManager(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public String toString() {
        return name;
    }
}
