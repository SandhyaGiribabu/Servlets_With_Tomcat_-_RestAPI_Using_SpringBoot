package HelloServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Hello
 */
public class Hello extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get username from request parameter (?username=...)
        String username = request.getParameter("username");

        response.setContentType("text/html;charset=UTF-8");
        if (username == null || username.isEmpty()) {
            response.getWriter().println("<h2>No username provided!</h2>");
        } else {
            response.getWriter().println("<h2>Welcome, " + username + "!</h2>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
