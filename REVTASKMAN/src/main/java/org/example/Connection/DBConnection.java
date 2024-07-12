package org.example.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/revtaskmanagement";
        String user = "root";
        String password = "Gu20ru03";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,user,password);
            return conn;
        }catch (ClassNotFoundException | SQLException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}