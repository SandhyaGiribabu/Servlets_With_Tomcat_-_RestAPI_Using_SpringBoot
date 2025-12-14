package com.example.todo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (!password.equals(confirm)) {
            out.println("Passwords do not match. <a href='register.html'>Go Back</a>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tododb","root","1234");

            // Check if username exists
            PreparedStatement psCheck = con.prepareStatement(
                "SELECT * FROM users WHERE username=?");
            psCheck.setString(1, username);
            ResultSet rs = psCheck.executeQuery();
            if(rs.next()) {
                out.println("Username already exists. <a href='register.html'>Go Back</a>");
                return;
            }

            // Insert user
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username,name,password,email,mobile) VALUES(?,?,?,?,?)");
            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.setString(4, email);
            ps.setString(5, mobile);
            ps.executeUpdate();

            out.println("Registration successful. <a href='login.html'>Login</a>");
            con.close();
        } catch(Exception e) {
            e.printStackTrace(out);
        }
    }
}
