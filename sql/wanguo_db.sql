/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : wanguo_db

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/03/2023 19:10:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userAccount` varchar(256) NOT NULL COMMENT '账号',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `status` varchar(256) NOT NULL DEFAULT '0' COMMENT '状态（0-未登陆，1-已登陆）',
  `email` varchar(256) NOT NULL COMMENT '邮箱',
  `pastTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDeleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_index` (`userAccount`),
  KEY `union_index` (`userAccount`,`status`,`pastTime`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of User
-- ----------------------------
BEGIN;
INSERT INTO `User` VALUES (27, '531802633@qq.com', '2ab586aa837c7211fa2fa31d8be4015f', '1', '531802633@qq.com', '2024-06-06 14:50:13', '2023-03-01 14:50:12', '2023-03-01 18:43:16', 0);
INSERT INTO `User` VALUES (48, '17089098018', '12345678', '0', 'georgeanna.stroman@gmail.com', '2023-02-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:09:43', 1);
INSERT INTO `User` VALUES (49, '15082734369', '12345678', '0', 'willis.hermann@hotmail.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (50, '15080788910', '12345678', '0', 'solomon.murazik@gmail.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (51, '17141839446', '12345678', '0', 'janette.murray@gmail.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (52, '17813137191', '12345678', '0', 'vita.farrell@hotmail.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (53, '17330293565', '12345678', '0', 'clementina.braun@hotmail.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (54, '17236440889', '12345678', '0', 'earnest.corwin@yahoo.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (55, '17258537513', '12345678', '0', 'carolyn.farrell@gmail.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (56, '17161739428', '12345678', '0', 'queen.waelchi@yahoo.com', '2023-03-01 16:59:08', '2023-03-01 16:59:08', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (57, '14771371870', '12345678', '0', 'alonzo.hyatt@yahoo.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (58, '17277798136', '12345678', '0', 'dario.schoen@hotmail.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (59, '14771189764', '12345678', '0', 'sherika.cummerata@yahoo.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (60, '17105307182', '12345678', '0', 'julieta.simonis@yahoo.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (61, '15514089435', '12345678', '0', 'antonia.shields@gmail.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (62, '15826202994', '12345678', '0', 'hilton.kshlerin@gmail.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (63, '13026863120', '12345678', '0', 'leora.ruecker@yahoo.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (64, '15878799423', '12345678', '0', 'jaqueline.franecki@gmail.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (65, '15770741433', '12345678', '0', 'ladonna.boyer@gmail.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (66, '17665141893', '12345678', '0', 'dyan.howell@gmail.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:08:19', 0);
INSERT INTO `User` VALUES (67, '17881518993', '12345678', '1', 'darwin.nikolaus@yahoo.com', '2023-03-01 16:59:09', '2023-03-01 16:59:09', '2023-03-01 17:48:59', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
