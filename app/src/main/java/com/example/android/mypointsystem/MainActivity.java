package com.example.android.mypointsystem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.mypointsystem.adapter.EntryListDataAdapter;
import com.example.android.mypointsystem.model.Entry;
import com.example.android.mypointsystem.utils.JSONHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Internal Storage Write File
    public static final String FILE_NAME = "lorem_ipsum.text";
    private static final int REQUEST_PERMISSION_WRITE = 1001;

    // Field for ActivityResult check
    final int REQUEST_CODE = 1;

    private static final String TAG = "MainActivity";
    // Field for PatrolListData to be formatted
    EntryListDataAdapter adapter;
    // Field for Displaying above formatted data
    RecyclerView recyclerView;
    // Field for List of Patrol Profiles
    public List<Entry> entryList = new ArrayList<>();

    private boolean permissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!permissionGranted) {
            checkPermissions();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SIMPLE ALERT
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setTitle("Create New Entry");
//                alertDialog.setMessage("Alert message to be shown");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();

                // SIMPLE ALERT BOX WITH TEXT
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                alert.setTitle("Create New Entry");
                alert.setMessage("Enter Your Title");

                alert.setView(edittext);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
//                        Editable YouEditTextValue = edittext.getText();
                        //OR
                        String inputString = edittext.getText().toString();
                        addEntry(inputString);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();

                updateScreen();
            }
        });

        // Load Previously Saved Files
//        entryList = JSONHelper.importFromJson(this);
//        if (entryList == null){
//            Toast.makeText(this, "LOADED FROM EMPTY", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Toast.makeText(this, "LOADED", Toast.LENGTH_SHORT).show();
//        }

        // CUSTOM ARRAY RECYCLERVIEWADAPTER WITH COURSE OBJECTS
        adapter = new EntryListDataAdapter(this, entryList);
        // Link a ListView to ListView widget
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);

        // Here you modify your LinearLayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_import:
                List<Entry> entryItems = JSONHelper.importFromJson(this);
                if (entryItems != null) {
                    for (Entry entryItem : entryItems) {
                        Log.i(TAG, "onOptionsItemsSelected: " + entryItem.getENTRY_NAME());
                    }
                    override(entryItems);
                }
                return true;
            case R.id.action_export:
                boolean result = JSONHelper.exportToJSON(this, entryList);
                if (result) {
                    Toast.makeText(this, "DATA EXPORTED", Toast.LENGTH_SHORT).show();
                    for (Entry entryItem : entryList) {
                        Log.i(TAG, "onOptionsItemsSelected: " + entryItem.getENTRY_NAME());
                    }
                } else {
                    Toast.makeText(this, "EXPORT FAILED", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.action_refresh:
                Toast.makeText(this, "REFRESHED", Toast.LENGTH_SHORT).show();
                updateScreen();
                return true;
            case R.id.action_reset:
                reset();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Pull Parcelable Data from Child
                Entry returnEntry = data.getExtras().getParcelable("returnData");
                int index = getIndex(returnEntry);
                if (index != -1) {
                    // Replace EntryList[i] with updated Entry
                    entryList.set(index, returnEntry);
                    updateScreen();
                }
            }
        }
    }
//    public void override(){
//        EntryListDataAdapter newAdapter = new EntryListDataAdapter(this, entryList2);
//        recyclerView.setAdapter(newAdapter);
//    }

    public void reset(){
        entryList.clear();
        override(entryList);
    }
    public void override(List<Entry> entryItems){
        entryList = entryItems;
        adapter = new EntryListDataAdapter(this, entryList);
        recyclerView.setAdapter(adapter);
    }
    public void updateScreen() {
        recyclerView.setAdapter(adapter);
    }

    public int getIndex(Entry entry) {
        for (int i = 0; i < entryList.size(); i++) {
            if (entry.getItemId().equals(entryList.get(i).getItemId())) {
                return i;
            }
        }
        return -1;
    }

    public void addEntry(String string) {
        Entry entry;
        if (string.length() == 0) {
            entry = new Entry();
        } else {
            entry = new Entry(string);
        }

        entryList.add(entry);
        boolean result = JSONHelper.exportToJSON(this, entryList);

        updateScreen();
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}