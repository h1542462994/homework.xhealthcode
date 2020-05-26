package controllers;

import dao.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "controller/AdminController", urlPatterns = "/admin/*")
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserInfo user = (UserInfo) req.getAttribute("user");
        if(!user.isAdmin()){ //不是管理员无法访问
            resp.sendRedirect("/");
        } else {
            if(req.getPathInfo() == null){
                req.getRequestDispatcher("/admin/user").forward(req,resp);
            } else {
                req.getRequestDispatcher("/admin" + req.getPathInfo()).forward(req, resp);
            }
        }
    }
}
