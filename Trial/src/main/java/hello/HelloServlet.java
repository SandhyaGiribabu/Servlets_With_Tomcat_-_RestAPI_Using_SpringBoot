package hello;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!doctype html><html><body>");
        out.println("<h2>Hello, welcome to Servlet Programming!</h2>");
        out.println("</body></html>");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req,res);
    }
}
