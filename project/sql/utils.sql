/*
SQLyog  v12.2.6 (64 bit)
MySQL - 8.0.22 : Database - utils
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`utils` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `utils`;

/*Table structure for table `interact_action` */

DROP TABLE IF EXISTS `interact_action`;

CREATE TABLE `interact_action` (
  `id` varchar(64) NOT NULL,
  `begin_class_id` varchar(64) DEFAULT NULL COMMENT '班级id',
  `code` text COMMENT '动作指令',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(1) DEFAULT NULL COMMENT '0删除 1正常',
  `data` text COMMENT '当前动作数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='三峡医专互动 - 当前动作表';

/*Data for the table `interact_action` */

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `login_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `model` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(64) DEFAULT NULL,
  `update_user` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `INDEX_ID` (`id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`user_id`,`login_name`,`model`,`time`,`method`,`ip`,`address`,`create_time`,`create_user`,`update_user`,`update_time`,`status`) values 
(2,'1','admin','登录',3689,'com.yss.web.tUser.controller.TUserController.login()','0:0:0:0:0:0:0:1',NULL,'2021-10-22 16:06:12',NULL,NULL,'2021-10-22 16:06:12','1'),
(3,'1','admin','日志',41,'com.yss.web.sysLog.controller.SysLogController.getSysLogPageList()','0:0:0:0:0:0:0:1',NULL,'2021-10-22 16:06:31',NULL,NULL,'2021-10-22 16:06:31','1'),
(4,'1','admin','登录',328,'com.yss.web.tUser.controller.TUserController.login()','0:0:0:0:0:0:0:1',NULL,'2021-10-22 16:10:28',NULL,NULL,'2021-10-22 16:10:28','1'),
(5,'1','admin','日志',36,'com.yss.web.sysLog.controller.SysLogController.getSysLogPageList()','0:0:0:0:0:0:0:1',NULL,'2021-10-22 16:10:38',NULL,NULL,'2021-10-22 16:10:38','1'),
(6,'1','admin','登录',891,'com.yss.web.tUser.controller.TUserController.login()','0:0:0:0:0:0:0:1',NULL,'2021-10-22 16:56:54',NULL,NULL,'2021-10-22 16:56:54','1'),
(7,'1','admin','用户表详情',6,'com.yss.web.tUser.controller.TUserController.getTUser()','0:0:0:0:0:0:0:1',NULL,'2021-10-22 16:57:06',NULL,NULL,'2021-10-22 16:57:06','1'),
(8,'1','admin','登录',484,'com.zf.web.tUser.controller.TUserController.login()','0:0:0:0:0:0:0:1',NULL,'2021-10-25 08:44:03',NULL,NULL,'2021-10-25 08:44:03','1'),
(9,'1','admin','日志',43,'com.zf.web.sysLog.controller.SysLogController.getSysLogPageList()','0:0:0:0:0:0:0:1',NULL,'2021-10-25 08:44:28',NULL,NULL,'2021-10-25 08:44:28','1'),
(10,'1','admin','日志',69,'com.zf.web.sysLog.controller.SysLogController.getSysLogPageList()','0:0:0:0:0:0:0:1',NULL,'2021-10-25 09:02:29',NULL,NULL,'2021-10-25 09:02:29','1'),
(11,'1','admin','登录',428,'com.zf.web.tUser.controller.TUserController.login()','0:0:0:0:0:0:0:1',NULL,'2021-10-25 09:07:05',NULL,NULL,'2021-10-25 09:07:05','1'),
(12,'1','admin','日志',48,'com.zf.web.sysLog.controller.SysLogController.getSysLogPageList()','0:0:0:0:0:0:0:1',NULL,'2021-10-25 09:10:33',NULL,NULL,'2021-10-25 09:10:33','1'),
(13,'1','admin','日志',12,'com.zf.web.sysLog.controller.SysLogController.getSysLogPageList()','0:0:0:0:0:0:0:1',NULL,'2021-10-25 09:10:45',NULL,NULL,'2021-10-25 09:10:45','1');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL COMMENT '账号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `org_id` int DEFAULT NULL COMMENT '组织id',
  `realname` varchar(255) NOT NULL COMMENT '真实姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(5) DEFAULT NULL COMMENT '性`user``interact_action`别',
  `id_card` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `type` varchar(50) DEFAULT NULL COMMENT '用户类型 ADMIN_USER管理员用户STUDENT_USER学生 SOCIAL_USER社会人员 TEACHER_USER教师',
  `head_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(1) DEFAULT NULL COMMENT '0删除 1正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`org_id`,`realname`,`phone`,`sex`,`id_card`,`type`,`head_url`,`role_id`,`create_user`,`create_time`,`update_user`,`update_time`,`status`) values 
(1,'admin','123',1,'yss','18888888888','1',NULL,'ADMIN_USER',NULL,'1',NULL,NULL,NULL,NULL,'1'),
(3,'yss','123',0,'string','string','1','string','string','string','string',NULL,'2021-10-22 14:37:14',NULL,'2021-10-22 14:37:14','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
