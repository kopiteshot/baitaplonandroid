package com.example.kopiteshot.buoi15parserxml.control.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.kopiteshot.buoi15parserxml.model.ItemNew;
import com.example.kopiteshot.buoi15parserxml.control.xml.ParseXML;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Kopiteshot on 5/14/2017.
 */

public class MyAsyncTask extends AsyncTask<String, Void, ArrayList<ItemNew>> {
    public static final int WHAT_NEWS = 1;
    private Handler handler;
    private ProgressDialog progressDialog;

    public MyAsyncTask(Handler handler, Activity newsAtivity) {
        this.handler = handler;
        progressDialog = new ProgressDialog(newsAtivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressDialog.setMessage("Loading");
//        progressDialog.show();
    }

    @Override
    protected ArrayList<ItemNew> doInBackground(String... params) {
        String link = params[0];
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser parser = saxParserFactory.newSAXParser();
            ParseXML parseXML = new ParseXML();
            parser.parse(link, parseXML);
            Log.d(TAG, "doInBackground: " + parseXML);
            parseXML.getArr().remove(0);
            return parseXML.getArr();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemNew> itemNews) {
        super.onPostExecute(itemNews);
        Message message = new Message();
        message.what = WHAT_NEWS;
        message.obj = itemNews;
        handler.sendMessage(message);
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//        }
    }
}
