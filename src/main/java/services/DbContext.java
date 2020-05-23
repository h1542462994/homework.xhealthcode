package services;

import ext.declare.DbContextBase;
import ext.declare.DbSettings;
import ext.sql.DbSet;
import models.*;

/**
 * 提供数据库读取相关的服务
 */
public class DbContext extends DbContextBase {
    public DbContext() throws ClassNotFoundException {
        super(new DbSettings(
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/trop.center?useSSL=false&serverTimezone=UTC",
                "root",
                "123456"
        ));
    }

    public DbSet<User> users = new DbSet<>(User.class);
    public DbSet<Teacher> teachers = new DbSet<>(Teacher.class);
    public DbSet<Student> students = new DbSet<>(Student.class);
    public DbSet<AdminUser> adminUsers = new DbSet<>(AdminUser.class);
    public DbSet<UserAccess> userAccesses = new DbSet<>(UserAccess.class);
}
