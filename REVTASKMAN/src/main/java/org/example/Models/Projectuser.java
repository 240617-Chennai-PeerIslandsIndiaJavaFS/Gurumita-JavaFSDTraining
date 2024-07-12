package org.example.Models;

public class Projectuser {
    private int project_user_id;
    private int project_id;
    private int user_id;
    private String role_in_project;
    private boolean is_active;
    private int manager_id;

    public Projectuser(int project_user_id, int project_id, int user_id, String role_in_project, boolean is_active, int manager_id) {
        this.project_user_id = project_user_id;
        this.project_id = project_id;
        this.user_id = user_id;
        this.role_in_project = role_in_project;
        this.is_active = is_active;
        this.manager_id = manager_id;
    }
    public Projectuser() {
    }

    public int getProject_user_id() {
        return project_user_id;
    }

    public void setProject_user_id(int project_user_id) {
        this.project_user_id = project_user_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRole_in_project() {
        return role_in_project;
    }

    public void setRole_in_project(String role_in_project) {
        this.role_in_project = role_in_project;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }
}






