package models;

import ext.annotation.Rename;
import ext.annotation.Entity;
import ext.annotation.Primary;

@Entity(model = "user")
public class User {



    @Primary
    private long userId;
    private int userType;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
