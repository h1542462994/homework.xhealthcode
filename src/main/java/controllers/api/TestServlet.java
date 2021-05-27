package controllers.api;

import util.Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestServlet", urlPatterns = "/api/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("输出中文:");
        String input = request.getParameter("input");
        System.out.println("输入内容:"  + input);
        Web.sendOK(response, "输入内容:" + input);
    }
}
