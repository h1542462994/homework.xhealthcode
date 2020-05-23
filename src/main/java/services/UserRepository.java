package services;

import dao.UserHandle;
import ext.exception.OperationFailedException;
import models.AdminUser;
import models.Teacher;
import models.User;
import models.UserAccess;
import requests.AdminLogin;
import util.StringTools;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

public class UserRepository implements IUserRepository {
    private DbContext context;
    public UserRepository(DbContext context){
        this.context = context;
    }

    @Override
    public UserHandle adminLogin(AdminLogin login, ServletContext servletContext) {
        try {
            Teacher teacher = context.teachers.query("number = ?", login.number).unique();
            if(teacher == null)
                return null;
            AdminUser adminUser = context.adminUsers.query("teacherId = ?",teacher.getTeacherId()).unique();
            if(adminUser == null)
                return null;
            if(!adminUser.getPassword().equals(login.passport))
                return null;
            // 此时执行登录操作
            context.executeNoQuery("update userAccess set expired = true where userId = ?",teacher.getUserId());
            UserAccess access = createUserAccess(teacher.getUserId());
            context.userAccesses.update(access);

            return new UserHandle(access, this.context);
        } catch (OperationFailedException | SQLException e) {
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
