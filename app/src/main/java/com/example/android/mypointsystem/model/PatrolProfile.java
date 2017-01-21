package com.example.android.mypointsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tangj on 1/2/2017.
 */

public class PatrolProfile implements Parcelable {
    private String PATROL_NAME;
    private int numberOfMembers;
    private List<MemberProfile> memberProfileList;
    private int pointSummary = 0;


    public PatrolProfile(String patrolName) {
        this.PATROL_NAME = patrolName;
        numberOfMembers = 0;
        this.memberProfileList = new ArrayList<>();
    }

    // ADD & REMOVE MEMBERS
    public void addMember(String firstName, String lastName, String patrolName, String posName) {
        MemberProfile newMember = new MemberProfile(firstName, lastName, patrolName, posName);
        numberOfMembers++;
        memberProfileList.add(newMember);
    }

    public void addMember(String firstName, String lastName, String patrolName) {
        MemberProfile newMember = new MemberProfile(firstName, lastName, patrolName);
        numberOfMembers++;
        memberProfileList.add(newMember);
    }

    public void addMember(String fullName, String patrolName) {
        MemberProfile newMember = new MemberProfile(parseFirst(fullName), parseLast(fullName), patrolName);
        numberOfMembers++;
        memberProfileList.add(newMember);
    }

    public boolean removeMember(String fullName) {
        for (int i = 0; i < numberOfMembers; i++) {
            if (fullName.equals(memberProfileList.get(i).getFull_name())) {
                memberProfileList.remove(i);
                numberOfMembers--;
                return true;
            }
        }
        return false;
    }


    public int getIndex(MemberProfile someMember) {
        for (int i = 0; i < numberOfMembers; i++) {
            if (someMember.getFull_name().equals(memberProfileList.get(i).getFull_name())) {
                return i;
            }
        }
        return -1;
    }

    public String getPATROL_NAME() {
        return PATROL_NAME;
    }

    public void setPATROL_NAME(String PATROL_NAME) {
        this.PATROL_NAME = PATROL_NAME;
    }

    public List<MemberProfile> getMemberProfileList() {
        return memberProfileList;
    }

    public void setMemberProfileList(List<MemberProfile> memberProfileList) {
        this.memberProfileList = memberProfileList;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void refreshPatrolDetails() {
        updatePatrolPoints();
    }

    private void updatePatrolPoints() {
        int sum = 0;
        for (int i = 0; i < numberOfMembers; i++) {
            sum += memberProfileList.get(i).getPoints();
        }
        pointSummary = sum;
    }

    public int getPointSummary() {
        return pointSummary;
    }

    public void setPointSummary(int pointSummary) {
        this.pointSummary = pointSummary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PATROL_NAME);
        dest.writeInt(this.numberOfMembers);
        dest.writeTypedList(this.memberProfileList);
        dest.writeInt(this.pointSummary);
    }

    protected PatrolProfile(Parcel in) {
        this.PATROL_NAME = in.readString();
        this.numberOfMembers = in.readInt();
        this.memberProfileList = in.createTypedArrayList(MemberProfile.CREATOR);
        this.pointSummary = in.readInt();
    }

    public static final Creator<PatrolProfile> CREATOR = new Creator<PatrolProfile>() {
        @Override
        public PatrolProfile createFromParcel(Parcel source) {
            return new PatrolProfile(source);
        }

        @Override
        public PatrolProfile[] newArray(int size) {
            return new PatrolProfile[size];
        }
    };

    private String parseFirst(String name){
        int index = name.indexOf(" ");
        return name.substring(0,index-1);
    }
    private String parseLast(String name){
        int index = name.indexOf(" ");
        return name.substring(index+1,name.length());
    }
}

