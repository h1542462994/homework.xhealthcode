# Web应用开发系列文档

## 目录

[首页](../README.md)
[页面设计](./pages.md)
[模型设计](./models.md)
[Dao](./dao.md)
[动作](./action.md)
[插件](./addin.md)
[Sql代码](./sqls.md)

## 动作

动作定义于Dao层以上，主要用于封装一个特定的谓词逻辑，在这个系统中，将动作分成三组。第一组与用户相关

### UserAction

#### get(@_token)

从当前的_token中查找当前的用户，如果查找到了当前的用户，则返回用户的基本信息，否则，返回一个null。

#### login(@userlogin)

从userlogin出发，进行用户的登录操作，如果登录成功，则返回用户的基本信息，否则返回errormsg.

#### logout()

登录当前的用户

#### adduser(@state,@user)

插入一条用户

#### @results addusers(@state,iterable<@user> users)

插入多条用户

#### @pages teachers(@state, [@pageindex])

查找所有的教师


### CollegeAction

#### @pages colleges(@state, [@pageindex])

获取所有college的信息

#### @pages profession(@state, @college, [@pageindex])

获取专业的信息

#### @pages xclass(@state, @profession, [@pageindex])

获取所有班级的信息








