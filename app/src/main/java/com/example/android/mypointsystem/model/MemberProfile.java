package com.example.android.mypointsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tangj on 1/2/2017.
 */

public class MemberProfile implements Parcelable {
    private String FIRST_NAME;
    private String LAST_NAME;
    private String FULL_NAME;
    private String PATROL_NAME;
    private String POSITION_NAME;
    private int points;

    // DEFAULT CONSTRUCTOR
    public MemberProfile(String firstName, String lastName, String patrolName) {
        FIRST_NAME = firstName;
        LAST_NAME = lastName;
        PATROL_NAME = patrolName;
        POSITION_NAME = "MEMBER";
        points = 0;
        updateFullName();
    }

    public MemberProfile(String firstName, String lastName, String patrolName, String posName) {
        FIRST_NAME = firstName;
        LAST_NAME = lastName;
        PATROL_NAME = patrolName;
        POSITION_NAME = posName;
        points = 0;
        updateFullName();
    }

    public void update(MemberProfile someProfile) {
        FIRST_NAME = someProfile.getFirst_name();
        LAST_NAME = someProfile.getLast_name();
        FULL_NAME = someProfile.getFull_name();
        PATROL_NAME = someProfile.getCurrent_patrol();
        POSITION_NAME = someProfile.getPOSITION_NAME();
        points = someProfile.getPoints();
    }

    public String getPOSITION_NAME() {
        return POSITION_NAME;
    }

    public void setPOSITION_NAME(String POSITION_NAME) {
        this.POSITION_NAME = POSITION_NAME;
    }

    // ADD POINTS TO MEMBER & REMOVE
    public void addPoints(int newPoints) {
        points += newPoints;
    }

    public void removePoint(int newPoints) {
        points -= newPoints;
    }

    // RESET POINTS TO 0
    public void resetPoints() {
        points = 0;
    }

    // SETTERS AND GETTERS
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getFirst_name() {
        return FIRST_NAME;
    }

    public void setFirst_name(String first_name) {
        this.FIRST_NAME = first_name;
        updateFullName();
    }

    public String getLast_name() {
        return LAST_NAME;
    }

    public void setLast_name(String last_name) {
        this.LAST_NAME = last_name;
        updateFullName();
    }

    public String getFull_name() {
        return FULL_NAME;
    }

    public String getCurrent_patrol() {
        return PATROL_NAME;
    }

    public void setCurrent_patrol(String current_patrol) {
        this.PATROL_NAME = current_patrol;
    }

    private void updateFullName() {
        FULL_NAME = FIRST_NAME + " " + LAST_NAME;
    }


    @Override
    public String toString() {
        return "MemberProfile{" +
                "FIRST_NAME='" + FIRST_NAME + '\'' +
                ", LAST_NAME='" + LAST_NAME + '\'' +
                ", FULL_NAME='" + FULL_NAME + '\'' +
                ", PATROL_NAME='" + PATROL_NAME + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.FIRST_NAME);
        dest.writeString(this.LAST_NAME);
        dest.writeString(this.FULL_NAME);
        dest.writeString(this.PATROL_NAME);
        dest.writeString(this.POSITION_NAME);
        dest.writeInt(this.points);
    }

    protected MemberProfile(Parcel in) {
        this.FIRST_NAME = in.readString();
        this.LAST_NAME = in.readString();
        this.FULL_NAME = in.readString();
        this.PATROL_NAME = in.readString();
        this.POSITION_NAME = in.readString();
        this.points = in.readInt();
    }

    public static final Creator<MemberProfile> CREATOR = new Creator<MemberProfile>() {
        @Override
        public MemberProfile createFromParcel(Parcel source) {
            return new MemberProfile(source);
        }

        @Override
        public MemberProfile[] newArray(int size) {
            return new MemberProfile[size];
        }
    };
}
