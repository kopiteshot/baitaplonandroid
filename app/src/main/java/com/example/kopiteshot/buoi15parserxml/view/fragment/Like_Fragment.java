package com.example.kopiteshot.buoi15parserxml.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kopiteshot.buoi15parserxml.R;
import com.example.kopiteshot.buoi15parserxml.control.database.LikeDatabase;
import com.example.kopiteshot.buoi15parserxml.model.ItemAdapter;
import com.example.kopiteshot.buoi15parserxml.model.ItemNew;
import com.example.kopiteshot.buoi15parserxml.model.MyResource;
import com.example.kopiteshot.buoi15parserxml.view.browser.Newspaper_Fragment;

import java.util.ArrayList;

/**
 * Created by Kopiteshot on 5/16/2017.
 */

public class Like_Fragment extends Fragment {
    private ArrayList<ItemNew> itemNews = new ArrayList<>();
    private ListView listViewlike;
    private TextView textViewlike;
    private ItemAdapter itemAdapter;
    private LikeDatabase likeDatabase;
    private MyResource myResource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.like_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        myResource = (MyResource) getActivity().getApplicationContext();
        listViewlike = (ListView) getActivity().findViewById(R.id.lvliked);
        textViewlike = (TextView) getActivity().findViewById(R.id.tvemptyliked);
        itemAdapter = new ItemAdapter(getActivity(), itemNews);
        likeDatabase = new LikeDatabase(getActivity());
        itemNews.clear();
        itemNews.addAll(likeDatabase.getData());
        check();
        listViewlike.setAdapter(itemAdapter);
        listViewlike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                myResource.url = itemNews.get(position).getLink();
                myResource.htmlcode = itemNews.get(position).getHtmlcode();
                myResource.check = 3;

                Newspaper_Fragment newspaper_fragment = new Newspaper_Fragment();
                fragmentTransaction.replace(R.id.fmlayout, newspaper_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void check() {
        if (itemNews.isEmpty()) {
            listViewlike.setVisibility(View.GONE);
            textViewlike.setVisibility(View.VISIBLE);
        } else {
            listViewlike.setVisibility(View.VISIBLE);
            textViewlike.setVisibility(View.GONE);
        }
    }

    public void initArr() {
        itemNews.clear();
        itemNews.addAll(likeDatabase.getData());
        check();
        itemAdapter.notifyDataSetChanged();
    }
}
