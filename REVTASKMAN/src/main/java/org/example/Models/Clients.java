package org.example.Models;

public class Clients {
    private int client_id;
    private String client_name;
    private String client_info;
    private String client_email;

    public Clients(int client_id, String client_name, String client_info, String client_email) {
        this.client_id = client_id;
        this.client_name = client_name;
        this.client_info = client_info;
        this.client_email = client_email;
    }
    public Clients(){

    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_info() {
        return client_info;
    }

    public void setClient_info(String client_info) {
        this.client_info = client_info;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }
}


