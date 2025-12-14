package CookieServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");
        StringBuilder html = new StringBuilder("<!DOCTYPE html><html><body>");

        // Check if user wants to change the color
        boolean reset = "true".equals(request.getParameter("reset"));

        // Retrieve favoriteColor cookie
        String favoriteColor = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                if(c.getName().equals("favoriteColor")) {
                    favoriteColor = c.getValue();
                    break;
                }
            }
        }

        if(favoriteColor != null && !reset) {
            // Cookie exists → display the color
            html.append("<h2>Your favorite color is <span style='color:")
                .append(favoriteColor)
                .append("'>")
                .append(favoriteColor)
                .append("</span>.</h2>");
            html.append("<p><a href='/ServletDemo/cookie?reset=true'>Change Color</a></p>");
        } else {
            // No cookie or reset requested → show input form
            html.append("<h2>Enter your favorite color:</h2>");
            html.append("<form action='/ServletDemo/cookie' method='post'>");
            html.append("<input type='text' name='color' required>");
            html.append("<input type='submit' value='Submit'>");
            html.append("</form>");
        }

        html.append("</body></html>");
        response.getWriter().print(html.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String color = request.getParameter("color");
        if(color != null && !color.isEmpty()) {
            Cookie colorCookie = new Cookie("favoriteColor", color);
            colorCookie.setMaxAge(24*60*60); // 24 hours
            response.addCookie(colorCookie);
        }

        // Redirect back to GET to display color
        response.sendRedirect("/ServletDemo/cookie");
    }
}
