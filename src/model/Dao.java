package model;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {

    private Connection con;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/market";
    private String user = "Arthur";
    private String password = "Tuts2003!";

    public Connection connectionDb() {

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
