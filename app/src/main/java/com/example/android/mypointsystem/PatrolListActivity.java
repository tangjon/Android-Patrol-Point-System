package com.example.android.mypointsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mypointsystem.adapter.EntryListDataAdapter;
import com.example.android.mypointsystem.adapter.MemberListDataAdapter;
import com.example.android.mypointsystem.adapter.PatrolListDataAdapter;
import com.example.android.mypointsystem.model.Entry;
import com.example.android.mypointsystem.model.MemberProfile;
import com.example.android.mypointsystem.model.PatrolProfile;
import com.example.android.mypointsystem.sample.StaticSampleData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PatrolListActivity extends AppCompatActivity {

    // Field for ActivityResult check
    final int REQUEST_CODE = 1;

    // Field for PatrolListData to be formatted
    PatrolListDataAdapter adapter;
    // Field for Displaying above formatted data
    RecyclerView recyclerView;
    // Field for List of Patrol Profiles
    List<PatrolProfile> patrolList;
    Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_list);

        // ALLOW BACK BUTTON TO BE DISPLAYED (VISUAL)
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initiate Patrol List
        entry = getIntent().getExtras().getParcelable(EntryListDataAdapter.ITEM_KEY);

//        Toast.makeText(this, "SENT ITEM ID: " + entry.getItemId(), Toast.LENGTH_SHORT).show();

        patrolList = entry.getPatrolList();

        // SORTING PATROL LIST
        Collections.sort(patrolList, new Comparator<PatrolProfile>() {
            @Override
            public int compare(PatrolProfile o1, PatrolProfile o2) {
                return o1.getPATROL_NAME().compareTo(o2.getPATROL_NAME());
            }
        });

        // PRINTING PATROL NAMES
//        textDisplay1.setText("");
//        for (int i = 0; i < patrolList.size() ; i++) {
//            textDisplay1.append(patrolList.get(i).getPATROL_NAME() + "\n");
//        }

        // DEFAULT ARRAY ADAPTER WITH STRINGS
        // Array Adapter to Prepare information to be listed in a list
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//               this, android.R.layout.simple_list_item_1,couresListStrings
//        );

        // CUSTOM ARRAY LISTVIEWADAPTER WITH COURSE OBJECTS
//        PatrolListDataAdapterListView adapter = new PatrolListDataAdapterListView(this, patrolList);
//        // Link a ListView to ListView widget
//        ListView listView = (ListView) findViewById(R.id.list);
//        listView.setAdapter(adapter);


        // CUSTOM ARRAY RECYCLERVIEWADAPTER WITH COURSE OBJECTS
        adapter = new PatrolListDataAdapter(this, patrolList);
        // Link a ListView to ListView widget
        recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        recyclerView.setAdapter(adapter);


    }

    // CALLED WHEN CHILD FINISHES
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Pull Parcelable Data from Child
                PatrolProfile patrolProfile = data.getExtras().getParcelable("returnData");
                int index = getIndex(patrolProfile);
                if (index != -1) {
                    // Replace PatrolList[i] with updated PatrolProfile
                    patrolList.set(index, patrolProfile);
//                    Toast.makeText(this, Integer.toString(patrolList.get(index).getPointSummary()), Toast.LENGTH_SHORT).show();
                    updateScreen();
                }
            }
        }
    }

    // Refresh the adapter with new update data
    public void updateScreen() {
        recyclerView.setAdapter(adapter);
    }

    // get the index of a patrolProfile in the patrolList
    public int getIndex(PatrolProfile someProfile) {
        for (int i = 0; i < patrolList.size(); i++) {
            if (someProfile.getPATROL_NAME().equals(patrolList.get(i).getPATROL_NAME())) {
                return i;
            }
        }
        return -1;
    }

    // ALLOWS BACK NAVIGATION TO PARENT ACTIVITY
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            entry.setPatrolList(patrolList);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("returnData", entry);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        return true;
    }
}
