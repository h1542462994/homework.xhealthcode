package controllers;

import ext.exception.ServiceConstructException;
import services.ServiceContainer;
import util.Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers/LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServiceContainer.get().userRepository().logout(request, response);
            Web.sendRedirect(response, getServletContext(), "/login");
            //response.sendRedirect("/login");
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }
}
