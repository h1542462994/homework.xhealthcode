package controllers;

import dao.UserHandle;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.Validator;
import requests.UserLogin;
import services.IUserRepository;
import services.ServiceContainer;

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
        try {
            UserLogin login = Validator.assertValue(UserLogin.class, request);
            IUserRepository repository = ServiceContainer.get().userRepository();
            UserHandle handle = repository.adminLogin(login, response);
            if(handle != null){
                response.sendRedirect("/");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (ValidateFailedException | ServiceConstructException e) {
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
