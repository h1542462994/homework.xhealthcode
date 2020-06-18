package controllers;

import dao.LoginViewModel;
import dao.UserDao;
import dao.ViewModel;
import util.Web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
        UserDao info = (UserDao)request.getAttribute("user");
        if(info == null) //当前用户还没有登录
        {
            Web.sendRedirect(response, getServletContext(), "/login");
        } else {
            ViewModel viewModel = new ViewModel();
            request.setAttribute("viewModel", viewModel);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

    }
}
