package controllers;

import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.validation.Validator;
import requests.UserAcquire;
import services.IHealthFeedback;
import services.ServiceContainer;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.DailyCardServlet", urlPatterns = "/dailycard")
public class DailyCardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            UserAcquire userAcquire = new UserAcquire();
            Validator.fill(userAcquire,request);


            IHealthFeedback healthFeedback = ServiceContainer.get().healthFeedback();

            healthFeedback.processingClock(userAcquire,request);


        } catch (IllegalAccessException | ServiceConstructException | OperationFailedException ie) {
            ie.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/dailycard.jsp").forward(request, response);
    }
}
