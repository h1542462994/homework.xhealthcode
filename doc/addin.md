# Web应用开发系列文档

## 目录

[首页](../README.md)
[页面设计](./pages.md)
[模型设计](./models.md)
[Dao](./dao.md)
[动作](./action.md)
[插件](./addin.md)
[Sql代码](./sqls.md)

## 插件

### 服务容器

由`ext.ServiceContainerBase`实现，提供注册服务和获取服务的能力。其中注册服务有`addTransient`和`addSingleton`两种实现方式。获取服务应使用`getService`方法。

在本项目中，使用了`service.ServiceContainer`扩展了基类，从而作为服务容器使用。

### 数据库模型绑定

数据库模型绑定主要通过反射机制将对象绑定到数据库的相应模型。首先先声明模型类。

#### 第一步：声明模型类

```java
package models;

import ext.annotation.Rename;
import ext.annotation.Entity;
import ext.annotation.Primary;

@Entity(model = "users")
public class User {
    @Primary
    @Rename(name = "user_id")
    private long userId;

    private String name;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

其中`@Primary`表示该属性为主属性，目前支持以单一属性为主属性进行查询。`@Rename`表示重命名ColumnName，否则将以Field的名称作为数据库的ColumnName。

#### 第二步：在`services/DbContext`中注册模型

其中DbSettings中为数据库的连接属性。使用`DbSet<T>`来注册模型。

```java
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

    public DbSet<User> Users = new DbSet<>(User.class);
}
```

#### 第三步：使用`DbSet<T>`数据模型

```java
DbContext context = ServiceContainer.get().dbContext();
for(User user = context.Users.query("name = ?", "admin"){
    ...
}
```

### 快速验证器

#### 第一步：创建表单验证模型

```java
package requests;

import ext.annotation.Region;
import ext.annotation.Required;

public class UserLogin {
    @Required
    @Region(min = 2, max = 20)
    public String name;
    @Required
    public String passport;
}

```

#### 第二步：执行验证

```java
try {
    UserLogin userLogin = Validator.assertValue(UserLogin.class, request);
} catch (ValidateFailedException e){
    ...
}
```

当通过验证时，将会不引发错误且能够正确的返回值，否则，将引发ValidateFailedException异常。

### 过滤器

过滤器是用来对`HttpServletRequest`和`HttpServletResponse`的一个类。在这个应用中，实现了两个过滤器。

#### filters.LogFilter

用于打日志的一个过滤器。

#### filters.UserFilter

用于注入当前用户信息。并且可以在用户未登录时重定向。

#### filters.AdminFilter

未实现，用于提供验证用户的管理权限的能力，并执行相应的动作，将会配合AdminManager使用。

### Cache

数据项缓存，用于缓存某些特定的数据项，从而提高应用的运行速度。Cache的两类实现`CacheItem`和`CacheCollection`分别提供单一项和集合项的缓存功能。


