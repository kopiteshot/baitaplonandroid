package com.example.kopiteshot.buoi15parserxml.view.fragment;

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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kopiteshot.buoi15parserxml.R;
import com.example.kopiteshot.buoi15parserxml.control.asynctask.AsyncTaskDownload;
import com.example.kopiteshot.buoi15parserxml.control.asynctask.MyAsyncTask;
import com.example.kopiteshot.buoi15parserxml.control.database.MyDatabase;
import com.example.kopiteshot.buoi15parserxml.model.ItemAdapter;
import com.example.kopiteshot.buoi15parserxml.model.ItemNew;
import com.example.kopiteshot.buoi15parserxml.model.MyResource;
import com.example.kopiteshot.buoi15parserxml.model.Utils;
import com.example.kopiteshot.buoi15parserxml.view.MainActivity;
import com.example.kopiteshot.buoi15parserxml.view.browser.Newspaper_Fragment;

import java.util.ArrayList;
import java.util.Collection;

import static android.content.ContentValues.TAG;


public class News_Fragment extends Fragment {
    private ArrayList<ItemNew> itemNewArrayList = new ArrayList<>();
    private ListView listView;
    private ItemAdapter itemAdapter;
    private MyResource myResource;
    private TextView textView;
    private ItemNew chooseItem;
    private MyDatabase myDatabase;
    private String htmlCode;

    private int page;

    private int current_page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_layout, container, false);
    }


    // khởi tạo view
    private void init() {
        page = 1;
        current_page = page;
        htmlCode = "";
        myDatabase = new MyDatabase(getActivity());
        textView = (TextView) getActivity().findViewById(R.id.tvempty);
        myResource = (MyResource) getActivity().getApplicationContext();
        itemAdapter = new ItemAdapter(getActivity(), itemNewArrayList);
        listView = (ListView) getActivity().findViewById(R.id.lv);
        listView.setAdapter(itemAdapter);

        // xử lý sự kiện
        listener();
    }


    public void listener() {

        // neu click thi đọc báo
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                processOnItemClick(position);
            }
        });


        update(page);
        // nếu long click thi tải offline
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                processOnItemLongClick(position);
                return true;
            }
        });

        // load gần cuối thì load thêm
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = listView.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count - threshold) {
                        update(page++);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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
                for (ItemNew itemNew : itemNewArrayList){
                    Log.d(TAG, "handleMessage: "+ itemNew.getTitle());
                }
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

    public void update(int page) {
        itemNewArrayList.clear();
        MyAsyncTask myAsyncTask = new MyAsyncTask(handler, getActivity());
        myAsyncTask.execute(Utils.head_link + page + "" + Utils.foot_link + getLink());
        Log.d(TAG, "update: " + Utils.head_link + page + "" + Utils.foot_link + getLink());
    }

    public void processOnItemClick(int position) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        String url = itemNewArrayList.get(position).getLink();
        myResource.url = url;
        myResource.htmlcode = itemNewArrayList.get(position).getHtmlcode();
        myResource.check = 1;
        Newspaper_Fragment newspaper_fragment = new Newspaper_Fragment();
        fragmentTransaction.replace(R.id.fmlayout, newspaper_fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void processOnItemLongClick(int position) {
        String link = itemNewArrayList.get(position).getLink();
        AsyncTaskDownload asyncTaskDownload = new AsyncTaskDownload(handler1, News_Fragment.this);
        asyncTaskDownload.execute(link);
        chooseItem = itemNewArrayList.get(position);
        Toast.makeText(getActivity(), "Downloading...", Toast.LENGTH_LONG).show();
    }
}
