package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

import java.sql.Timestamp;

@Entity(model = "useraccess")
public class UserAccess {
    @Primary
    private long userAccessId;
    private long userId;
    private String token;
    private Timestamp expired;

    public long getUserAccessId() {
        return userAccessId;
    }

    public void setUserAccessId(long userAccessId) {
        this.userAccessId = userAccessId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpired() {
        return expired;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }
}
