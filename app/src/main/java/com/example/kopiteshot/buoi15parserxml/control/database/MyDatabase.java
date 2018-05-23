package com.example.kopiteshot.buoi15parserxml.control.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.kopiteshot.buoi15parserxml.model.ItemNew;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Kopiteshot on 5/18/2017.
 */

public class MyDatabase implements DatabaseInterface {

    public static final String TABLE_NAME_SAVE = "newstable";
    public static final String IMAGE = "IMAGE";
    public static final String LINK = "LINK";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PUBDATE = "PUBDATE";
    public static final String GUID = "GUID";
    public static final String TITLE = "TITLE";
    public static final String HTMLCODE = "HTMLCODE";

    private SQLiteDatabase database;
    public static final String PATH = Environment.getDataDirectory().getPath() + "/data/com.example.kopiteshot.buoi15parserxml/databases/mymagazines.sqlite";

    private Context context;

    public MyDatabase(Context context) {
        this.context = context;
        copyFile();
    }

    @Override
    public void copyFile() {
        File file = new File(PATH);
        if (file.exists() == false) {
            File parent = file.getParentFile();
            parent.mkdirs();
            try {
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                InputStream inputStream = context.getAssets().open("mymagazines.sqlite");
                byte[] b = new byte[1024];
                int count = inputStream.read(b);
                while (count != -1) {
                    fileOutputStream.write(b, 0, count);
                    count = inputStream.read(b);
                }
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void openDatabase() {
        database = context.openOrCreateDatabase(PATH, Context.MODE_PRIVATE, null);
    }

    @Override
    public void closeDatabase() {
        database.close();
    }


    @Override
    public ArrayList<ItemNew> getData() {
        ArrayList<ItemNew> arr = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.query(TABLE_NAME_SAVE, null, null, null, null, null, null);
        cursor.moveToFirst();
        int indexImage = cursor.getColumnIndex(IMAGE);
        int indexLink = cursor.getColumnIndex(LINK);
        int indexDescription = cursor.getColumnIndex(DESCRIPTION);
        int indexPubdate = cursor.getColumnIndex(PUBDATE);
        int indexGuid = cursor.getColumnIndex(GUID);
        int indexTitle = cursor.getColumnIndex(TITLE);
        int indexHtmlcode = cursor.getColumnIndex(HTMLCODE);
        while (cursor.isAfterLast() == false) {
            String image = cursor.getString(indexImage);
            String link = cursor.getString(indexLink);
            String description = cursor.getString(indexDescription);
            String pubdate = cursor.getString(indexPubdate);
            String guid = cursor.getString(indexGuid);
            String title = cursor.getString(indexTitle);
            String htmlcode = cursor.getString(indexHtmlcode);
            ItemNew s = new ItemNew(title, description, pubdate, link, image, guid, htmlcode);
            arr.add(s);
            cursor.moveToNext();
        }
        closeDatabase();
        return arr;
    }


    @Override
    public long insert(ItemNew student) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        String image, link, guid, pubdate, title, htmlcode, des;
        image = student.getImage();
        contentValues.put(IMAGE, image);
        link = student.getLink();
        contentValues.put(LINK, link);
        des = student.getDescription();
        contentValues.put(DESCRIPTION, des);
        guid = student.getGuid();
        contentValues.put(GUID, guid);
        pubdate = student.getPubDate();
        contentValues.put(PUBDATE, pubdate);
        title = student.getTitle();
        contentValues.put(TITLE, title);
        htmlcode = student.getHtmlcode();
        contentValues.put(HTMLCODE, htmlcode);
        long newid = database.insert(TABLE_NAME_SAVE, null, contentValues);
        closeDatabase();
        return newid;
    }

    @Override
    public int update(ItemNew student) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE, student.getImage());

        contentValues.put(DESCRIPTION, student.getDescription());
        contentValues.put(GUID, student.getGuid());
        contentValues.put(PUBDATE, student.getPubDate());
        contentValues.put(TITLE, student.getTitle());
        contentValues.put(HTMLCODE, student.getHtmlcode());
        String selection = LINK + "=?";
        String[] seclectionAgs = {student.getLink() + ""};
        int rows = database.update(TABLE_NAME_SAVE, contentValues, selection, seclectionAgs);
        closeDatabase();
        return rows;
    }


    public int delete(String id) {
        openDatabase();
        String selection = LINK + "=?";
        String[] seclectionAgs = {id + ""};
        int rows = database.delete(TABLE_NAME_SAVE, selection, seclectionAgs);
        closeDatabase();
        return rows;
    }

}
