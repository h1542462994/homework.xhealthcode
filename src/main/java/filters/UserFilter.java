package filters;

import com.google.gson.Gson;
import dao.ApiResponse;
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

@WebFilter(filterName = "UserFilter", urlPatterns = "*")
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
                    "",
                    "/admin/user",
                    "/admin/college"
            );

            //System.out.println("servletPath: " + request.getServletPath());
            if(matcher.matches(request.getServletPath()) && access == null){
                if(UrlMatcher.isApi(request.getServletPath())){
                    response.getWriter().write(new Gson().toJson(ApiResponse.userNoPass()));
                } else {
                    Web.sendRedirect(response, this.getServletContext(), "/login");
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
