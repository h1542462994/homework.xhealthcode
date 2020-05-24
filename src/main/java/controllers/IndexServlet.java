package controllers;

import dao.UserHandle;
import ext.exception.ServiceConstructException;
import services.IUserRepository;
import services.ServiceContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controllers/IndexServlet", urlPatterns = "")
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IUserRepository repository = ServiceContainer.get().userRepository();
            UserHandle handle = repository.getUser(request);
            if(handle == null) //当前用户还没有登录
            {
                response.sendRedirect("/login");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }

        } catch (ServiceConstructException e) {
            e.printStackTrace();
        }
    }
}
