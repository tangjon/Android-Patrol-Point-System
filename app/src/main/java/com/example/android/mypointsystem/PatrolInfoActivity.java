package com.example.android.mypointsystem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mypointsystem.adapter.MemberListDataAdapter;
import com.example.android.mypointsystem.adapter.PatrolListDataAdapter;
import com.example.android.mypointsystem.model.MemberProfile;
import com.example.android.mypointsystem.model.PatrolProfile;

import java.io.IOException;
import java.io.InputStream;

public class PatrolInfoActivity extends AppCompatActivity {

    // ADAPTER THAT FORMATS PATROLPROFILE DATA
    MemberListDataAdapter adapter;
    // WHERE THE ADAPTER INFORMATION IS SHOWN TO USER
    RecyclerView recyclerView;

    // CONTAINS ENTIRE PATROL PROFILE
    PatrolProfile patrolProfile;

    // FOR RETURN ON ACTIVITY
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_info);

        // ALLOW BACK BUTTON TO BE DISPLAYED (VISUAL)
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // RECEIVE ITEM_ID FROM PREVIOUS ACTIVITY
//        String itemId = getIntent().getExtras().getString(PatrolListDataAdapter.ITEM_ID_KEY);
        // GET ITEM FROM MAP
//        PatrolProfile patrolProfile = StaticSampleData.samplePatrolMap.get(PatrolListDataAdapter.ITEM_KEY);

        // RECEIVE ITEM OBJECT FROM PREVIOUS ACTIVITY
        patrolProfile = getIntent().getExtras().getParcelable(PatrolListDataAdapter.ITEM_KEY);

        // SAFETY CODE
        if (patrolProfile != null) {
            Toast.makeText(this, "Received Item " + patrolProfile.getPATROL_NAME(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }

        // DISPLAY PATROL NAME & IMAGE ABOVE
        TextView tvPatrolName = (TextView) findViewById(R.id.tvPatrolName);
        tvPatrolName.setText(patrolProfile.getPATROL_NAME() + " Patrol");
        ImageView imgView = (ImageView) findViewById(R.id.ivPatrolIcon);

        // DISPLAY MEMBERLIST
        InputStream inputStream = null;
        try {
            // CHANGE THIS
            String imageFile = "patrolImg_" + patrolProfile.getPATROL_NAME() + ".png";
            inputStream = getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imgView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // CUSTOM ARRAY RECYCLERVIEWADAPTER WITH COURSE OBJECTS
        adapter = new MemberListDataAdapter(this, patrolProfile.getMemberProfileList());
        // Link a ListView to ListView widget
        recyclerView = (RecyclerView) findViewById(R.id.rvMemberList);
        recyclerView.setAdapter(adapter);


    }

    // CALLED WHEN CHILD FINISHES
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                MemberProfile memberProfile = data.getExtras().getParcelable("returnData");
                int index = patrolProfile.getIndex(memberProfile);
                if (index != -1) {
                    patrolProfile.getMemberProfileList().get(index).update(memberProfile);
                    patrolProfile.refreshPatrolDetails();
//                    Toast.makeText(this, "TOTAL POITS UPDATED: " + Integer.toString(patrolProfile.getPointSummary()), Toast.LENGTH_SHORT).show();
                    updateScreen();
                }
            }
        }
    }

    // ALLOWS BACK NAVIGATION TO PARENT ACTIVITY
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("returnData", patrolProfile);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        return true;
    }

    public void updateScreen() {
        recyclerView.setAdapter(adapter);
    }


}



