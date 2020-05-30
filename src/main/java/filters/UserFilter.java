package filters;

import com.google.gson.Gson;
import dao.ApiResponse;
import dao.UserHandle;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import services.IUserRepository;
import services.ServiceContainer;
import util.UrlMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UserFilter", urlPatterns = "*")
public class UserFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean flag = true;
        try {
            IUserRepository repository = ServiceContainer.get().userRepository();
            UserHandle handle = repository.getUser(request);
            if(handle != null){
                request.setAttribute("user", handle.getUserInfo());
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
            if(matcher.matches(request.getServletPath()) && handle == null){
                if(UrlMatcher.isApi(request.getServletPath())){
                    response.getWriter().write(new Gson().toJson(ApiResponse.userNoPass()));
                } else {
                    response.sendRedirect("/login");
                }
                flag = false;
            }
        } catch (ServiceConstructException | OperationFailedException e) {
            e.printStackTrace();
        } finally {
            if(flag){
                chain.doFilter(request, response);
            }
        }
    }
}
