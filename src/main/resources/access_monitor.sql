/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : access_monitor

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2017-03-13 14:48:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for datatables
-- ----------------------------
DROP TABLE IF EXISTS `datatables`;
CREATE TABLE `datatables` (
  `ENCODE` varchar(50) NOT NULL DEFAULT '' COMMENT '编码',
  `NAME` varchar(50) DEFAULT NULL COMMENT '名称',
  `COMMENT` varchar(255) DEFAULT NULL COMMENT '备注',
  `STATUS` varchar(50) DEFAULT NULL COMMENT '账号状态',
  `CREATE_TIME` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `LAST_LOGIN_TIME` varchar(50) DEFAULT NULL COMMENT '最后登录时间',
  `ACCOUNT_TYPE` varchar(50) DEFAULT NULL COMMENT '账号类型',
  PRIMARY KEY (`ENCODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of datatables
-- ----------------------------
INSERT INTO `datatables` VALUES ('A201406', 'Dave1', '这是一个测试账户', 'FREEZN', '2017-03-12 11:23:53.948 +0000', null, 'SHOP_ADMIN');
INSERT INTO `datatables` VALUES ('A201705', 'Stone21', 'area manager access', 'FREEZN', '2016-12-13 07:05:57.960 +0000', '2016-12-13 07:05:57.960 +0000', 'NORMAL');
INSERT INTO `datatables` VALUES ('H201406', 'Micheller', '这是一个测试账户', 'NORMAL', '2017-03-12 12:34:11.489 +0000', null, 'SHOP_ADMIN');
INSERT INTO `datatables` VALUES ('H201506', 'PuHao', 'shop manager access', 'FREEZN', '2016-12-13 07:05:57.960 +0000', '2016-12-13 07:05:57.960 +0000', 'NORMAL');
INSERT INTO `datatables` VALUES ('N201703', 'Joyce', 'normal access', 'NORMAL', '2016-12-13 07:05:57.960 +0000', '2016-12-13 07:05:57.960 +0000', 'NORMAL');
INSERT INTO `datatables` VALUES ('S201102', 'Joke', 'fffffkkk', 'FREEZN', '2017-03-12 09:23:55.953 +0000', null, 'NORMAL');
INSERT INTO `datatables` VALUES ('S201202', 'Oliver', 'super manager access', 'NORMAL', '2016-12-13 07:05:57.960 +0000', '2016-12-13 07:05:57.960 +0000', 'SUPER_ADMIN');
