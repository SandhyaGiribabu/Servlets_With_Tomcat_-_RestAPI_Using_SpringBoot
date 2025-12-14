package com.example.todo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String username = (String) session.getAttribute("username");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String dueDate = request.getParameter("due_date"); // yyyy-mm-dd

        try {
            // Connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tododb", "root", "1234");

            // Insert the new task
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO tasks(username,title,description,status,due_date) VALUES(?,?,?,?,?)");
            ps.setString(1, username);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, status);
            if (dueDate != null && !dueDate.isEmpty()) {
                ps.setDate(5, java.sql.Date.valueOf(dueDate));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }

            ps.executeUpdate();
            con.close();

            // Redirect to viewTasks to reload the page and show updated table
            response.sendRedirect("viewTasks");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<p style='color:red'>Error adding task!</p>");
        }
    }

    // Optional: handle GET gracefully
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("viewTasks");
    }
}
