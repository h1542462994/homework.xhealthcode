package controllers.api;

import com.google.gson.Gson;
import ext.exception.ServiceConstructException;
import services.CollegeRepository;
import services.ICollegeRepository;
import services.ServiceContainer;
import util.Api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CollegeServlet", urlPatterns = "/api/college")
public class CollegeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if(action != null){
                ICollegeRepository collegeRepository = ServiceContainer.get().collegeRepository();
                if(action.equals("get")) {
                    Api.sendOK(response, collegeRepository.getColleges());
                }
            } else {
                Api.sendError(response, 403, "未指定action");
            }
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
