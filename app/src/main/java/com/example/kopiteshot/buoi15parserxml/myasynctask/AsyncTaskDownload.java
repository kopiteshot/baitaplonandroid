package com.example.kopiteshot.buoi15parserxml.myasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Kopiteshot on 5/18/2017.
 */

public class AsyncTaskDownload extends AsyncTask<String, Integer, String> {
    public static final int WHAT_UPDATE_PROGRESS = 1;
    public static final int WHAT_URL_RESULT = 2;
    private Handler handler;
    private ProgressDialog progressDialog;

    public AsyncTaskDownload(Handler handler, Fragment fragment) {
        this.handler = handler;
        progressDialog = new ProgressDialog(fragment.getActivity());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... urlStr) {
        // do stuff on non-UI thread
        StringBuffer htmlCode = new StringBuffer();
        try {
            URL url = new URL(urlStr[0]);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                htmlCode.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlCode.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Message message = new Message();
        message.what = WHAT_URL_RESULT;
        message.obj = s;
        handler.sendMessage(message);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Message message = new Message();
        message.what = WHAT_UPDATE_PROGRESS;
        message.arg1 = values[0];
        handler.sendMessage(message);
    }

    public FileOutputStream getOutputStream(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        return fileOutputStream;
    }
}
