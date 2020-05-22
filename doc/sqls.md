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

```sql
-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.17 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 xhealthcode 的数据库结构
CREATE DATABASE IF NOT EXISTS `xhealthcode` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `xhealthcode`;

-- 导出  表 xhealthcode.adminuser 结构
CREATE TABLE IF NOT EXISTS `adminuser` (
  `AdminUserId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` bigint(20) NOT NULL,
  `Password` varchar(120) NOT NULL,
  `Role` int(11) NOT NULL DEFAULT '0' COMMENT '角色 0:院级管理员,1:校级管理员,2:系统管理员',
  PRIMARY KEY (`AdminUserId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.colleage 结构
CREATE TABLE IF NOT EXISTS `colleage` (
  `ColleageId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY (`ColleageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.dailycard 结构
CREATE TABLE IF NOT EXISTS `dailycard` (
  `DailyCardId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` bigint(20) NOT NULL,
  `Answer` text,
  `Result` int(11) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `EnterSchool` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`DailyCardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.info 结构
CREATE TABLE IF NOT EXISTS `info` (
  `InfoId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` bigint(20) NOT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`InfoId`),
  UNIQUE KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.problemschema 结构
CREATE TABLE IF NOT EXISTS `problemschema` (
  `SchemaId` bigint(20) NOT NULL AUTO_INCREMENT,
  `Problem` varchar(120) NOT NULL,
  `ProblemType` int(11) NOT NULL DEFAULT '0',
  `ProblemRegion` text,
  `ValidMeta` text,
  PRIMARY KEY (`SchemaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.profession 结构
CREATE TABLE IF NOT EXISTS `profession` (
  `ProfessonId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ColleageId` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY (`ProfessonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.resultcache 结构
CREATE TABLE IF NOT EXISTS `resultcache` (
  `ResultCacheId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` bigint(20) NOT NULL,
  `Result` int(11) NOT NULL DEFAULT '0',
  `CodeKey` varchar(120) NOT NULL,
  `Date` date NOT NULL,
  `ExpiredAt` timestamp NOT NULL,
  PRIMARY KEY (`ResultCacheId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `StudentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  `IdCard` varchar(60) NOT NULL,
  `Number` varchar(60) NOT NULL,
  `XClassId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`StudentId`),
  UNIQUE KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.teacher 结构
CREATE TABLE IF NOT EXISTS `teacher` (
  `TeacherId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  `IdCard` varchar(60) DEFAULT NULL,
  `Number` varchar(60) NOT NULL COMMENT '工号',
  `ColleageId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TeacherId`),
  UNIQUE KEY `UserId` (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `UserId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserType` int(11) NOT NULL DEFAULT '0' COMMENT '用户类型 0=学生;1=老师,2=管理员',
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.useraccess 结构
CREATE TABLE IF NOT EXISTS `useraccess` (
  `UserAccessId` bigint(20) NOT NULL AUTO_INCREMENT,
  `UserId` bigint(20) NOT NULL,
  `Token` varchar(120) NOT NULL,
  `Expired` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`UserAccessId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 xhealthcode.xclass 结构
CREATE TABLE IF NOT EXISTS `xclass` (
  `XClassId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ProfessionId` bigint(20) NOT NULL,
  `Name` varchar(60) NOT NULL,
  PRIMARY KEY (`XClassId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

```