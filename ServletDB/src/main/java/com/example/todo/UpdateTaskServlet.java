package com.example.todo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/updateTask")
public class UpdateTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int taskId = Integer.parseInt(request.getParameter("task_id"));
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tododb","root","1234");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM tasks WHERE task_id=?");
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                out.println("<form action='updateTask' method='post'>");
                out.println("<input type='hidden' name='task_id' value='" + taskId + "'>");
                out.println("Title: <input type='text' name='title' value='" + rs.getString("title") + "'><br>");
                out.println("Description: <input type='text' name='description' value='" + rs.getString("description") + "'><br>");
                out.println("Status: <input type='text' name='status' value='" + rs.getString("status") + "'><br>");
                out.println("Due Date: <input type='date' name='due_date' value='" + rs.getDate("due_date") + "'><br>");
                out.println("<input type='submit' value='Update'>");
                out.println("</form>");
            }
            con.close();
        } catch(Exception e) {
            e.printStackTrace(out);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int taskId = Integer.parseInt(request.getParameter("task_id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String dueDate = request.getParameter("due_date");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/tododb","root","1234");

            PreparedStatement ps = con.prepareStatement(
                "UPDATE tasks SET title=?, description=?, status=?, due_date=? WHERE task_id=?");
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, status);
            ps.setDate(4, java.sql.Date.valueOf(dueDate));
            ps.setInt(5, taskId);
            ps.executeUpdate();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("viewTasks");
    }
}
