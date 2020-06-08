package models;

import ext.annotation.Entity;
import ext.annotation.Primary;

@Entity(model = "adminuser")
public class AdminUser {


    @Primary
    private long adminUserId;
    private long teacherId;
    private String password;
    private int role;

    public long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


}
