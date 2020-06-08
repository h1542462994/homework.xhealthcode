-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.17 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
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

-- 正在导出表  xhealthcode.adminuser 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `adminuser` DISABLE KEYS */;
REPLACE INTO `adminuser` (`adminUserId`, `teacherId`, `password`, `role`) VALUES
	(1, 1, '25D55AD283AA400AF464C76D713C07AD', 2),
	(2, 2, '25D55AD283AA400AF464C76D713C07AD', 1);
/*!40000 ALTER TABLE `adminuser` ENABLE KEYS */;

-- 导出  表 xhealthcode.college 结构
CREATE TABLE IF NOT EXISTS `college` (
  `collegeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`collegeId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.college 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
REPLACE INTO `college` (`collegeId`, `name`) VALUES
	(1, '信息工程学院'),
	(2, '计算机科学与技术学院'),
	(3, '生物工程学院'),
	(15, '教育科学与技术学院');
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

-- 正在导出表  xhealthcode.info 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `info` DISABLE KEYS */;
REPLACE INTO `info` (`infoId`, `userId`, `phone`, `result`, `date`) VALUES
	(1, 2, '19857180001', 0, '2020-05-30');
/*!40000 ALTER TABLE `info` ENABLE KEYS */;

-- 导出  表 xhealthcode.profession 结构
CREATE TABLE IF NOT EXISTS `profession` (
  `professionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `collegeId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`professionId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.profession 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `profession` DISABLE KEYS */;
REPLACE INTO `profession` (`professionId`, `collegeId`, `name`) VALUES
	(1, 2, '软件工程'),
	(2, 2, '计算机科学与技术'),
	(3, 2, '数字媒体技术'),
	(4, 2, '嵌入式系统开发');
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

-- 正在导出表  xhealthcode.student 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
REPLACE INTO `student` (`studentId`, `userId`, `name`, `idCard`, `number`, `xClassId`) VALUES
	(1, 2, '学生1', '330124199906000003', '201806000003', 2),
	(2, 4, '学生2', '330124199906000004', '201806000004', 2),
	(3, 5, '学生3', '330124199906000005', '201806000005', 6),
	(4, 6, '学生4', '330124199906000006', '201806000006', 6),
	(5, 7, '学生5', '330124199906000007', '201806000007', NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.teacher 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
REPLACE INTO `teacher` (`teacherId`, `userId`, `name`, `idCard`, `number`, `collegeId`) VALUES
	(1, 1, '系统管理员', '330124199906000000', '201806061201', NULL),
	(2, 3, '院级管理员', '330124199906000001', '201806060001', 2),
	(3, 8, '普通教师', '330123199906000008', '201806060008', 1);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;

-- 导出  表 xhealthcode.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userType` int(11) NOT NULL DEFAULT '0' COMMENT '用户类型 0=学生;1=老师',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.user 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`userId`, `userType`) VALUES
	(1, 1),
	(2, 0),
	(3, 1),
	(4, 0),
	(5, 0),
	(6, 0),
	(7, 0),
	(8, 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 导出  表 xhealthcode.useraccess 结构
CREATE TABLE IF NOT EXISTS `useraccess` (
  `userAccessId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `token` varchar(120) NOT NULL,
  `expired` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`userAccessId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.useraccess 的数据：~22 rows (大约)
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
	(12, 2, 'Dp8zDVK9aNX984A/rTGlUQ==', NULL),
	(13, 1, 'BdXuReOxU7LoXv6xP3p2Jw==', NULL),
	(14, 1, 'HCr/afFkGwixpFZSn0ztWA==', NULL),
	(15, 1, '3rWsdiIr3etP9sWpcGnFEQ==', NULL),
	(16, 1, 'THtgSSBEUjtVq4MssEq+bA==', NULL),
	(17, 2, 'wqGkZj9PbyB6b0QfrHlekw==', '2020-06-05 04:27:05'),
	(18, 1, 'DwTGokx34OwUEFBchgXPkw==', NULL),
	(19, 1, '7PDuSR/5XgSJ/qBHJoieuw==', NULL),
	(20, 1, '1fer4YjlXxjJBcJxTTPHYw==', NULL),
	(21, 1, 'bR2yXdfOwvpOoZ91tID8+Q==', NULL),
	(22, 1, 'wEuvC3XnFLk7nm2Otpk26Q==', '2020-06-10 09:53:22');
/*!40000 ALTER TABLE `useraccess` ENABLE KEYS */;

-- 导出  表 xhealthcode.xclass 结构
CREATE TABLE IF NOT EXISTS `xclass` (
  `xclassId` bigint(20) NOT NULL AUTO_INCREMENT,
  `professionId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`xclassId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 正在导出表  xhealthcode.xclass 的数据：~10 rows (大约)
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
	(9, 2, '计算机科学与技术1802班'),
	(10, 1, '软件工程1806班');
/*!40000 ALTER TABLE `xclass` ENABLE KEYS */;