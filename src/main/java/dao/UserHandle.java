package dao;

import ext.exception.OperationFailedException;
import models.*;
import services.DbContext;

public class UserHandle {
    private DbContext context;
    private UserAccess access;
    public UserHandle(UserAccess access, DbContext context){
        this.context = context;
        this.access = access;
    }

    public UserAccess getAccess() {
        return access;
    }

    public UserDao getUserInfo() throws OperationFailedException {
        UserDao userDao = new UserDao();
        userDao.setAdmin(false);

        userDao.setType(context.users.get(access.getUserId()).getUserType());

        IStudentTeacherUnion user;
        user = context.teachers.query("userId = ?", access.getUserId()).unique();
        if(user != null){
            AdminUser adminUser = context.adminUsers.query("teacherId = ?", ((Teacher)user).getTeacherId()).unique();
            if(adminUser != null){
                userDao.setAdmin(true);
                userDao.setRole(adminUser.getRole());
            }
            userDao.setName(user.getName());
            userDao.setNumber(user.getNumber());
        }
        user = context.students.query("userId = ?",access.getUserId()).unique();
        if(user != null){
            userDao.setName(user.getName());
            userDao.setNumber(user.getNumber());
        }

        Info info = context.infos.query("userId = ?",access.getUserId()).unique();
        if(info == null){
            userDao.setAcquired(false);
        } else {
            userDao.setAcquired(true);
            userDao.setResult(info.getResult());
            userDao.setDate(info.getDate());
        }

        return userDao;
    }
}
