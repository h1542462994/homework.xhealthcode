package filters;

import dao.UserHandle;
import dao.UserInfo;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import services.IUserRepository;
import services.ServiceContainer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebFilter(filterName = "UserFilter", urlPatterns = "*")
public class UserFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean flag = true;
        try {

            IUserRepository repository = null;
            repository = ServiceContainer.get().userRepository();
            UserHandle handle = repository.getUser(request);
            if(handle != null){
                request.setAttribute("user", handle.getUserInfo());
            } else {
                request.setAttribute("user", null);
            }

            //
            ArrayList<String> redirectUrls = new ArrayList<>();
            redirectUrls.add("");
            redirectUrls.add("admin/user");
            redirectUrls.add("admin/college");

            System.out.println("servletPath: " + request.getServletPath());
            if(redirectUrls.contains(request.getServletPath()) && handle == null){
                response.sendRedirect("/login");
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
