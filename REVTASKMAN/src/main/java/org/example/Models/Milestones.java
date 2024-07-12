package org.example.Models;

public class Milestones {
    private int milestone_id;
    private String milestone_name;
    private String milestone_desc;

    public Milestones(int milestone_id, String milestone_name, String milestone_desc) {
        this.milestone_id = milestone_id;
        this.milestone_name = milestone_name;
        this.milestone_desc = milestone_desc;
    }
    public Milestones() {

    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getMilestone_name() {
        return milestone_name;
    }

    public void setMilestone_name(String milestone_name) {
        this.milestone_name = milestone_name;
    }

    public String getMilestone_desc() {
        return milestone_desc;
    }

    public void setMilestone_desc(String milestone_desc) {
        this.milestone_desc = milestone_desc;
    }
}
