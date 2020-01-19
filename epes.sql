/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : epes

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 20/01/2020 01:00:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for daily_log
-- ----------------------------
DROP TABLE IF EXISTS `daily_log`;
CREATE TABLE `daily_log`  (
  `id` int(11) NOT NULL COMMENT '自增id',
  `emp_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  `last_upd_time` datetime(6) NULL DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未审阅，1已审阅',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `dpart_id` int(11) NOT NULL COMMENT '自增部门id',
  `dpart_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`dpart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int(11) NOT NULL COMMENT '自增id',
  `emp_id` char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工号（101200101）',
  `emp_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dpart_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0离职，1在职',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for login
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NULL DEFAULT NULL,
  `login_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `nt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增nt_id',
  `title` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`nt_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for prj_task
-- ----------------------------
DROP TABLE IF EXISTS `prj_task`;
CREATE TABLE `prj_task`  (
  `task_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增task_id',
  `prj_id` int(11) NOT NULL COMMENT '关联project',
  `weight` tinyint(2) NULL DEFAULT NULL COMMENT '权重',
  `create_time` datetime(6) NULL DEFAULT NULL,
  `finish_time` datetime(6) NULL DEFAULT NULL,
  `task_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '任务描述',
  `score_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评分标准说明',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未开始，1进行中，2已完成，3已作废',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `prj_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增prj_id',
  `prj_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '项目描述',
  `create_time` datetime(6) NULL DEFAULT NULL,
  `finish_time` datetime(6) NULL DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未开始，1进行中，2已完成，3已作废',
  PRIMARY KEY (`prj_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_eva
-- ----------------------------
DROP TABLE IF EXISTS `task_eva`;
CREATE TABLE `task_eva`  (
  `eva_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增eva_id',
  `emp_id` int(11) NULL DEFAULT NULL,
  `task_id` int(11) NULL DEFAULT NULL COMMENT '关联prj_task表',
  `create_time` datetime(6) NULL DEFAULT NULL,
  `finish_time` datetime(6) NULL DEFAULT NULL,
  `last_upd_time` datetime(6) NULL DEFAULT NULL,
  `score` decimal(5, 2) NULL DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '0未开始，1进行中，2已完成，3已作废',
  PRIMARY KEY (`eva_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增user_id',
  `emp_id` char(9) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '员工id',
  `password` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `role` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '角色（0管理员，1部门主管，2普通员工）',
  `last_login_time` datetime(6) NULL DEFAULT NULL,
  `isActive` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '（0失效，1生效）',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
