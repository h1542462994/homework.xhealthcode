package filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
        ArrayList<String> cookiesString = new ArrayList<>();
        for (Cookie cookie: req.getCookies()) {
            cookiesString.add(cookie.getName() + "=" + cookie.getValue());
        }
        System.out.println("Access: url=" + req.getRequestURL() + " servlet=" + req.getHttpServletMapping().getServletName() + " sessionId=" + req.getRequestedSessionId() + " cookies={" + Arrays.toString(cookiesString.toArray()) +"}" );
        chain.doFilter(req, res);

    }

}
