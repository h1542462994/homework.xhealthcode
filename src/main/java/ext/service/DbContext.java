package ext.service;

import ext.declare.DbContextBase;
import ext.sql.DbSet;
import model.User;

public class DbContext extends DbContextBase {
    public DbContext(){
        super();
        super.driver = "com.mysql.cj.jdbc.Driver";
        super.url = "jdbc:mysql://localhost:3306/trop.center?useSSL=false&serverTimezone=UTC";
        super.user = "root";
        super.passport = "123456";
    }

    public DbSet<User> Users = new DbSet<>(User.class);
}
