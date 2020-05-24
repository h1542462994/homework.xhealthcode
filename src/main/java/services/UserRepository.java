package services;

import dao.UserHandle;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import models.AdminUser;
import models.Teacher;
import models.UserAccess;
import requests.UserLogin;
import util.StringTools;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRepository implements IUserRepository {
    private DbContext context;
    public UserRepository(DbContextBase context){
        this.context = (DbContext) context;
    }

    @Override
    public UserHandle adminLogin(UserLogin login, HttpServletResponse response) {
        try {
            if(login.isAdmin.equals("true")){
                Teacher teacher = context.teachers.query("number = ?", login.number).unique();
                if(teacher == null)
                    return null;
                AdminUser adminUser = context.adminUsers.query("teacherId = ?",teacher.getTeacherId()).unique();
                if(adminUser == null)
                    return null;
                if(!adminUser.getPassword().equals(login.passport))
                    return null;
                // 此时执行登录操作
                context.executeNoQuery("update userAccess set expired = null where userId = ?",teacher.getUserId());
                UserAccess access = createUserAccess(teacher.getUserId());
                context.userAccesses.add(access);
                response.addCookie(new Cookie("_token", access.getToken()));

                return new UserHandle(access, this.context);
            } else {
                return null;
            }
        } catch (OperationFailedException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public UserHandle getUser(HttpServletRequest request){
        try {
            String token = null;
            Cookie[] cookies = request.getCookies();
            Cookie tokenCookie = null;
            if(cookies == null)
                return null;
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("_token")) {
                    token = cookie.getValue();
                    tokenCookie = cookie;
                    break;
                }
            }

            if(token == null)
                return null;
            UserAccess access = context.userAccesses.query("token = ?",token).unique();
            if(access == null)
                return null;
            if(access.getExpired().before(Timestamp.valueOf(LocalDateTime.now()))){
                return null;
            }

            return new UserHandle(access, context);
        } catch (OperationFailedException e){
            e.printStackTrace();
            return null;
        }
    }

    private UserAccess createUserAccess(long userId){
        UserAccess access = new UserAccess();
        access.setUserId(userId);
        access.setToken(StringTools.makeToken());
        access.setExpired(Timestamp.valueOf(LocalDateTime.now().plusDays(3)));
        return access;
    }
}
