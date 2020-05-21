package ext.service;

import ext.declare.DbContextBase;
import ext.declare.DbSettings;
import ext.sql.DbSet;
import model.User;

public class DbContext extends DbContextBase {

    public DbContext() throws ClassNotFoundException {
        super(new DbSettings(
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/trop.center?useSSL=false&serverTimezone=UTC",
                "root",
                "123456"
        ));
    }

    public DbSet<User> Users = new DbSet<>(User.class);
}
