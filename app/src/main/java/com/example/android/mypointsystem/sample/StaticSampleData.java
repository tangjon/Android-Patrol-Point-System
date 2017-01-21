package com.example.android.mypointsystem.sample;

import com.example.android.mypointsystem.model.PatrolProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangj on 1/2/2017.
 */

public class StaticSampleData {
    public static List<PatrolProfile> samplePatrolList;
    public static PatrolProfile patrol1;
    public static PatrolProfile patrol2;
    public static PatrolProfile patrol3;
    public static PatrolProfile patrol4;
    public static PatrolProfile patrol5;

    static final String PL = "PL";
    static final String APL = "APL";

    static {

        samplePatrolList = new ArrayList<>();

        patrol1 = new PatrolProfile("Wolf");
        patrol2 = new PatrolProfile("Panther");
        patrol3 = new PatrolProfile("Lion");
        patrol4 = new PatrolProfile("Tiger");
        patrol5 = new PatrolProfile("NoName");

        // Patrol 1
        patrol1.addMember("Vivian", "Padilla", patrol1.getPATROL_NAME(), PL);
        patrol1.addMember("Lana", "Goodwin",patrol1.getPATROL_NAME());
        patrol1.addMember("Jimmy", "Mccormick",patrol1.getPATROL_NAME());

        // Patrol 2
        patrol2.addMember("Freddie", "Mccormick",patrol2.getPATROL_NAME());
        patrol2.addMember("Carl", "Gonzalez",patrol2.getPATROL_NAME());
        patrol2.addMember("Tricia", "Osborne",patrol2.getPATROL_NAME());
        patrol2.addMember("Mandy", "Cruz",patrol2.getPATROL_NAME());

        // Patrol 3
        patrol3.addMember("Freddie", "Mccormick",patrol3.getPATROL_NAME());
        patrol3.addMember("Carl", "Gonzalez",patrol3.getPATROL_NAME());
        patrol3.addMember("Tricia", "Osborne",patrol3.getPATROL_NAME());
        patrol3.addMember("Mandy", "Cruz",patrol3.getPATROL_NAME());

        // Patrol 4
        patrol4.addMember("Freddie", "Mccormick",patrol4.getPATROL_NAME());
        patrol4.addMember("Carl", "Gonzalez",patrol4.getPATROL_NAME());
        patrol4.addMember("Tricia", "Osborne",patrol4.getPATROL_NAME());
        patrol4.addMember("Mandy", "Cruz",patrol4.getPATROL_NAME());

        // Patrol 5
        patrol5.addMember("Freddie", "Mccormick",patrol4.getPATROL_NAME());
        patrol5.addMember("Carl", "Gonzalez",patrol4.getPATROL_NAME());
        patrol5.addMember("Tricia", "Osborne",patrol4.getPATROL_NAME());
        patrol5.addMember("Mandy", "Cruz",patrol4.getPATROL_NAME());

        samplePatrolList.add(patrol1);
        samplePatrolList.add(patrol2);
        samplePatrolList.add(patrol3);
        samplePatrolList.add(patrol4);
        samplePatrolList.add(patrol5);
    }
}
