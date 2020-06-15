package controllers;

import com.google.gson.Gson;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.Validator;
import requests.DailyCardAnswer;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers.DailyCardServlet", urlPatterns = "/dailycard")
public class DailyCardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("getdailydata");
        try{
            DailyCardAnswer dailyCardAnswer = new DailyCardAnswer();
            Validator.fill(dailyCardAnswer,request);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/dailycard.jsp").forward(request, response);
    }
}
