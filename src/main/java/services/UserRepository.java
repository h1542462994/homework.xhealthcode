package services;

import dao.UserHandle;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import models.*;
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

    public UserHandle login(UserLogin login, HttpServletResponse response){
        try {
            IStudentTeacherUnion user;
            if(login.type == 2){
                user = context.teachers.query("number = ?", login.number).unique();
                if(user == null)
                    return null;
                AdminUser adminUser = context.adminUsers.query("teacherId = ?",((Teacher)user).getTeacherId()).unique();
                if(adminUser == null)
                    return null;
                if(!adminUser.getPassword().equals(StringTools.md5(login.passport)))
                    return null;

            } else {
                if(login.type == 0){
                    user = context.students.query("number = ?",login.number).unique();
                } else {
                    user = context.teachers.query("number = ?",login.number).unique();
                }
                if(user == null)
                    return null;

                if(!user.getIdCard().substring(user.getIdCard().length() - 6).equals(login.passport))
                    return null;
                if(!user.getName().equals(login.name))
                    return null;
            }
            // 此时执行登录操作
            context.executeNoQuery("update userAccess set expired = null where userId = ?",user.getUserId());
            UserAccess access = createUserAccess(user.getUserId());
            context.userAccesses.add(access);
            response.addCookie(new Cookie("_token", access.getToken()));

            return new UserHandle(access, this.context);
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
