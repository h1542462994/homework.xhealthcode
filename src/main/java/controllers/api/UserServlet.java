package controllers.api;

import ext.exception.ServiceConstructException;
import services.ICache;
import services.ServiceContainer;
import util.Api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="UserServlet", urlPatterns = "/api/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String type = request.getParameter("type");
            int index = Integer.getInteger(request.getParameter("index"), 0);
            ICache cache = ServiceContainer.get().cache();
            if (type == null)
                type = "teacher";
            if(type.equals("teacher")){
                Api.sendOK(response, cache.ofTeachers(index));
                return;
            }
            if(type.equals("student")){
                Api.sendOK(response, cache.ofStudents(index));
                return;
            }

            Api.sendError(response,403,"type异常");
            return;
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }
}
