package controllers.api;

import dao.UserDao;
import enums.RoleType;
import ext.exception.ServiceConstructException;
import ext.exception.ValidateFailedException;
import ext.validation.Validator;
import requests.UserRequest;
import services.ICache;
import dao.ResourceLocator;
import services.IUserRepository;
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
                Web.adminPass(request, RoleType.ALL, null);

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
            } else if(action.equals("add")) { //添加一条记录
                Web.adminPass(request, RoleType.SYSTEM, null);

                IUserRepository userRepository = ServiceContainer.get().userRepository();
                try {
                    UserRequest userRequest = Validator.assertValue(UserRequest.class, request);
                    boolean result = userRepository.insertOrUpdateUser(userRequest, -1);
                    if(result){
                        Web.sendOK(response, null);
                        return;
                    } else {
                        Web.sendError(response, 403, "插入错误");
                        return;
                    }
                } catch (ValidateFailedException e) {
                    Web.sendError(response, 403, "表单验证错误");
                    e.printStackTrace();
                    return;
                }
            } else if(action.equals("update")) {
                Web.adminPass(request, RoleType.SYSTEM, null);

                IUserRepository userRepository = ServiceContainer.get().userRepository();
                try {
                    UserRequest userRequest = Validator.assertValue(UserRequest.class, request);
                    long id = Long.parseLong(request.getParameter("id"));
                    boolean result = userRepository.insertOrUpdateUser(userRequest, id);
                    if(result){
                        Web.sendOK(response, null);
                        return;
                    } else {
                        Web.sendError(response, 403, "更新错误");
                        return;
                    }
                } catch (ValidateFailedException | NumberFormatException e){
                    Web.sendError(response, 403 , "表单验证错误");
                    e.printStackTrace();
                    return;
                }
            } else if(action.equals("delete")){
                Web.adminPass(request, RoleType.SYSTEM, null);

                IUserRepository userRepository = ServiceContainer.get().userRepository();
                String ids = request.getParameter("ids");
                //TODO 对ids的合法性进行验证
                if(ids == null){
                    Web.sendError(response, 403, "ids不符合要求");
                    return;
                }
                for (String id: ids.split(",")) {
                    userRepository.delete(Long.parseLong(id));
                }
                Web.sendOK(response,null);
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
