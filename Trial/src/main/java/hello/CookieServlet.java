package hello;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();

        String color = req.getParameter("color");
        if (color != null && !color.trim().isEmpty()) {
            Cookie c = new Cookie("favColor", color.trim());
            c.setMaxAge(24 * 60 * 60); // 24 hours
            res.addCookie(c);
            out.println("<h2>Your favorite color is " + color + ".</h2>");
            return;
        }

        // if no color param, try reading cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("favColor".equals(c.getName())) {
                    out.println("<h2>Your favorite color is " + c.getValue() + ".</h2>");
                    return;
                }
            }
        }
        out.println("<h2>No favorite color saved. Please enter it using the form.</h2>");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doPost(req,res);
    }
}
