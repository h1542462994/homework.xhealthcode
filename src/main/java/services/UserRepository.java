package services;

import dao.UserHandle;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import models.*;
import requests.UserLogin;
import util.StringTools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRepository implements IUserRepository {
    private DbContext context;
    private String msg;
    public UserRepository(DbContextBase context){
        this.context = (DbContext) context;
    }

    public String getMsg(){
        return this.msg;
    }

    public UserHandle login(UserLogin login, HttpServletResponse response){
        try {
            IStudentTeacherUnion user;
            if(login.type == UserLogin.ADMIN){
                user = context.teachers.query("number = ?", login.number).unique();
                if(user == null){
                    msg = "不存在该用户";
                    return null;
                }

                AdminUser adminUser = context.adminUsers.query("teacherId = ?",user.getId()).unique();
                if(adminUser == null){
                    msg = "该用户不是管理员";
                    return null;
                }

                if(!adminUser.getPassword().equals(StringTools.md5(login.passport))){
                    msg = "密码错误";
                    return null;
                }
            } else {
                if(login.type == UserLogin.STUDENT){
                    user = context.students.query("number = ?",login.number).unique();
                } else {
                    user = context.teachers.query("number = ?",login.number).unique();
                }
                if(user == null){
                    msg = "不存在该用户";
                    return null;
                }


                System.out.println("user:" + user.getUserId() + " idCard:" + user.getIdCard().substring(user.getIdCard().length() - 6));
                if(!user.getName().equals(login.name)){
                    msg = "姓名错误";
                    return null;
                }
                if(!user.getIdCard().substring(user.getIdCard().length() - 6).equals(login.passport))
                {
                    msg = "密码错误";
                    return null;
                }

            }
            // 此时执行登录操作
            context.executeNoQuery("update userAccess set expired = null where userId = ?",user.getUserId());
            UserAccess access = createUserAccess(user.getUserId());
            context.userAccesses.add(access);
            response.addCookie(new Cookie("_token", access.getToken()));

            return new UserHandle(access, this.context);
        } catch (OperationFailedException e) {
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
