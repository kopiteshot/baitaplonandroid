package com.example.kopiteshot.buoi15parserxml.control.database;

import com.example.kopiteshot.buoi15parserxml.model.ItemNew;

import java.util.ArrayList;

public interface DatabaseInterface {
    void copyFile();
    void openDatabase();
    void closeDatabase();
    ArrayList<ItemNew> getData();
    long insert(ItemNew itemNew);
    int update(ItemNew student);
    int delete(String id);

}
