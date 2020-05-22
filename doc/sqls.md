# Web应用开发系列文档

## 目录

[首页](../README.md)
[页面设计](./pages.md)
[模型设计](./models.md)
[请求模型](./requestmodel.md)
[仓储](./repository.md)
[插件](./addin.md)
[Sql代码](./sqls.md)

## Sql代码

### 建表

#### User

```sql
CREATE TABLE `user` (
	`UserId` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`Uid` VARCHAR(120) NULL DEFAULT NULL,
	`UserType` INT(11) NOT NULL DEFAULT '1' COMMENT '用户类型 0=教师，1=学生',
	`Password` VARCHAR(120) NOT NULL,
	PRIMARY KEY (`UserId`),
	UNIQUE INDEX `Uid` (`Uid`)
)
```

#### UserAccess

```sql
CREATE TABLE `userAccess` (
	`UserAccessId` BIGINT NOT NULL AUTO_INCREMENT,
	`UserId` BIGINT NOT NULL,
	`Token` VARCHAR(120) NOT NULL,
	`Expired` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`UserAccessId`),
	CONSTRAINT `FK_User_UserAccesses` FOREIGN KEY (`UserId`) REFERENCES `user` () ON UPDATE RESTRICT ON DELETE SET NULL
)
COLLATE='utf8_general_ci'
;
```

#### Teacher

```sql
CREATE TABLE `teacher` (
	`TeacherId` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`UserId` BIGINT(20) NOT NULL,
	`Name` VARCHAR(60) NOT NULL,
	`IdCard` VARCHAR(60) NOT NULL,
	`Number` VARCHAR(60) NOT NULL COMMENT '工号',
	`ColleageId` BIGINT(20) NULL DEFAULT NULL,
	`Role` INT(11) NOT NULL DEFAULT '0',
	PRIMARY KEY (`TeacherId`),
	UNIQUE INDEX `UserId` (`UserId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

```

#### Student

```sql
CREATE TABLE `student` (
	`StudentId` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`UserId` BIGINT(20) NOT NULL,
	`Name` VARCHAR(60) NOT NULL,
	`IdCard` VARCHAR(60) NOT NULL,
	`Number` VARCHAR(60) NOT NULL,
	`XClassId` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`StudentId`),
	UNIQUE INDEX `UserId` (`UserId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
```