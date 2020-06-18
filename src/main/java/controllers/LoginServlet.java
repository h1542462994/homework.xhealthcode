package controllers;

import dao.LoginViewModel;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.ValidateRule;
import ext.validation.ValidateRuleUnit;
import ext.validation.Validator;
import ext.validation.unit.ValidateRegion;
import ext.validation.unit.ValidateRequired;
import models.UserAccess;
import requests.UserLogin;
import services.IUserRepository;
import services.ServiceContainer;
import util.Web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers/LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginViewModel viewModel = new LoginViewModel();
        request.setAttribute("viewModel", viewModel);
        try {
            UserLogin login = new UserLogin();
            Validator.fill(login, request);  //获取request中的对应字段

            //TODO 完善表单验证规则
            if(login.type == 0 || login.type == 1){ //学生或者老师登录
                ValidateRule rule = new ValidateRule(
                    new ValidateRuleUnit("number",
                        new ValidateRequired(),
                        new ValidateRegion(6,20)),
                    new ValidateRuleUnit("name",
                        new ValidateRequired(),
                        new ValidateRegion(2,10)),
                    new ValidateRuleUnit("passport",
                        new ValidateRequired(),
                        new ValidateRegion(6,6))
                );
                rule.validate(login);
            } else { //管理员登录
                ValidateRule rule = new ValidateRule(
                    new ValidateRuleUnit("number",
                            new ValidateRequired(),
                            new ValidateRegion(6,20)),
                    new ValidateRuleUnit("passport",
                            new ValidateRequired(),
                            new ValidateRegion(6,20))
                );
                rule.validate(login);
            }

            IUserRepository repository = ServiceContainer.get().userRepository();
            UserAccess access = repository.login(login, response);

            if(access != null)
                Web.sendRedirect(response, this.getServletContext(), "");
            else {
                viewModel.setMsg(repository.getMsg());
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (IllegalAccessException | ServiceConstructException e) {
            e.printStackTrace();
        } catch (ValidateFailedException e){
            viewModel.setErrors(e.getMsg());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginViewModel viewModel = new LoginViewModel();
        request.setAttribute("viewModel", viewModel);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
