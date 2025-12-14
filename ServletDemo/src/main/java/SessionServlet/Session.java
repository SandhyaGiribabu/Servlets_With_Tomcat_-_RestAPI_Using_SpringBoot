package SessionServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Session extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        HttpSession session = request.getSession(); // create/get session
        
        String username = (String) session.getAttribute("username"); // check existing user
        String htmlResponse = "<!DOCTYPE html><html><body>";
        
        if(username == null) {
            // First visit, show form
            htmlResponse += "<h2>Enter your name to start a session</h2>";
            htmlResponse += "<form action='/ServletDemo/session' method='post'>";
            htmlResponse += "<label>Name:</label>";
            htmlResponse += "<input type='text' name='username' required>";
            htmlResponse += "<input type='submit' value='Submit'>";
            htmlResponse += "</form>";
        } else {
            // Returning user
            htmlResponse += "<h2>Welcome back, " + username + "!</h2>";
            htmlResponse += "<p>Your Session ID is: " + session.getId() + "</p>";
            htmlResponse += "<p><a href='/ServletDemo/session?logout=true'>Logout</a></p>";
        }
        
        htmlResponse += "</body></html>";
        response.getWriter().print(htmlResponse);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        
        if(username != null && !username.isEmpty()) {
            session.setAttribute("username", username); // store in session
        }
        
        // Redirect to GET to display welcome message
        response.sendRedirect("/ServletDemo/session");
    }
}
