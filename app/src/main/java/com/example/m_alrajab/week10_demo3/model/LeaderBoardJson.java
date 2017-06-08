package com.example.m_alrajab.week10_demo3.model;

/**
 * Created by Arthur on 4/5/2017.
 */

public class LeaderBoardJson {
    private String childName;
    private int timesGameBeaten;
    private String parentName;

    public LeaderBoardJson(){

    }

    public LeaderBoardJson(String childName, int timesGameBeaten, String parentName){
        this.childName = childName;
        this.timesGameBeaten = timesGameBeaten;
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }

    public int getTimesGameBeaten() {
        return timesGameBeaten;
    }

    public String getParentName() {
        return parentName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public void setTimesGameBeaten(int timesGameBeaten) {
        this.timesGameBeaten = timesGameBeaten;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
