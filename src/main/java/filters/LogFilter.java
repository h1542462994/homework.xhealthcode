package filters;

import dao.UserHandle;
import dao.UserInfo;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import services.IUserRepository;
import services.ServiceContainer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

@WebFilter(filterName = "LogFilter", urlPatterns = "*")
public class LogFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("Access: url=" + req.getRequestURL() + " method=" + req.getMethod());
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        try {
            IUserRepository repository = null;
            repository = ServiceContainer.get().userRepository();
            UserHandle handle = repository.getUser(req);
            if(handle != null){
                req.setAttribute("user", handle.getUserInfo());
            } else {
                req.setAttribute("user", new UserInfo());
            }
        } catch (ServiceConstructException | OperationFailedException e) {
            e.printStackTrace();
        }

        chain.doFilter(req, res);
    }

}
