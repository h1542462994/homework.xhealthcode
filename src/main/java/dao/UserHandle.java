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

    public UserInfo getUserInfo() throws OperationFailedException {
        UserInfo userInfo = new UserInfo();
        userInfo.setAdmin(false);

        userInfo.setType(context.users.get(access.getUserId()).getUserType());

        IStudentTeacherUnion user;
        user = context.teachers.query("userId = ?", access.getUserId()).unique();
        if(user != null){
            AdminUser adminUser = context.adminUsers.query("teacherId = ?", ((Teacher)user).getTeacherId()).unique();
            if(adminUser != null){
                userInfo.setAdmin(true);
                userInfo.setRole(adminUser.getRole());
            }
            userInfo.setName(user.getName());
            userInfo.setNumber(user.getNumber());
        }
        user = context.students.query("userId = ?",access.getUserId()).unique();
        if(user != null){
            userInfo.setName(user.getName());
            userInfo.setNumber(user.getNumber());
        }

        Info info = context.infos.query("userId = ?",access.getUserId()).unique();
        if(info == null){
            userInfo.setAcquired(false);
        } else {
            userInfo.setAcquired(true);
            userInfo.setResult(info.getResult());
            userInfo.setDate(info.getDate());
        }

        return userInfo;
    }
}
