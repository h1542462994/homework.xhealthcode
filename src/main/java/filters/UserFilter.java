package filters;

import com.google.gson.Gson;
import com.google.protobuf.Api;
import dao.ApiResponse;
import dao.LoginViewModel;
import ext.exception.ServiceConstructException;
import models.UserAccess;
import services.IUserRepository;
import services.ServiceContainer;
import util.Web;
import util.UrlMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.sql.Date;

public class UserFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean flag = true;
        try {
            IUserRepository repository = ServiceContainer.get().userRepository();
            UserAccess access = repository.active(request);
            if(access != null){
                request.setAttribute("user", repository.get(access.getUserId()));
            } else {
                request.setAttribute("user", null);
            }

            // TODO 完善访问权限检查系统
            UrlMatcher matcher = new UrlMatcher(
                    "", //首页
                    //"/api.*", //所有的api目录
                    "/admin.*", //所有管理系统
                    "/acquire.*",
                    "/dailycard.*",
                    "/healthcode.*",
                    "/user.*",
                    "/logout.*"
            );

            //System.out.println("servletPath: " + request.getServletPath());
            if(matcher.matches(request.getServletPath()) && access == null){
                if(UrlMatcher.isApi(request.getServletPath())){
                    Web.sendError(response, 405, "用户未登录");
                } else {
                    LoginViewModel viewModel = new LoginViewModel();
                    request.setAttribute("viewModel", viewModel);
                    viewModel.setMsg("你只有登录才能访问该资源");
                    viewModel.setRedirectUrl(request.getServletPath());
                    Web.page(response, request, getServletContext(), "/login.jsp");
                }
                flag = false;
            }
        } catch (ServiceConstructException e) {
            e.printStackTrace();
        } finally {
            if(flag){
                chain.doFilter(request, response);
            }
        }
    }
}
