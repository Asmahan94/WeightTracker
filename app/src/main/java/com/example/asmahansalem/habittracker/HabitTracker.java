package com.example.asmahansalem.habittracker;

/**
 * Created by Asmahan Salem on 9/19/2017.
 */

public class HabitTracker {

    private int id;
    private String weight;
    private String loss;
    private long date;
    private String water;

    public HabitTracker(int id, String weight, String loss, long date, String water) {
        this.id = id;
        this.weight = weight;
        this.loss = loss;
        this.date = date;
        this.water = water;
    }

    public HabitTracker(String weight, String loss, long date, String water) {
        this.weight = weight;
        this.loss = loss;
        this.date = date;
        this.water = water;
    }

    public int getId() {
        return id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String name) {
        this.weight = name;
    }

    public String getLoss() {
        return loss;
    }

    public long getDate() {
        return date;
    }

    public String getWater() {
        return water;
    }

}
