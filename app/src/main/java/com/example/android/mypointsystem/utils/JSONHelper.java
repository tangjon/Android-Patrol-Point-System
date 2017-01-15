package com.example.android.mypointsystem.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.android.mypointsystem.model.Entry;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by tangj on 1/5/2017.
 */

public class JSONHelper {

    private static final String FILE_NAME = "menuitems.json";
    private static final String TAG = "JSONHelper";

    public static boolean exportToJSON(Context context, List<Entry> entryList) {
        EntryItems entryData = new EntryItems();
        entryData.setEntryItems(entryList);

        Gson gson = new Gson();
        String jsonString = gson.toJson(entryData);
        Log.i(TAG, "exportToJSON: " + jsonString);

        FileOutputStream fileOutputStream = null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static List<Entry> importFromJson(Context context) {

        FileReader reader = null;

        try {
            File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
            reader = new FileReader(file);
            Gson gson = new Gson();
            EntryItems entryItems = gson.fromJson(reader, EntryItems.class);
            return entryItems.getEntryItems();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    static class EntryItems {
        List<Entry> entryItems;

        public List<Entry> getEntryItems() {
            return entryItems;
        }

        public void setEntryItems(List<Entry> entryItems) {
            this.entryItems = entryItems;
        }
    }
}
