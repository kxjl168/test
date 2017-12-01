/*
Navicat MySQL Data Transfer

Source Server         : 10.204.37.192-消息服务器web3
Source Server Version : 50629
Source Host           : 10.204.37.192:3306
Source Database       : gserver

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2017-12-01 17:43:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for company_info
-- ----------------------------
DROP TABLE IF EXISTS `company_info`;
CREATE TABLE `company_info` (
  `company_name` varchar(256) DEFAULT NULL,
  `recordid` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `desc_info` varchar(256) DEFAULT NULL COMMENT 'desc',
  `pass` varchar(64) DEFAULT NULL COMMENT 'pass',
  `accountid` varchar(32) DEFAULT NULL COMMENT 'userid',
  `ip_desc` varchar(256) DEFAULT NULL COMMENT 'ip_desc',
  `ip_refresh_interval` varchar(256) DEFAULT NULL COMMENT '出口IP刷新周期',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  KEY `Index_1` (`recordid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='公司账号';

-- ----------------------------
-- Records of company_info
-- ----------------------------
INSERT INTO `company_info` VALUES ('111', '2', '111', '111', '111', '111', '111', '2017-12-01 15:12:33', null);
INSERT INTO `company_info` VALUES ('测试k公司', '3', '测试', '123321', 'c1', '192.168.1.1-192.1681.33', '50', '2017-12-01 15:30:44', null);
INSERT INTO `company_info` VALUES ('汉庭有限公司', '4', '1111', '123321', 'c2', '222.11-123123', '55', '2017-12-01 15:42:03', null);

-- ----------------------------
-- Table structure for phoneaccount_info
-- ----------------------------
DROP TABLE IF EXISTS `phoneaccount_info`;
CREATE TABLE `phoneaccount_info` (
  `recordid` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `accountid` varchar(32) NOT NULL COMMENT 'userid',
  `pass` varchar(64) DEFAULT NULL COMMENT 'pass',
  `ip_refresh_interval` varchar(256) DEFAULT NULL COMMENT '出口IP刷新周期',
  `city` varchar(64) DEFAULT NULL COMMENT '出口位置',
  `ip` varchar(128) DEFAULT NULL COMMENT 'ip',
  `company_userid` varchar(256) DEFAULT NULL COMMENT '所属公司user_id',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`recordid`),
  KEY `Index_1` (`recordid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='手机账号';

-- ----------------------------
-- Records of phoneaccount_info
-- ----------------------------
INSERT INTO `phoneaccount_info` VALUES ('2', 'tsets11', '123', '11', '上海', null, 'c2', '2017-12-01 16:21:25', null);
INSERT INTO `phoneaccount_info` VALUES ('8', 'test1', '123321', '', '南京', '192.6.1.1', 'c1', '2017-12-01 17:09:48', '2017-12-01 17:12:53');
INSERT INTO `phoneaccount_info` VALUES ('11', '1231', '231', '231', '南京', null, '111', '2017-12-01 17:39:04', null);

-- ----------------------------
-- Table structure for route_info
-- ----------------------------
DROP TABLE IF EXISTS `route_info`;
CREATE TABLE `route_info` (
  `recordid` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `routeid` varchar(32) NOT NULL COMMENT 'id',
  `ip` varchar(128) DEFAULT NULL COMMENT '路由器出口ip',
  `city` varchar(32) DEFAULT NULL COMMENT '所在城市',
  `onlinetime` varchar(64) DEFAULT NULL COMMENT '上线时间',
  `offlinetime` varchar(64) DEFAULT NULL COMMENT '下线时间',
  KEY `Index_1` (`recordid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='路由器记录表';

-- ----------------------------
-- Records of route_info
-- ----------------------------
INSERT INTO `route_info` VALUES ('1', 'sdfada', '192.168.1.12', '南京', '11111111', null);
INSERT INTO `route_info` VALUES ('2', 'sdfada111', '192.168.1.112', '南京22', '11111111', null);
INSERT INTO `route_info` VALUES ('3', 'kkkk111', '192.168.2.212', '无锡', '4444', null);
INSERT INTO `route_info` VALUES ('4', 'sss111', '292.168.2.212', '上海', '4444', null);
INSERT INTO `route_info` VALUES ('5', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('6', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('7', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('8', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('9', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('10', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('11', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('12', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('13', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('14', 'hh444', '192.268.2.212', '义乌', '12312312312', null);
INSERT INTO `route_info` VALUES ('15', 'hh44422', '192.268.2.212', '义乌22', '12312312312', null);
INSERT INTO `route_info` VALUES ('16', 'hh444111', '192.268.2.212', '义乌22', '12312312312', null);
INSERT INTO `route_info` VALUES ('17', 'hh44433', '192.268.2.212', '义乌22', '12312312312', null);

-- ----------------------------
-- Table structure for trans_log
-- ----------------------------
DROP TABLE IF EXISTS `trans_log`;
CREATE TABLE `trans_log` (
  `recordid` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `phone_account_use_id` varchar(32) DEFAULT NULL COMMENT '手机账号',
  `phone_ip` varchar(64) DEFAULT NULL COMMENT '手机ip',
  `device_id` varchar(256) DEFAULT NULL COMMENT '出口路由器id',
  `device_ip` varchar(256) DEFAULT NULL COMMENT '出口路由器ip',
  `city` varchar(64) DEFAULT NULL COMMENT '出口城市',
  `time` varchar(64) DEFAULT NULL COMMENT '时间',
  KEY `Index_1` (`recordid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流量转发日志';

-- ----------------------------
-- Records of trans_log
-- ----------------------------
