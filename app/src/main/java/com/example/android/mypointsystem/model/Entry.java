package com.example.android.mypointsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.mypointsystem.MainActivity;
import com.example.android.mypointsystem.sample.StaticSampleData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by tangj on 1/5/2017.
 */

public class Entry implements Parcelable {
    private String ENTRY_NAME;
    private String DATE;
    private List<PatrolProfile> patrolList;
    private String itemId;

    public Entry(String newEntry){
        this.itemId = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.DATE = dateFormat.format(date);
        this.ENTRY_NAME = newEntry;
        this.patrolList = StaticSampleData.samplePatrolList;
    }

    public Entry(String newEntry, List<PatrolProfile> newList){
        this.itemId = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.DATE = dateFormat.format(date);
        this.ENTRY_NAME = newEntry;
        this.patrolList = newList;
    }

    public Entry(){
        this.itemId = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.DATE = dateFormat.format(date);
        this.ENTRY_NAME = this.DATE;
        this.patrolList = StaticSampleData.samplePatrolList;
    }

    public Entry(List<PatrolProfile> newList){
        this.itemId = UUID.randomUUID().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.DATE = dateFormat.format(date);
        this.ENTRY_NAME = this.DATE;
        this.patrolList = newList;
    }

    public String getItemId() {
        return itemId;
    }

    public String getENTRY_NAME() {
        return ENTRY_NAME;
    }

    public void setENTRY_NAME(String ENTRY_NAME) {
        this.ENTRY_NAME = ENTRY_NAME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public List<PatrolProfile> getPatrolList() {
        return patrolList;
    }

    public void setPatrolList(List<PatrolProfile> patrolList) {
        this.patrolList = patrolList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ENTRY_NAME);
        dest.writeString(this.DATE);
        dest.writeTypedList(this.patrolList);
        dest.writeString(this.itemId);
    }

    protected Entry(Parcel in) {
        this.ENTRY_NAME = in.readString();
        this.DATE = in.readString();
        this.patrolList = in.createTypedArrayList(PatrolProfile.CREATOR);
        this.itemId = in.readString();
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel source) {
            return new Entry(source);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}





