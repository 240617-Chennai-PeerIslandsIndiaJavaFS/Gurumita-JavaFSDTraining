package org.example;

import org.example.Service.UserService;
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        UserApp userApp = new UserApp(userService);
        userApp.start();
    }
}


