package com.example.kopiteshot.buoi15parserxml.view.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.kopiteshot.buoi15parserxml.model.ItemAdapter;
import com.example.kopiteshot.buoi15parserxml.model.ItemNew;
import com.example.kopiteshot.buoi15parserxml.R;
import com.example.kopiteshot.buoi15parserxml.control.database.LikeDatabase;
import com.example.kopiteshot.buoi15parserxml.control.database.MyDatabase;
import com.example.kopiteshot.buoi15parserxml.model.MyResource;
import com.example.kopiteshot.buoi15parserxml.view.MainActivity;
import com.example.kopiteshot.buoi15parserxml.view.browser.Newspaper_Fragment;

import java.util.ArrayList;

/**
 * Created by Kopiteshot on 5/16/2017.
 */

public class Saved_Fragement extends Fragment {
    private ListView listViewsave;
    private TextView textViewsave;
    private ItemAdapter itemAdaptersaved;

    private MyResource myResource;

    private MyDatabase myDatabase;
    private LikeDatabase likeDatabase;
    public static ArrayList<ItemNew> itemNewsaved = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.saved_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        myResource = (MyResource) getActivity().getApplicationContext();
        listViewsave = (ListView) getActivity().findViewById(R.id.lvsaved);
        textViewsave = (TextView) getActivity().findViewById(R.id.tvemptysaved);

        myDatabase = new MyDatabase(getActivity());
        likeDatabase = new LikeDatabase(getActivity());

        itemNewsaved = myDatabase.getData();
        check();
        itemAdaptersaved = new ItemAdapter(getActivity(), itemNewsaved);
        listViewsave.setAdapter(itemAdaptersaved);
        listViewsave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                myResource.url = itemNewsaved.get(position).getLink();
                myResource.htmlcode = itemNewsaved.get(position).getHtmlcode();
                myResource.check = 2;

                Newspaper_Fragment newspaper_fragment = new Newspaper_Fragment();
                fragmentTransaction.replace(R.id.fmlayout, newspaper_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        listViewsave.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_news: {
                                myDatabase.delete(itemNewsaved.get(position).getLink());
                                itemNewsaved.remove(position);
                                itemAdaptersaved.notifyDataSetChanged();


                                break;
                            }
                            case R.id.like_news: {
                                likeDatabase.insert(itemNewsaved.get(position));
                                MainActivity mainActivity = (MainActivity) getActivity();
                                Like_Fragment like_fragment = mainActivity.getLike_fragment();
                                like_fragment.initArr();
                                break;
                            }
                        }

                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });
    }

    public void update() {
        initArr();
        itemAdaptersaved.notifyDataSetChanged();
    }

    private void check() {
        if (itemNewsaved.isEmpty()) {
            listViewsave.setVisibility(View.GONE);
            textViewsave.setVisibility(View.VISIBLE);
        } else {
            listViewsave.setVisibility(View.VISIBLE);
            textViewsave.setVisibility(View.GONE);
        }
    }

    public void initArr() {
        itemNewsaved.clear();
        itemNewsaved.addAll(myDatabase.getData());
        check();
        itemAdaptersaved.notifyDataSetChanged();
    }


}
