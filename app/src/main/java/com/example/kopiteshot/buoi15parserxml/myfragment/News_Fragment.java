package com.example.kopiteshot.buoi15parserxml.myfragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kopiteshot.buoi15parserxml.R;
import com.example.kopiteshot.buoi15parserxml.itemnews.ItemAdapter;
import com.example.kopiteshot.buoi15parserxml.itemnews.ItemNew;
import com.example.kopiteshot.buoi15parserxml.mainactivity.MainActivity;
import com.example.kopiteshot.buoi15parserxml.myasynctask.AsyncTaskDownload;
import com.example.kopiteshot.buoi15parserxml.myasynctask.MyAsyncTask;
import com.example.kopiteshot.buoi15parserxml.mydatabase.MyDatabase;
import com.example.kopiteshot.buoi15parserxml.myresource.MyResource;
import com.example.kopiteshot.buoi15parserxml.mywebview.Newspaper_Fragment;

import java.util.ArrayList;
import java.util.Collection;

import static android.content.ContentValues.TAG;


/**
 * Created by Kopiteshot on 5/16/2017.
 */

public class News_Fragment extends Fragment {
    private ArrayList<ItemNew> itemNewArrayList = new ArrayList<>();
    private ListView listView;
    private ItemAdapter itemAdapter;
    private MyResource myResource;
    private TextView textView;
    private ItemNew chooseItem;
    private MyDatabase myDatabase;
    private String htmlCode;
    // private ArrayList<ItemNew> tmp = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_layout, container, false);
    }


    private void init() {
        htmlCode = "";
        myDatabase = new MyDatabase(getActivity());
        textView = (TextView) getActivity().findViewById(R.id.tvempty);
        myResource = (MyResource) getActivity().getApplicationContext();
        itemAdapter = new ItemAdapter(getActivity(), itemNewArrayList);
        listView = (ListView) getActivity().findViewById(R.id.lv);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                //  listView.setVisibility(View.GONE);

                String url = itemNewArrayList.get(position).getLink();
                myResource.url = url;
                myResource.htmlcode = itemNewArrayList.get(position).getHtmlcode();
                myResource.check = 1;
                Newspaper_Fragment newspaper_fragment = new Newspaper_Fragment();
                // newspaper_fragment.goUrl(myResource.url);
                fragmentTransaction.replace(R.id.fmlayout, newspaper_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        update();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String link = itemNewArrayList.get(position).getLink();

                AsyncTaskDownload asyncTaskDownload = new AsyncTaskDownload(handler1, News_Fragment.this);
                asyncTaskDownload.execute(link);
                chooseItem=itemNewArrayList.get(position);
//                itemNewArrayList.get(position).setHtmlcode(htmlCode);
//                myDatabase.insert(itemNewArrayList.get(position));
//
//                Log.d(TAG, "onItemLongClick: " + htmlCode);
//                MainActivity main_fragement = (MainActivity) getActivity();
//                main_fragement.getSaved_fragement().initArr();

                Toast.makeText(getActivity(), "Downloading...", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            itemNewArrayList.clear();
            if (msg.obj != null) {
                itemNewArrayList.addAll((Collection<? extends ItemNew>) msg.obj);
                itemNewArrayList.remove(itemNewArrayList.size() - 1);
                itemAdapter.notifyDataSetChanged();
            }
            if (itemNewArrayList.isEmpty()) {
                listView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            } else {
                listView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }
        }
    };

    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            htmlCode = (String) msg.obj;
            //
            chooseItem.setHtmlcode(htmlCode);
            myDatabase.insert(chooseItem);

            Log.d(TAG, "onItemLongClick: " + htmlCode);
            MainActivity main_fragement = (MainActivity) getActivity();
            main_fragement.getSaved_fragement().initArr();
            Log.d(TAG, "handleMessage: " + htmlCode);
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private String getLink() {
        MainActivity mainActivity = (MainActivity) getActivity();

        String tmp = Uri.encode(mainActivity.getKeyString());
        Log.d(TAG, "getLink: " + tmp);
        return tmp;
    }

    public void update() {
        itemNewArrayList.clear();
        MyAsyncTask myAsyncTask = new MyAsyncTask(handler, getActivity());
//        String link1 = ""
        String link = "https://news.google.de/news/feeds?pz=1&cf=vi_vn&ned=vi_vn&hl=vi_vn&q=";
        Log.d(TAG, "update: " + link + getLink());
        myAsyncTask.execute(link + getLink());
    }

}
