package dao;

import util.RoleType;
import util.TypeType;

import java.sql.Date;

public class UserDao {
    public UserDao(){

    }

    /**
     * 是否是管理员
     */
    private boolean isAdmin;
    /**
     *
     */
    private int type;
    private String name;
    private String number;
    private int role;
    private boolean acquired;
    private Integer result;
    private Date date;

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getRole() {
        return role;
    }

    public String getTypeDisplay(){
        if(type == TypeType.STUDENT){
            return "学生";
        } else {
            return "老师";
        }
    }

    public String getRoleDisplay(){
        if(role == RoleType.SYSTEM) {
            return "系统管理员";
        } else if(role == RoleType.SCHOOL){
            return "校级管理员";
        } else {
            return "院级管理员";
        }
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isAcquired() {
        return acquired;
    }

    public void setAcquired(boolean acquired) {
        this.acquired = acquired;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
