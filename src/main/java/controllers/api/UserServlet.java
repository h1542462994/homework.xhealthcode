package controllers.api;

import dao.UserDao;
import ext.exception.ServiceConstructException;
import ext.validation.Validator;
import services.ICache;
import dao.ResourceLocator;
import services.ServiceContainer;
import util.Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name="UserServlet", urlPatterns = "/api/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null){
                Web.sendError(response, 403, "action为空");
                return;
            }
            if(action.equals("get")){
                ResourceLocator locator = new ResourceLocator();
                Validator.fill(locator, request);
                ICache cache = ServiceContainer.get().cache();
                ArrayList<UserDao> resultPageDao = cache.getUserResultOfLocator(locator);
                if (resultPageDao == null){
                    Web.sendError(response, 403,"locator错误");
                    return;
                }
                Web.sendOK(response, resultPageDao);
                return;
            }

            Web.sendError(response, 403, "当前action不支持");
            return;
        } catch (IllegalAccessException | ServiceConstructException e) {
            Web.sendError(response, 403, "未知的异常");
            e.printStackTrace();
        }
    }
}
