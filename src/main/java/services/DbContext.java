package services;

import ext.sql.DbContextBase;
import ext.sql.DbSettings;
import ext.sql.DbSet;
import models.*;

/**
 * 提供数据库读取相关的服务
 */
public class DbContext extends DbContextBase {
    public DbContext(ServiceContainer container) throws ClassNotFoundException {
        super((DbSettings) container.getConfig("dbsettings"));
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
    public final DbSet<DailyCard> dailyCards = new DbSet<>(DailyCard.class);
    public final DbSet<ResultCache> resultCaches = new DbSet<>(ResultCache.class);
}
