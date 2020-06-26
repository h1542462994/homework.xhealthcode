package util;

import com.google.gson.Gson;
import dao.ApiResponse;
import dao.UserDao;
import enums.RoleType;
import enums.TypeType;
import ext.exception.RoleNotSupportedException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Objects;

public class Web {
    public static <T> void sendOK(HttpServletResponse response, T object) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(new Gson().toJson(new ApiResponse<>(200,"ok",object) ));
    }

    public static <T> void sendError(HttpServletResponse response, int code, String msg) throws IOException{
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(code);
        response.getWriter().write(new Gson().toJson(new ApiResponse<>(code, msg, null)));
    }

    public static <T> void sendRedirect(HttpServletResponse response, ServletContext context, String url) throws IOException {
        response.sendRedirect(context.getContextPath() + url);

    }

    public static <T> void page(HttpServletResponse response, HttpServletRequest request, ServletContext context, String url) throws ServletException, IOException {
        context.getRequestDispatcher(url).forward(request, response);
    }

    public static <T> void sendFile(HttpServletResponse response, ServletContext context, String fileName) throws IOException {
        File file = new File(context.getRealPath(fileName));
        if(!file.exists()){
            response.sendError(403, "文件不存在");
        }
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "utf-8"));
        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    public static <T> void adminPass(HttpServletRequest request, int roleType, Object tag) throws ServletException {
        UserDao userDao = (UserDao) request.getAttribute("user");
        if(userDao == null || userDao.getType() != TypeType.ADMIN){
            throw new ServletException(new RoleNotSupportedException());
        }

        if(roleType == RoleType.SYSTEM || roleType == RoleType.SCHOOL){
            if(userDao.getAdminType() < roleType){
                throw new ServletException(new RoleNotSupportedException());
            }
        } else if(roleType == RoleType.COLLAGE){
            if(userDao.getAdminType() < roleType && !Objects.equals(userDao.getFieldId(), tag)){
                throw new ServletException(new RoleNotSupportedException());
            }
        }
    }

}
