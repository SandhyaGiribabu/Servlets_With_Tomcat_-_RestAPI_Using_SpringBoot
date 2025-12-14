package com.example.todo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/viewTasks")
public class ViewTasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String username = (String) session.getAttribute("username");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Task Dashboard</h1>");
        out.println("<p><a href='logout'>Logout</a></p>");

        // Form to add task
        out.println("<form action='addTask' method='post'>");
        out.println("Title: <input type='text' name='title' required>");
        out.println("Description: <input type='text' name='description'>");
        out.println("Status: <input type='text' name='status'>");
        out.println("Due Date: <input type='date' name='due_date'>");
        out.println("<input type='submit' value='Add Task'>");
        out.println("</form>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tododb","root","1234");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM tasks WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            out.println("<table border='1'><tr><th>Title</th><th>Description</th><th>Status</th><th>Due Date</th><th>Actions</th></tr>");
            while(rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("<td>" + rs.getDate("due_date") + "</td>");
                out.println("<td><a href='updateTask?task_id=" + rs.getInt("task_id") + "'>Update</a> | " +
                            "<a href='deleteTask?task_id=" + rs.getInt("task_id") + "'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            con.close();
        } catch(Exception e) {
            e.printStackTrace(out);
        }
    }
}
