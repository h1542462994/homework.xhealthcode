package filters;


import dao.ViewModel;
import ext.exception.RoleNotSupportedException;
import util.UrlMatcher;
import util.Web;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (ServletException e){
            if(e.getRootCause() instanceof RoleNotSupportedException){
                if(UrlMatcher.isApi(request.getServletPath())){
                    Web.sendError(response, 403, "你没有相应的管理员权限" + e.getMessage());
                    return;
                } else {
                    ViewModel viewModel = new ViewModel();
                    viewModel.setMsg("你并没有访问该页面的权限！");
                    request.setAttribute("viewModel", viewModel);
                    Web.page(response, request, getServletContext(), "/index.jsp");
                }
            }
        }
    }
}
