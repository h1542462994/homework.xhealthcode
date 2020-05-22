# Web应用开发系列文档

## 目录

[首页](../README.md)
[页面设计](./pages.md)
[模型设计](./models.md)
[请求模型](./requestmodel.md)
[仓储](./repository.md)
[插件](./addin.md)
[Sql代码](./sqls.md)

## 插件

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

@过滤器的内容将在以后补充