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
                "jdbc:mysql://localhost:3306/xhealthcode?useSSL=false&serverTimezone=UTC",
                "root",
                "123456"
        ));
    }

    public final DbSet<User> users = new DbSet<>(User.class);
    public final DbSet<Teacher> teachers = new DbSet<>(Teacher.class);
    public final DbSet<Student> students = new DbSet<>(Student.class);
    public final DbSet<AdminUser> adminUsers = new DbSet<>(AdminUser.class);
    public final DbSet<UserAccess> userAccesses = new DbSet<>(UserAccess.class);
    public final DbSet<Info> infos = new DbSet<>(Info.class);
    public final DbSet<College> colleges = new DbSet<>(College.class);
    public final DbSet<Profession> professions = new DbSet<>(Profession.class);
    public final DbSet<Xclass> xclasses = new DbSet<>(Xclass.class);
}
