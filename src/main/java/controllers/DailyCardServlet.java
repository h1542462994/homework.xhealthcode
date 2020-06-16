package controllers;

import dao.UserDao;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.validation.Validator;
import models.UserAccess;
import requests.UserAcquire;
import services.IHealthFeedback;
import services.IUserRepository;
import services.ServiceContainer;
import util.Web;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "controllers.DailyCardServlet", urlPatterns = "/dailycard")
public class DailyCardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserAcquire userAcquire = new UserAcquire();
            Validator.fill(userAcquire,request);

            IHealthFeedback healthFeedback = ServiceContainer.get().healthFeedback();

            healthFeedback.processingClock(userAcquire,request);
//            healthFeedback.test(userAcquire,request);

        } catch (IllegalAccessException | ServiceConstructException | OperationFailedException ie) {
            ie.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IUserRepository repository = null;
        try {
            repository = ServiceContainer.get().userRepository();
            UserAccess access = repository.active(request);
            UserDao user= repository.get(access.getUserId());

            Date date = new Date(System.currentTimeMillis());
            if(user.getDate().toString().equals(date.toString())){
                Web.sendRedirect(response, this.getServletContext(), "/");
            }else{
                request.getRequestDispatcher("/dailycard.jsp").forward(request, response);
            }
        }
        catch (ServiceConstructException e) {
            e.printStackTrace();
        }

    }
}
