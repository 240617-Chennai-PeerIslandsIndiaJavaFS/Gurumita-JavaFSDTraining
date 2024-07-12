package org.example.Models;

public class Users {
    private int user_id;
    private String name;
    private String email_id;
    private String password;
    private Role role;
    private Status status;

    public int getUserId() {
        return 0;
    }

    public enum Role {
        ADMIN,PROJECT_MANAGER, TEAM_MEMBER
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public Users(int user_id, String name, String email_id) {
        this.user_id = user_id;
        this.name = name;
        this.email_id = email_id;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public Users(String name, String emailId, String password, Role role, Status status) {
        this.name = name;
        this.email_id = emailId;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public Users() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}