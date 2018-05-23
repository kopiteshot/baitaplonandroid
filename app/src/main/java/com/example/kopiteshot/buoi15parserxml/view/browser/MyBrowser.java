package com.example.kopiteshot.buoi15parserxml.view.browser;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.webkit.WebViewClient;


// Hiển thị progress bar khi load trang đọc báo
public class MyBrowser extends WebViewClient {
    private ProgressDialog progressDialog;

    public MyBrowser(Fragment fragment) {
        progressDialog = new ProgressDialog(fragment.getActivity());
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        progressDialog.setMessage("Loading");
        progressDialog.show();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
