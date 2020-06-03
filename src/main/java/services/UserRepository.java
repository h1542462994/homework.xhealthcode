package services;

import dao.Result;
import dao.UserDao;
import dao.UserResult;
import ext.declare.DbContextBase;
import ext.exception.OperationFailedException;
import models.*;
import requests.UserLogin;
import util.StringTools;
import util.TypeType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 用户仓储类，由{@link ext.ServiceContainerBase#getService(Class)}自动创建
 */
public class UserRepository implements IUserRepository {
    private DbContext context;
    private String msg;
    public UserRepository(DbContextBase context){
        this.context = (DbContext) context;
    }

    public String getMsg(){
        return this.msg;
    }

    @Override
    public UserDao get(long id) {
        try {
            UserDao userDao = new UserDao();
            userDao.setAdmin(false);

            userDao.setType(context.users.get(id).getUserType());

            IStudentTeacherUnion user;
            user = context.teachers.query("userId = ?", id).unique();
            if(user != null){
                AdminUser adminUser = context.adminUsers.query("teacherId = ?", ((Teacher)user).getTeacherId()).unique();
                if(adminUser != null){
                    userDao.setAdmin(true);
                    userDao.setRole(adminUser.getRole());
                }
                userDao.setName(user.getName());
                userDao.setNumber(user.getNumber());
            }
            user = context.students.query("userId = ?",id).unique();
            if(user != null){
                userDao.setName(user.getName());
                userDao.setNumber(user.getNumber());
            }

            Info info = context.infos.query("userId = ?",id).unique();
            if(info == null){
                userDao.setAcquired(false);
            } else {
                userDao.setAcquired(true);
                userDao.setResult(info.getResult());
                userDao.setDate(info.getDate());
            }

            return userDao;
        } catch (OperationFailedException e){
            return null;
        }
    }

    public UserAccess login(UserLogin login, HttpServletResponse response){
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

            return access;
        } catch (OperationFailedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserAccess active(HttpServletRequest request){
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

            return access;
        } catch (OperationFailedException e){
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public ArrayList<UserDao> fromCollege(long college) {
//        try {
//            ArrayList<UserDao> userDaos = new ArrayList<>();
//            for(User user: context.users.query("collegeId = ?", college)){
//                userDaos.add(get(user.getUserId()));
//            }
//            return userDaos;
//        } catch (OperationFailedException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        UserAccess access = active(request);
        if(access != null){
            access.setExpired(null);
            response.addCookie(new Cookie("_token", null));
        }
    }

    @Override
    public UserResult result(User user) {
        try {
            //User user = context.users.get(id);
            if(user == null)
                return null;
            UserResult result = new UserResult();
            result.setId(user.getUserId());
            result.setType(user.getUserType());
            Info info = context.infos.query("userId = ?", user.getUserId()).unique();
            if(info == null){
                result.setResult(Result.No);
            } else {
                result.setResult(info.getResult() + 1);
            }

            if(result.getType() == TypeType.STUDENT){
                Student student = context.students.query("userId = ?", user.getUserId()).unique();
                result.setFieldId(student.getXClassId());
            } else {
                Teacher teacher = context.teachers.query("userId =?", user.getUserId()).unique();
                result.setFieldId(teacher.getCollegeId());

                AdminUser adminUser = context.adminUsers.query("teacherId = ?", teacher.getTeacherId()).unique();
                if(adminUser == null){
                    result.setAdmin(false);
                } else {
                    result.setAdmin(true);
                }
            }

            return result;
        } catch (OperationFailedException e) {
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
