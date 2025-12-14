package com.example.todo;
import java.sql.*;

public class jdbc {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tododb"; // your DB
        String user = "root"; // your DB username
        String pass = "1234"; // your DB password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database connected successfully!");
            con.close();
            
        } catch(Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }
}
