package controllers;

import dao.AcquireViewModel;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.ValidateRule;
import ext.validation.ValidateRuleUnit;
import ext.validation.Validator;
import ext.validation.unit.ValidateRegion;
import ext.validation.unit.ValidateRequired;
import requests.UserAcquire;
import services.IHealthFeedback;
import services.ServiceContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller/AcquireServlet", urlPatterns = "/acquire")
public class AcquireServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AcquireViewModel acquireViewModel = new AcquireViewModel();
        request.setAttribute("viewModel", acquireViewModel);

        try{
            UserAcquire userAcquire = new UserAcquire();

            Validator.fill(userAcquire,request);


            ValidateRule rule = new ValidateRule(
                    new ValidateRuleUnit("phone",
                            new ValidateRequired(),
                            new ValidateRegion(11,11))
            );
            rule.validate(userAcquire);

            IHealthFeedback healthFeedback = ServiceContainer.get().healthFeedback();

            healthFeedback.processingAcquire(userAcquire,request);

        } catch (IllegalAccessException | ServiceConstructException | OperationFailedException ie) {
            ie.printStackTrace();
        } catch(ValidateFailedException e){
            acquireViewModel.setErrors(e.getMsg());
            request.getRequestDispatcher("/acquire.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AcquireDoGet");
        request.getRequestDispatcher("/acquire.jsp").forward(request, response);
    }
}
