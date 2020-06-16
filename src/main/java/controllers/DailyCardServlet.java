package controllers;

import com.google.gson.Gson;
import dao.DailyCardViewModel;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.ValidateRule;
import ext.validation.ValidateRuleUnit;
import ext.validation.Validator;
import ext.validation.unit.ValidateRegion;
import ext.validation.unit.ValidateRequired;
import models.DailyCard;
import requests.DailyCardAnswer;
import services.HealthFeedback;
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
        DailyCardViewModel dailyCardViewModel = new DailyCardViewModel();
        request.setAttribute("viewModel", dailyCardViewModel);

        try{
            DailyCardAnswer dailyCardAnswer = new DailyCardAnswer();
            Validator.fill(dailyCardAnswer,request);

            ValidateRule rule = new ValidateRule(
                    new ValidateRuleUnit("phone",
                            new ValidateRequired(),
                            new ValidateRegion(11,11))
            );
            rule.validate(dailyCardAnswer);

            IHealthFeedback healthFeedback = ServiceContainer.get().healthFeedback();
            healthFeedback.addToSql(healthFeedback.creatDailyCard(dailyCardAnswer,request));


        } catch (IllegalAccessException | ServiceConstructException | OperationFailedException ie) {
            ie.printStackTrace();
        } catch(ValidateFailedException e){
            dailyCardViewModel.setErrors(e.getMsg());
            request.getRequestDispatcher("/dailycard.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/dailycard.jsp").forward(request, response);
    }
}
