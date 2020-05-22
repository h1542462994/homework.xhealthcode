import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.Validator;
import requests.UserLogin;
import services.DbContext;
import models.User;
import services.ServiceContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestServlet", urlPatterns = "")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            DbContext context = ServiceContainer.get().dbContext();
            UserLogin userLogin = Validator.assertValue(UserLogin.class, request);


            response.getWriter().write(userLogin.name + " " + userLogin.passport + "\n");

            for (User user: context.Users.query("nick_name = ?", "hello world")) {
                response.getWriter().write(user.getUserId() + " " + user.getEmail() + "\n");
            }


        } catch (NullPointerException | ServiceConstructException | OperationFailedException e) {
            response.getWriter().write("error");
        } catch (ValidateFailedException e){
            e.printStackTrace();
            response.getWriter().write(e.getMsg().toString());
        }

    }
}
