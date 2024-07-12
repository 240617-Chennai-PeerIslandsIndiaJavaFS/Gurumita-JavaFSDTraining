package org.example.Models;

import java.sql.Date;

public class Projects {
    private int project_id;
    private String project_name;
    private String project_desc;
    private int client_id;
    private int user_id;
    private Date start_date;
    private Date due_date;

    public Projects(int project_id, String project_name, String project_desc, int client_id, int user_id, Date start_date, Date due_date) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.project_desc = project_desc;
        this.client_id = client_id;
        this.user_id = user_id;
        this.start_date= start_date;
        this.due_date= due_date;
    }
    public Projects() {}

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_desc() {
        return project_desc;
    }

    public void setProject_desc(String project_desc) {
        this.project_desc = project_desc;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }
}
