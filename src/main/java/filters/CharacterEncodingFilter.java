package filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CharacterEncodingFilter extends HttpFilter {
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        System.out.println("已过滤字符串编码");
        chain.doFilter(request, response);

        response.setCharacterEncoding("UTF-8");
    }
}
