package org.example.Models;

public class Timestamp {
    private int timestamp_id;
    private int user_id;
    private int milestone_id;

    public Timestamp(int timestamp_id, int user_id, int milestone_id) {
        this.timestamp_id = timestamp_id;
        this.user_id = user_id;
        this.milestone_id = milestone_id;
    }
    public Timestamp() {

    }

    public int getTimestamp_id() {
        return timestamp_id;
    }

    public void setTimestamp_id(int timestamp_id) {
        this.timestamp_id = timestamp_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }
}
