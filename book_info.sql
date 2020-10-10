/*
Navicat MySQL Data Transfer

Source Server         : 3306
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : book_system

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2020-09-29 09:20:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info` (
  `num` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `subscribeTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `isSubscirbe` tinyint DEFAULT NULL,
  `isScrap` tinyint DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES ('1', '基于位置的社会化网络推荐算法研究', '数理统计', '刘树栋 ', 'imags/1.jpg', '无', '0', '0');
INSERT INTO `book_info` VALUES ('2', '悖论：人类理性之谜', '悖论', '张建军', 'imags/2.jpg', '无', '0', '0');
INSERT INTO `book_info` VALUES ('3', '局外人', '小说', '阿尔贝•加缪', 'imags/3.jpg', '无', '0', '0');
INSERT INTO `book_info` VALUES ('4', '悲惨世界', '小说', '雨果', 'imags/4.jpg', '无', '0', '0');
INSERT INTO `book_info` VALUES ('5', '月亮与六便士', '小说', '毛姆', 'imags/5.jpg', '无', '0', '0');
INSERT INTO `book_info` VALUES ('6', '在路上', '小说', '杰克·凯鲁亚克', 'imags/6.jpg', '无', '0', '0');
INSERT INTO `book_info` VALUES ('7', '5G时代：生活方式和商业模式的大变革', '科学', '龟井卓也', 'imags/7.jpg', '无', '0', '0');
INSERT INTO `book_info` VALUES ('8', '未来简史', '科学', '尤瓦尔·赫拉利', 'imags/8.jpg', '无', '0', '0');
