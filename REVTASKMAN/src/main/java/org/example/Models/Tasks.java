package org.example.Models;

public class Tasks {
    public void setTask_status(org.example.Models.Taskstatus taskStatus) {
    }

    public enum Taskstatus {
        STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    private int task_id;
    private String task_name;
    private String task_desc;
    private Taskstatus task_status;
    private int project_id;
    private int assigned_user_id;
    private int milestone_id;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public Taskstatus getTask_status() {
        return task_status;
    }

    public void setTask_status(Taskstatus task_status) {
        this.task_status = task_status;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getAssigned_user_id() {
        return assigned_user_id;
    }

    public void setAssigned_user_id(int assigned_user_id) {
        this.assigned_user_id = assigned_user_id;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }
}
