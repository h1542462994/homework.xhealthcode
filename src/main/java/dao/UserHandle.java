package dao;

import models.UserAccess;
import services.DbContext;

public class UserHandle {
    private DbContext context;
    private UserAccess access;
    public UserHandle(UserAccess access, DbContext context){
        this.access = access;
    }


    public UserAccess getAccess() {
        return access;
    }
}
