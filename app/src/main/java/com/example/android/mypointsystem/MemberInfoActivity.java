package com.example.android.mypointsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mypointsystem.adapter.MemberListDataAdapter;
import com.example.android.mypointsystem.model.MemberProfile;

public class MemberInfoActivity extends AppCompatActivity {
    MemberProfile memberProfile;
    TextView memberScore;
    TextView memberName;
    TextView memberPosition;
    TextView memberPatrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        Button btnConfirm;

        // ALLOW BACK BUTTON TO BE DISPLAYED (VISUAL)
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // RECEIVE ITEM OBJECT FROM PREVIOUS ACTIVITY
        memberProfile = getIntent().getExtras().getParcelable(MemberListDataAdapter.ITEM_KEY);

        // SAFETY CODE
        if (memberProfile != null) {
            Toast.makeText(this, "Received Item " + memberProfile.getFull_name(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }

        // CREATE LINKS TO ANDROID OBJECTS
        memberName = (TextView) findViewById(R.id.tvMemberName);
        memberScore = (TextView) findViewById(R.id.tvPoints);
        memberPosition = (TextView) findViewById(R.id.tvMemberPos);
        memberPatrol = (TextView) findViewById(R.id.tvPatrolName);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);

        // UPDATE THE LINKS AND SCREEN
        updateScreen();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("returnData", memberProfile);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }


    // ALLOWS BACK NAVIGATION TO PARENT ACTIVITY
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    // UPDATES THE SCREEN AND LINKED ELEMENTS
    public void updateScreen() {
        memberPatrol.setText(memberProfile.getCurrent_patrol());
        memberName.setText(memberProfile.getFull_name());
        memberScore.setText(Integer.toString(memberProfile.getPoints()));
        memberPosition.setText(memberProfile.getPOSITION_NAME());
    }

    public void upVote(View v) {
        memberProfile.addPoints(1);
        updateScreen();
    }

    public void downVote(View v) {
        memberProfile.removePoint(1);
        updateScreen();
    }


}
