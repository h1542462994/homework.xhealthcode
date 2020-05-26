package dao;

import ext.exception.OperationFailedException;
import models.AdminUser;
import models.IStudentTeacherUnion;
import models.Teacher;
import models.UserAccess;
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
        UserInfo info = new UserInfo();
        info.setAdmin(false);

        IStudentTeacherUnion user;
        user = context.teachers.query("userId = ?", access.getUserId()).unique();
        if(user != null){
            info.setRole("teacher");
            info.setName(user.getName());
            info.setNumber(user.getNumber());

            AdminUser adminUser = context.adminUsers.query("teacherId = ?", ((Teacher)user).getTeacherId()).unique();
            if(adminUser != null){
                info.setAdmin(true);
            }
        }
        user = context.students.query("userId = ?",access.getUserId()).unique();
        if(user != null){
            info.setRole("student");
            info.setName(user.getName());
            info.setNumber(user.getNumber());
        }

        return info;
    }
}
