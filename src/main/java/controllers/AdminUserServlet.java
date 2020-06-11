package controllers;

import dao.CollegeDao;
import dao.PathDao;
import ext.validation.Validator;
import dao.ResourceLocator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller/AdminUserServlet", urlPatterns = "/admin/user")
public class AdminUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ResourceLocator locator = new ResourceLocator();
            Validator.fill(locator, request);
            request.setAttribute("locator", locator);
            request.setAttribute("path", PathDao.fromLocator(locator));

            request.getRequestDispatcher("/admin_user.jsp").forward(request,response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
