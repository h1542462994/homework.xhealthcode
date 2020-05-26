package controllers;

import dao.UserHandle;
import ext.exception.ServiceConstructException;
import services.ServiceContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers/LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserHandle handle = ServiceContainer.get().userRepository().getUser(request);
            if(handle != null){
                handle.getAccess().setExpired(null);
                response.addCookie(new Cookie("_token", null));
            }

            response.sendRedirect("/login");
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }
}
