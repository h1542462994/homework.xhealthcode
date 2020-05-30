# Web应用开发系列文档

## 目录

[首页](../README.md)
[页面设计](./pages.md)
[模型设计](./models.md)
[Dao](./dao.md)
[动作](./action.md)
[插件](./addin.md)
[Sql代码](./sqls.md)

## Sql代码

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
  `adminUserId` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacherId` bigint(20) NOT NULL,
  `password` varchar(120) NOT NULL,
  `role` int(11) NOT NULL DEFAULT '0' COMMENT '角色 0:院级管理员,1:校级管理员,2:系统管理员',
  PRIMARY KEY (`adminUserId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.adminuser 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `adminuser` DISABLE KEYS */;
REPLACE INTO `adminuser` (`adminUserId`, `teacherId`, `password`, `role`) VALUES
	(1, 1, '25D55AD283AA400AF464C76D713C07AD', 2),
	(2, 1, '25D55AD283AA400AF464C76D713C07AD', 1);
/*!40000 ALTER TABLE `adminuser` ENABLE KEYS */;

-- 导出  表 xhealthcode.college 结构
CREATE TABLE IF NOT EXISTS `college` (
  `collegeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`collegeId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.college 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
REPLACE INTO `college` (`collegeId`, `name`) VALUES
	(1, '信息工程学院'),
	(2, '计算机科学与技术学院'),
	(3, '生物工程学院'),
	(6, '教育科学与技术学院');
/*!40000 ALTER TABLE `college` ENABLE KEYS */;

-- 导出  表 xhealthcode.dailycard 结构
CREATE TABLE IF NOT EXISTS `dailycard` (
  `dailyCardId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `answer` text,
  `result` int(11) NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  PRIMARY KEY (`dailyCardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.dailycard 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `dailycard` DISABLE KEYS */;
/*!40000 ALTER TABLE `dailycard` ENABLE KEYS */;

-- 导出  表 xhealthcode.info 结构
CREATE TABLE IF NOT EXISTS `info` (
  `infoId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`infoId`),
  UNIQUE KEY `UserId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.info 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `info` DISABLE KEYS */;
REPLACE INTO `info` (`infoId`, `userId`, `phone`, `result`, `date`) VALUES
	(1, 2, '19857180001', 0, '2020-05-30');
/*!40000 ALTER TABLE `info` ENABLE KEYS */;

-- 导出  表 xhealthcode.problemschema 结构
CREATE TABLE IF NOT EXISTS `problemschema` (
  `schemaId` bigint(20) NOT NULL AUTO_INCREMENT,
  `problem` varchar(120) NOT NULL,
  `problemType` int(11) NOT NULL DEFAULT '0',
  `problemRegion` text,
  `validMeta` text,
  PRIMARY KEY (`schemaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.problemschema 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `problemschema` DISABLE KEYS */;
/*!40000 ALTER TABLE `problemschema` ENABLE KEYS */;

-- 导出  表 xhealthcode.profession 结构
CREATE TABLE IF NOT EXISTS `profession` (
  `professionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `collegeId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`professionId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.profession 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `profession` DISABLE KEYS */;
REPLACE INTO `profession` (`professionId`, `collegeId`, `name`) VALUES
	(1, 2, '软件工程'),
	(2, 2, '计算机科学与技术'),
	(3, 2, '数字媒体技术');
/*!40000 ALTER TABLE `profession` ENABLE KEYS */;

-- 导出  表 xhealthcode.resultcache 结构
CREATE TABLE IF NOT EXISTS `resultcache` (
  `resultCacheId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `result` int(11) NOT NULL DEFAULT '0',
  `codeKey` varchar(120) NOT NULL,
  `date` date NOT NULL,
  `expiredAt` timestamp NOT NULL,
  PRIMARY KEY (`resultCacheId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.resultcache 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `resultcache` DISABLE KEYS */;
/*!40000 ALTER TABLE `resultcache` ENABLE KEYS */;

-- 导出  表 xhealthcode.student 结构
CREATE TABLE IF NOT EXISTS `student` (
  `studentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `idCard` varchar(60) NOT NULL,
  `number` varchar(60) NOT NULL,
  `xClassId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`studentId`),
  UNIQUE KEY `UserId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.student 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
REPLACE INTO `student` (`studentId`, `userId`, `name`, `idCard`, `number`, `xClassId`) VALUES
	(1, 2, '学生1', '330124199906000003', '201806000003', 2),
	(2, 4, '学生2', '330124199906000004', '201806000004', 2),
	(3, 5, '学生3', '330124199906000005', '201806000005', 6),
	(4, 6, '学生4', '330124199906000006', '201806000006', 6),
	(5, 7, '学生5', '330124199906000007', '201806000007', 7);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;

-- 导出  表 xhealthcode.teacher 结构
CREATE TABLE IF NOT EXISTS `teacher` (
  `teacherId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `idCard` varchar(60) DEFAULT NULL,
  `number` varchar(60) NOT NULL COMMENT '工号',
  `collegeId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`teacherId`),
  UNIQUE KEY `UserId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.teacher 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
REPLACE INTO `teacher` (`teacherId`, `userId`, `name`, `idCard`, `number`, `collegeId`) VALUES
	(1, 1, '系统管理员', '330124199906000000', '201806061201', 2),
	(2, 3, '院级管理员', '330124199906000001', '201806060001', 2);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;

-- 导出  表 xhealthcode.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userType` int(11) NOT NULL DEFAULT '0' COMMENT '用户类型 0=学生;1=老师',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.user 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`userId`, `userType`) VALUES
	(1, 1),
	(2, 0),
	(3, 1),
	(4, 0),
	(5, 0),
	(6, 0),
	(7, 0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 导出  表 xhealthcode.useraccess 结构
CREATE TABLE IF NOT EXISTS `useraccess` (
  `userAccessId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `token` varchar(120) NOT NULL,
  `expired` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`userAccessId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.useraccess 的数据：~15 rows (大约)
/*!40000 ALTER TABLE `useraccess` DISABLE KEYS */;
REPLACE INTO `useraccess` (`userAccessId`, `userId`, `token`, `expired`) VALUES
	(1, 1, 'w1cR30j1T4PDMYHVOifpAA==', NULL),
	(2, 1, 'H9c4Ug49kC9X7fILfkvfcw==', NULL),
	(3, 1, 'stK2GONoGLU+2HpOmLX5TA==', NULL),
	(4, 1, 'GZIwEXndik02SNxoKL5Ghg==', NULL),
	(5, 1, '8IsBU1Dcogpvs3Vg2lCfIA==', NULL),
	(6, 1, 'cv66ZuT1+37E5y/LsfT4fA==', NULL),
	(7, 1, '2c42VMVn9KNsaGf75Yyj0Q==', NULL),
	(8, 2, 'aYYdOIC1AvK35JJEKPbF1w==', NULL),
	(9, 1, 'OS7eJRNnmWEQGrUOaT/5Rg==', NULL),
	(10, 1, '//oYhUFiMLxzKWvGjCI9pg==', NULL),
	(11, 2, 'goxRPElpmNJMecYnBen4jg==', NULL),
	(12, 2, 'Dp8zDVK9aNX984A/rTGlUQ==', '2020-05-29 14:05:17'),
	(13, 1, 'BdXuReOxU7LoXv6xP3p2Jw==', NULL),
	(14, 1, 'HCr/afFkGwixpFZSn0ztWA==', NULL),
	(15, 1, '3rWsdiIr3etP9sWpcGnFEQ==', '2020-06-02 01:48:42');
/*!40000 ALTER TABLE `useraccess` ENABLE KEYS */;

-- 导出  视图 xhealthcode.userview 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `userview` (
	`userId` BIGINT(20) NOT NULL,
	`type` VARCHAR(2) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`name` VARCHAR(60) NOT NULL COLLATE 'utf8_general_ci',
	`idCard` VARCHAR(60) NOT NULL COLLATE 'utf8_general_ci',
	`number` VARCHAR(60) NOT NULL COLLATE 'utf8_general_ci',
	`field` BIGINT(20) NULL,
	`result` INT(11) NULL,
	`role` BINARY(0) NULL,
	`password` BINARY(0) NULL
) ENGINE=MyISAM;

-- 导出  表 xhealthcode.xclass 结构
CREATE TABLE IF NOT EXISTS `xclass` (
  `xclassId` bigint(20) NOT NULL AUTO_INCREMENT,
  `professionId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`xclassId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.xclass 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `xclass` DISABLE KEYS */;
REPLACE INTO `xclass` (`xclassId`, `professionId`, `name`) VALUES
	(1, 1, '软件工程1802班'),
	(2, 1, '软件工程1801班'),
	(3, 1, '软件工程1803班'),
	(4, 1, '软件工程1804班'),
	(5, 1, '软件工程1805班'),
	(6, 2, '计算机科学与技术实验01班'),
	(7, 2, '计算机科学与技术实验02班'),
	(8, 2, '计算机科学与技术1801班'),
	(9, 2, '计算机科学与技术1802班');
/*!40000 ALTER TABLE `xclass` ENABLE KEYS */;

-- 导出  视图 xhealthcode.xclassview 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `xclassview` (
	`collegeId` BIGINT(20) NOT NULL,
	`collegeName` VARCHAR(60) NOT NULL COLLATE 'utf8_general_ci',
	`professionId` BIGINT(20) NOT NULL,
	`professionName` VARCHAR(60) NOT NULL COLLATE 'utf8_general_ci',
	`xClassid` BIGINT(20) NOT NULL,
	`xClassName` VARCHAR(60) NOT NULL COLLATE 'utf8_general_ci'
) ENGINE=MyISAM;

-- 导出  视图 xhealthcode.userview 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `userview`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `userview` AS select `student`.`userId` AS `userId`,'学生' AS `type`,`student`.`name` AS `name`,`student`.`idCard` AS `idCard`,`student`.`number` AS `number`,`student`.`xClassId` AS `field`,`info`.`result` AS `result`,NULL AS `role`,NULL AS `password` from (`student` join `info`) where (`student`.`userId` = `info`.`userId`) union select `teacher`.`userId` AS `userId`,'教师' AS `type`,`teacher`.`name` AS `name`,`teacher`.`idCard` AS `idCard`,`teacher`.`number` AS `number`,`teacher`.`collegeId` AS `field`,`info`.`result` AS `result`,`adminuser`.`role` AS `role`,`adminuser`.`password` AS `password` from ((`teacher` join `adminuser`) join `info`) where ((`teacher`.`teacherId` = `adminuser`.`teacherId`) and (`teacher`.`userId` = `info`.`userId`)) union select `student`.`userId` AS `userId`,'学生' AS `type`,`student`.`name` AS `name`,`student`.`idCard` AS `idCard`,`student`.`number` AS `number`,`student`.`xClassId` AS `field`,NULL AS `NULL`,NULL AS `role`,NULL AS `password` from `student` where exists(select 1 from `info` where (`info`.`userId` = `student`.`userId`)) is false union select `teacher`.`userId` AS `userId`,'教师' AS `type`,`teacher`.`name` AS `name`,`teacher`.`idCard` AS `idCard`,`teacher`.`number` AS `number`,`teacher`.`collegeId` AS `field`,NULL AS `NULL`,`adminuser`.`role` AS `role`,`adminuser`.`password` AS `password` from (`teacher` join `adminuser`) where ((`teacher`.`teacherId` = `adminuser`.`teacherId`) and exists(select 1 from `info` where (`info`.`userId` = `teacher`.`userId`)) is false);

-- 导出  视图 xhealthcode.xclassview 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `xclassview`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `xclassview` AS select `college`.`collegeId` AS `collegeId`,`college`.`name` AS `collegeName`,`profession`.`professionId` AS `professionId`,`profession`.`name` AS `professionName`,`xclass`.`xclassId` AS `xClassid`,`xclass`.`name` AS `xClassName` from ((`college` join `profession`) join `xclass`) where ((`college`.`collegeId` = `profession`.`collegeId`) and (`profession`.`professionId` = `xclass`.`professionId`)) union select `college`.`collegeId` AS `collegeId`,`college`.`name` AS `collegeName`,NULL AS `professionId`,NULL AS `professionName`,NULL AS `xClassId`,NULL AS `xClassName` from `college` where exists(select `profession`.`collegeId` from `profession` where (`college`.`collegeId` = `profession`.`collegeId`)) is false union select `college`.`collegeId` AS `collegeId`,`college`.`name` AS `collegeName`,`profession`.`professionId` AS `professionId`,`profession`.`name` AS `professionName`,NULL AS `xClassId`,NULL AS `xClassName` from (`college` join `profession`) where ((`college`.`collegeId` = `profession`.`collegeId`) and exists(select `xclass`.`professionId` from `xclass` where (`xclass`.`professionId` = `profession`.`professionId`)) is false);

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
