package com.example.kopiteshot.buoi15parserxml.mywebview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.kopiteshot.buoi15parserxml.myresource.MyResource;
import com.example.kopiteshot.buoi15parserxml.R;

/**
 * Created by Kopiteshot on 5/17/2017.
 */

public class Newspaper_Fragment extends Fragment {

    private WebView webView;
    private MyResource myResource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.newspaper_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        webView = (WebView) getActivity().findViewById(R.id.webview);
        webView.setWebViewClient(new MyBrowser(this));
        myResource = (MyResource) getActivity().getApplicationContext();
        int check = myResource.check;
        if (check == 1) {
            goUrl(myResource.url);
        } else {
            String html = myResource.htmlcode;
            webView = (WebView) getActivity().findViewById(R.id.webview);
            webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
//                Toast.makeText(getActivity(),"ok",Toast.LENGTH_SHORT).show();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//             //   MainActivity mainActivity = (MainActivity) getActivity();
//               // fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                getActivity().getFragmentManager().popBackStack();
                return true;
            }
        }
        return false;
    }


    public void goUrl(String url) {
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

}
