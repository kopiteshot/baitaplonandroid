package com.example.kopiteshot.buoi15parserxml.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kopiteshot.buoi15parserxml.R;
import com.example.kopiteshot.buoi15parserxml.model.FragmentManager;
import com.example.kopiteshot.buoi15parserxml.view.fragment.Like_Fragment;
import com.example.kopiteshot.buoi15parserxml.view.fragment.News_Fragment;
import com.example.kopiteshot.buoi15parserxml.view.fragment.Saved_Fragement;
import com.example.kopiteshot.buoi15parserxml.view.fragment.StatusbarUtils;
import com.example.kopiteshot.buoi15parserxml.view.pager.View_Pager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, SearchView.OnQueryTextListener {
    private SearchView editTextSearch;
    private ViewPager viewPager;
    private View_Pager view_pager;
    private String tmp;
    private News_Fragment news_fragment;
    private Saved_Fragement saved_fragement;
    private Like_Fragment like_fragment;
    private Like_Fragment like_fragment1;
    private ArrayList<FragmentManager> fragmentManagers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusbarUtils.darkenStatusBar(this, R.color.colorPrimary);
        init();
    }

    public Saved_Fragement getSaved_fragement() {
        return saved_fragement;
    }

    public Like_Fragment getLike_fragment() {
        return like_fragment;
    }

    public News_Fragment getNews_fragment() {
        return news_fragment;
    }

    // khởi tạo view và thuộc tính
    private void init() {
        tmp = "";
        news_fragment = new News_Fragment();
        saved_fragement = new Saved_Fragement();
        like_fragment = new Like_Fragment();
        fragmentManagers.add(new FragmentManager("TIN TỨC", news_fragment));
        fragmentManagers.add(new FragmentManager("ĐÃ LƯU", saved_fragement));
        fragmentManagers.add(new FragmentManager("YÊU THÍCH", like_fragment));
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        view_pager = new View_Pager(getSupportFragmentManager(), fragmentManagers);
        viewPager.setAdapter(view_pager);


        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        editTextSearch = (SearchView) menuItem.getActionView();
        editTextSearch.setOnQueryTextListener(this);
        return true;
    }


    // tu khoa tim kiem
    public String getKeyString() {
        return tmp;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        tmp = query;
        News_Fragment x = (News_Fragment) fragmentManagers.get(0).getFragment();
        x.update(1);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
