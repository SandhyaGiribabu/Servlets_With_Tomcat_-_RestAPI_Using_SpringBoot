package hello;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(true);
        String name = req.getParameter("username");
        if (name != null && !name.trim().isEmpty()) {
            session.setAttribute("user", name.trim());
        }
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String user = (String) session.getAttribute("user");
        if (user != null) {
            out.println("<h2>Welcome back, " + user + "! Your Session ID is: " + session.getId() + "</h2>");
        } else {
            out.println("<h2>No name in session. Submit the form.</h2>");
        }
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doPost(req,res);
    }
}
