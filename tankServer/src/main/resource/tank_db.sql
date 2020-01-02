/*
Navicat MySQL Data Transfer

Source Server         : tank
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : tank_db

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2018-01-12 18:31:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for map
-- ----------------------------
DROP TABLE IF EXISTS `map`;
CREATE TABLE `map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of map
-- ----------------------------
INSERT INTO `map` VALUES ('1', '第一关');
INSERT INTO `map` VALUES ('2', '第二关');
INSERT INTO `map` VALUES ('3', '第三关');
INSERT INTO `map` VALUES ('4', '第四关');

-- ----------------------------
-- Table structure for map_element
-- ----------------------------
DROP TABLE IF EXISTS `map_element`;
CREATE TABLE `map_element` (
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `map_id` int(11) NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT '地图中的物体的类型(1:砖块,2:河道,3:钢铁,4:电脑坦克,5:玩家坦克)',
  KEY `mapid_index` (`map_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of map_element
-- ----------------------------
INSERT INTO `map_element` VALUES ('70', '470', '1', '1');
INSERT INTO `map_element` VALUES ('110', '470', '1', '1');
INSERT INTO `map_element` VALUES ('150', '470', '1', '1');
INSERT INTO `map_element` VALUES ('190', '470', '1', '1');
INSERT INTO `map_element` VALUES ('230', '470', '1', '1');
INSERT INTO `map_element` VALUES ('270', '470', '1', '1');
INSERT INTO `map_element` VALUES ('310', '470', '1', '1');
INSERT INTO `map_element` VALUES ('350', '470', '1', '1');
INSERT INTO `map_element` VALUES ('390', '470', '1', '1');
INSERT INTO `map_element` VALUES ('430', '470', '1', '1');
INSERT INTO `map_element` VALUES ('470', '470', '1', '1');
INSERT INTO `map_element` VALUES ('510', '470', '1', '1');
INSERT INTO `map_element` VALUES ('550', '470', '1', '1');
INSERT INTO `map_element` VALUES ('590', '470', '1', '1');
INSERT INTO `map_element` VALUES ('60', '130', '1', '2');
INSERT INTO `map_element` VALUES ('80', '130', '1', '2');
INSERT INTO `map_element` VALUES ('100', '130', '1', '2');
INSERT INTO `map_element` VALUES ('120', '130', '1', '2');
INSERT INTO `map_element` VALUES ('140', '130', '1', '2');
INSERT INTO `map_element` VALUES ('160', '130', '1', '2');
INSERT INTO `map_element` VALUES ('180', '130', '1', '2');
INSERT INTO `map_element` VALUES ('200', '130', '1', '2');
INSERT INTO `map_element` VALUES ('220', '130', '1', '2');
INSERT INTO `map_element` VALUES ('240', '130', '1', '2');
INSERT INTO `map_element` VALUES ('260', '130', '1', '2');
INSERT INTO `map_element` VALUES ('280', '130', '1', '2');
INSERT INTO `map_element` VALUES ('300', '130', '1', '2');
INSERT INTO `map_element` VALUES ('320', '130', '1', '2');
INSERT INTO `map_element` VALUES ('340', '130', '1', '2');
INSERT INTO `map_element` VALUES ('360', '130', '1', '2');
INSERT INTO `map_element` VALUES ('380', '130', '1', '2');
INSERT INTO `map_element` VALUES ('400', '130', '1', '2');
INSERT INTO `map_element` VALUES ('420', '130', '1', '2');
INSERT INTO `map_element` VALUES ('440', '130', '1', '2');
INSERT INTO `map_element` VALUES ('460', '130', '1', '2');
INSERT INTO `map_element` VALUES ('480', '130', '1', '2');
INSERT INTO `map_element` VALUES ('500', '130', '1', '2');
INSERT INTO `map_element` VALUES ('520', '130', '1', '2');
INSERT INTO `map_element` VALUES ('540', '130', '1', '2');
INSERT INTO `map_element` VALUES ('100', '280', '1', '2');
INSERT INTO `map_element` VALUES ('100', '300', '1', '2');
INSERT INTO `map_element` VALUES ('100', '320', '1', '2');
INSERT INTO `map_element` VALUES ('100', '340', '1', '2');
INSERT INTO `map_element` VALUES ('100', '360', '1', '2');
INSERT INTO `map_element` VALUES ('100', '380', '1', '2');
INSERT INTO `map_element` VALUES ('100', '400', '1', '2');
INSERT INTO `map_element` VALUES ('60', '60', '1', '2');
INSERT INTO `map_element` VALUES ('80', '60', '1', '2');
INSERT INTO `map_element` VALUES ('100', '60', '1', '2');
INSERT INTO `map_element` VALUES ('120', '60', '1', '2');
INSERT INTO `map_element` VALUES ('140', '60', '1', '2');
INSERT INTO `map_element` VALUES ('160', '60', '1', '2');
INSERT INTO `map_element` VALUES ('180', '60', '1', '2');
INSERT INTO `map_element` VALUES ('200', '60', '1', '2');
INSERT INTO `map_element` VALUES ('220', '60', '1', '2');
INSERT INTO `map_element` VALUES ('240', '60', '1', '2');
INSERT INTO `map_element` VALUES ('260', '60', '1', '2');
INSERT INTO `map_element` VALUES ('340', '60', '1', '2');
INSERT INTO `map_element` VALUES ('360', '60', '1', '2');
INSERT INTO `map_element` VALUES ('380', '60', '1', '2');
INSERT INTO `map_element` VALUES ('400', '60', '1', '2');
INSERT INTO `map_element` VALUES ('420', '60', '1', '2');
INSERT INTO `map_element` VALUES ('440', '60', '1', '2');
INSERT INTO `map_element` VALUES ('460', '60', '1', '2');
INSERT INTO `map_element` VALUES ('480', '60', '1', '2');
INSERT INTO `map_element` VALUES ('500', '60', '1', '2');
INSERT INTO `map_element` VALUES ('520', '60', '1', '2');
INSERT INTO `map_element` VALUES ('540', '60', '1', '2');
INSERT INTO `map_element` VALUES ('30', '540', '1', '3');
INSERT INTO `map_element` VALUES ('110', '540', '1', '3');
INSERT INTO `map_element` VALUES ('150', '540', '1', '3');
INSERT INTO `map_element` VALUES ('230', '540', '1', '3');
INSERT INTO `map_element` VALUES ('270', '540', '1', '3');
INSERT INTO `map_element` VALUES ('350', '540', '1', '3');
INSERT INTO `map_element` VALUES ('390', '540', '1', '3');
INSERT INTO `map_element` VALUES ('470', '540', '1', '3');
INSERT INTO `map_element` VALUES ('510', '540', '1', '3');
INSERT INTO `map_element` VALUES ('10', '540', '1', '3');
INSERT INTO `map_element` VALUES ('10', '200', '1', '3');
INSERT INTO `map_element` VALUES ('30', '200', '1', '3');
INSERT INTO `map_element` VALUES ('70', '200', '1', '3');
INSERT INTO `map_element` VALUES ('90', '200', '1', '3');
INSERT INTO `map_element` VALUES ('130', '200', '1', '3');
INSERT INTO `map_element` VALUES ('150', '200', '1', '3');
INSERT INTO `map_element` VALUES ('310', '200', '1', '3');
INSERT INTO `map_element` VALUES ('330', '200', '1', '3');
INSERT INTO `map_element` VALUES ('370', '200', '1', '3');
INSERT INTO `map_element` VALUES ('390', '200', '1', '3');
INSERT INTO `map_element` VALUES ('430', '200', '1', '3');
INSERT INTO `map_element` VALUES ('450', '200', '1', '3');
INSERT INTO `map_element` VALUES ('490', '200', '1', '3');
INSERT INTO `map_element` VALUES ('510', '200', '1', '3');
INSERT INTO `map_element` VALUES ('225', '150', '1', '3');
INSERT INTO `map_element` VALUES ('225', '170', '1', '3');
INSERT INTO `map_element` VALUES ('290', '200', '1', '3');
INSERT INTO `map_element` VALUES ('290', '290', '1', '3');
INSERT INTO `map_element` VALUES ('310', '290', '1', '3');
INSERT INTO `map_element` VALUES ('290', '310', '1', '3');
INSERT INTO `map_element` VALUES ('310', '310', '1', '3');
INSERT INTO `map_element` VALUES ('590', '400', '1', '3');
INSERT INTO `map_element` VALUES ('570', '400', '1', '3');
INSERT INTO `map_element` VALUES ('200', '290', '1', '1');
INSERT INTO `map_element` VALUES ('220', '290', '1', '1');
INSERT INTO `map_element` VALUES ('200', '310', '1', '1');
INSERT INTO `map_element` VALUES ('220', '310', '1', '1');
INSERT INTO `map_element` VALUES ('380', '290', '1', '2');
INSERT INTO `map_element` VALUES ('400', '290', '1', '2');
INSERT INTO `map_element` VALUES ('380', '310', '1', '2');
INSERT INTO `map_element` VALUES ('400', '310', '1', '2');
INSERT INTO `map_element` VALUES ('192', '168', '1', '4');
INSERT INTO `map_element` VALUES ('214', '210', '1', '5');
INSERT INTO `map_element` VALUES ('550', '550', '1', '4');
INSERT INTO `map_element` VALUES ('440', '440', '1', '4');
INSERT INTO `map_element` VALUES ('350', '350', '1', '4');
INSERT INTO `map_element` VALUES ('550', '20', '1', '5');
INSERT INTO `map_element` VALUES ('80', '60', '2', '3');
INSERT INTO `map_element` VALUES ('120', '60', '2', '3');
INSERT INTO `map_element` VALUES ('160', '60', '2', '3');
INSERT INTO `map_element` VALUES ('200', '60', '2', '3');
INSERT INTO `map_element` VALUES ('240', '60', '2', '3');
INSERT INTO `map_element` VALUES ('360', '60', '2', '3');
INSERT INTO `map_element` VALUES ('400', '60', '2', '3');
INSERT INTO `map_element` VALUES ('440', '60', '2', '3');
INSERT INTO `map_element` VALUES ('480', '60', '2', '3');
INSERT INTO `map_element` VALUES ('520', '60', '2', '3');
INSERT INTO `map_element` VALUES ('60', '540', '2', '3');
INSERT INTO `map_element` VALUES ('80', '540', '2', '3');
INSERT INTO `map_element` VALUES ('100', '540', '2', '3');
INSERT INTO `map_element` VALUES ('120', '540', '2', '3');
INSERT INTO `map_element` VALUES ('140', '540', '2', '3');
INSERT INTO `map_element` VALUES ('160', '540', '2', '3');
INSERT INTO `map_element` VALUES ('180', '540', '2', '3');
INSERT INTO `map_element` VALUES ('200', '540', '2', '3');
INSERT INTO `map_element` VALUES ('220', '540', '2', '3');
INSERT INTO `map_element` VALUES ('240', '540', '2', '3');
INSERT INTO `map_element` VALUES ('260', '540', '2', '3');
INSERT INTO `map_element` VALUES ('340', '540', '2', '3');
INSERT INTO `map_element` VALUES ('360', '540', '2', '3');
INSERT INTO `map_element` VALUES ('380', '540', '2', '3');
INSERT INTO `map_element` VALUES ('400', '540', '2', '3');
INSERT INTO `map_element` VALUES ('420', '540', '2', '3');
INSERT INTO `map_element` VALUES ('440', '540', '2', '3');
INSERT INTO `map_element` VALUES ('460', '540', '2', '3');
INSERT INTO `map_element` VALUES ('480', '540', '2', '3');
INSERT INTO `map_element` VALUES ('500', '540', '2', '3');
INSERT INTO `map_element` VALUES ('520', '540', '2', '3');
INSERT INTO `map_element` VALUES ('540', '540', '2', '3');
INSERT INTO `map_element` VALUES ('60', '80', '2', '2');
INSERT INTO `map_element` VALUES ('60', '120', '2', '2');
INSERT INTO `map_element` VALUES ('60', '160', '2', '2');
INSERT INTO `map_element` VALUES ('60', '200', '2', '2');
INSERT INTO `map_element` VALUES ('60', '240', '2', '2');
INSERT INTO `map_element` VALUES ('60', '360', '2', '2');
INSERT INTO `map_element` VALUES ('60', '400', '2', '2');
INSERT INTO `map_element` VALUES ('60', '440', '2', '2');
INSERT INTO `map_element` VALUES ('60', '480', '2', '2');
INSERT INTO `map_element` VALUES ('60', '520', '2', '2');
INSERT INTO `map_element` VALUES ('540', '80', '2', '2');
INSERT INTO `map_element` VALUES ('540', '120', '2', '2');
INSERT INTO `map_element` VALUES ('540', '160', '2', '2');
INSERT INTO `map_element` VALUES ('540', '200', '2', '2');
INSERT INTO `map_element` VALUES ('540', '240', '2', '2');
INSERT INTO `map_element` VALUES ('540', '360', '2', '2');
INSERT INTO `map_element` VALUES ('540', '400', '2', '2');
INSERT INTO `map_element` VALUES ('540', '440', '2', '2');
INSERT INTO `map_element` VALUES ('540', '480', '2', '2');
INSERT INTO `map_element` VALUES ('540', '520', '2', '2');
INSERT INTO `map_element` VALUES ('290', '290', '2', '2');
INSERT INTO `map_element` VALUES ('310', '290', '2', '2');
INSERT INTO `map_element` VALUES ('290', '310', '2', '2');
INSERT INTO `map_element` VALUES ('310', '310', '2', '2');
INSERT INTO `map_element` VALUES ('60', '60', '3', '1');
INSERT INTO `map_element` VALUES ('80', '60', '3', '1');
INSERT INTO `map_element` VALUES ('100', '60', '3', '1');
INSERT INTO `map_element` VALUES ('120', '60', '3', '1');
INSERT INTO `map_element` VALUES ('140', '60', '3', '1');
INSERT INTO `map_element` VALUES ('160', '60', '3', '1');
INSERT INTO `map_element` VALUES ('180', '60', '3', '1');
INSERT INTO `map_element` VALUES ('200', '60', '3', '1');
INSERT INTO `map_element` VALUES ('220', '60', '3', '1');
INSERT INTO `map_element` VALUES ('240', '60', '3', '1');
INSERT INTO `map_element` VALUES ('260', '60', '3', '1');
INSERT INTO `map_element` VALUES ('280', '60', '3', '1');
INSERT INTO `map_element` VALUES ('300', '60', '3', '1');
INSERT INTO `map_element` VALUES ('320', '60', '3', '1');
INSERT INTO `map_element` VALUES ('340', '60', '3', '1');
INSERT INTO `map_element` VALUES ('360', '60', '3', '1');
INSERT INTO `map_element` VALUES ('380', '60', '3', '1');
INSERT INTO `map_element` VALUES ('400', '60', '3', '1');
INSERT INTO `map_element` VALUES ('420', '60', '3', '1');
INSERT INTO `map_element` VALUES ('440', '60', '3', '1');
INSERT INTO `map_element` VALUES ('460', '60', '3', '1');
INSERT INTO `map_element` VALUES ('480', '60', '3', '1');
INSERT INTO `map_element` VALUES ('500', '60', '3', '1');
INSERT INTO `map_element` VALUES ('520', '60', '3', '1');
INSERT INTO `map_element` VALUES ('540', '60', '3', '1');
INSERT INTO `map_element` VALUES ('60', '140', '3', '1');
INSERT INTO `map_element` VALUES ('80', '140', '3', '1');
INSERT INTO `map_element` VALUES ('100', '140', '3', '1');
INSERT INTO `map_element` VALUES ('120', '140', '3', '1');
INSERT INTO `map_element` VALUES ('140', '140', '3', '1');
INSERT INTO `map_element` VALUES ('160', '140', '3', '1');
INSERT INTO `map_element` VALUES ('180', '140', '3', '1');
INSERT INTO `map_element` VALUES ('200', '140', '3', '1');
INSERT INTO `map_element` VALUES ('220', '140', '3', '1');
INSERT INTO `map_element` VALUES ('240', '140', '3', '1');
INSERT INTO `map_element` VALUES ('260', '140', '3', '1');
INSERT INTO `map_element` VALUES ('280', '140', '3', '1');
INSERT INTO `map_element` VALUES ('300', '140', '3', '1');
INSERT INTO `map_element` VALUES ('320', '140', '3', '1');
INSERT INTO `map_element` VALUES ('340', '140', '3', '1');
INSERT INTO `map_element` VALUES ('360', '140', '3', '1');
INSERT INTO `map_element` VALUES ('380', '140', '3', '1');
INSERT INTO `map_element` VALUES ('400', '140', '3', '1');
INSERT INTO `map_element` VALUES ('420', '140', '3', '1');
INSERT INTO `map_element` VALUES ('440', '140', '3', '1');
INSERT INTO `map_element` VALUES ('460', '140', '3', '1');
INSERT INTO `map_element` VALUES ('480', '140', '3', '1');
INSERT INTO `map_element` VALUES ('500', '140', '3', '1');
INSERT INTO `map_element` VALUES ('520', '140', '3', '1');
INSERT INTO `map_element` VALUES ('540', '140', '3', '1');
INSERT INTO `map_element` VALUES ('60', '220', '3', '1');
INSERT INTO `map_element` VALUES ('80', '220', '3', '1');
INSERT INTO `map_element` VALUES ('100', '220', '3', '1');
INSERT INTO `map_element` VALUES ('120', '220', '3', '1');
INSERT INTO `map_element` VALUES ('140', '220', '3', '1');
INSERT INTO `map_element` VALUES ('160', '220', '3', '1');
INSERT INTO `map_element` VALUES ('180', '220', '3', '1');
INSERT INTO `map_element` VALUES ('200', '220', '3', '1');
INSERT INTO `map_element` VALUES ('220', '220', '3', '1');
INSERT INTO `map_element` VALUES ('240', '220', '3', '1');
INSERT INTO `map_element` VALUES ('260', '220', '3', '1');
INSERT INTO `map_element` VALUES ('280', '220', '3', '1');
INSERT INTO `map_element` VALUES ('300', '220', '3', '1');
INSERT INTO `map_element` VALUES ('320', '220', '3', '1');
INSERT INTO `map_element` VALUES ('340', '220', '3', '1');
INSERT INTO `map_element` VALUES ('360', '220', '3', '1');
INSERT INTO `map_element` VALUES ('380', '220', '3', '1');
INSERT INTO `map_element` VALUES ('400', '220', '3', '1');
INSERT INTO `map_element` VALUES ('420', '220', '3', '1');
INSERT INTO `map_element` VALUES ('440', '220', '3', '1');
INSERT INTO `map_element` VALUES ('460', '220', '3', '1');
INSERT INTO `map_element` VALUES ('480', '220', '3', '1');
INSERT INTO `map_element` VALUES ('500', '220', '3', '1');
INSERT INTO `map_element` VALUES ('520', '220', '3', '1');
INSERT INTO `map_element` VALUES ('540', '220', '3', '1');
INSERT INTO `map_element` VALUES ('60', '460', '3', '1');
INSERT INTO `map_element` VALUES ('80', '460', '3', '1');
INSERT INTO `map_element` VALUES ('100', '460', '3', '1');
INSERT INTO `map_element` VALUES ('120', '460', '3', '1');
INSERT INTO `map_element` VALUES ('140', '460', '3', '1');
INSERT INTO `map_element` VALUES ('160', '460', '3', '1');
INSERT INTO `map_element` VALUES ('180', '460', '3', '1');
INSERT INTO `map_element` VALUES ('200', '460', '3', '1');
INSERT INTO `map_element` VALUES ('220', '460', '3', '1');
INSERT INTO `map_element` VALUES ('240', '460', '3', '1');
INSERT INTO `map_element` VALUES ('260', '460', '3', '1');
INSERT INTO `map_element` VALUES ('340', '460', '3', '1');
INSERT INTO `map_element` VALUES ('360', '460', '3', '1');
INSERT INTO `map_element` VALUES ('380', '460', '3', '1');
INSERT INTO `map_element` VALUES ('400', '460', '3', '1');
INSERT INTO `map_element` VALUES ('420', '460', '3', '1');
INSERT INTO `map_element` VALUES ('440', '460', '3', '1');
INSERT INTO `map_element` VALUES ('460', '460', '3', '1');
INSERT INTO `map_element` VALUES ('480', '460', '3', '1');
INSERT INTO `map_element` VALUES ('500', '460', '3', '1');
INSERT INTO `map_element` VALUES ('520', '460', '3', '1');
INSERT INTO `map_element` VALUES ('540', '460', '3', '1');
INSERT INTO `map_element` VALUES ('60', '540', '3', '1');
INSERT INTO `map_element` VALUES ('80', '540', '3', '1');
INSERT INTO `map_element` VALUES ('100', '540', '3', '1');
INSERT INTO `map_element` VALUES ('120', '540', '3', '1');
INSERT INTO `map_element` VALUES ('140', '540', '3', '1');
INSERT INTO `map_element` VALUES ('160', '540', '3', '1');
INSERT INTO `map_element` VALUES ('180', '540', '3', '1');
INSERT INTO `map_element` VALUES ('200', '540', '3', '1');
INSERT INTO `map_element` VALUES ('220', '540', '3', '1');
INSERT INTO `map_element` VALUES ('240', '540', '3', '1');
INSERT INTO `map_element` VALUES ('260', '540', '3', '1');
INSERT INTO `map_element` VALUES ('340', '540', '3', '1');
INSERT INTO `map_element` VALUES ('360', '540', '3', '1');
INSERT INTO `map_element` VALUES ('380', '540', '3', '1');
INSERT INTO `map_element` VALUES ('400', '540', '3', '1');
INSERT INTO `map_element` VALUES ('420', '540', '3', '1');
INSERT INTO `map_element` VALUES ('440', '540', '3', '1');
INSERT INTO `map_element` VALUES ('460', '540', '3', '1');
INSERT INTO `map_element` VALUES ('480', '540', '3', '1');
INSERT INTO `map_element` VALUES ('500', '540', '3', '1');
INSERT INTO `map_element` VALUES ('520', '540', '3', '1');
INSERT INTO `map_element` VALUES ('540', '540', '3', '1');
INSERT INTO `map_element` VALUES ('60', '60', '3', '1');
INSERT INTO `map_element` VALUES ('60', '80', '3', '1');
INSERT INTO `map_element` VALUES ('60', '100', '3', '1');
INSERT INTO `map_element` VALUES ('60', '120', '3', '1');
INSERT INTO `map_element` VALUES ('60', '140', '3', '1');
INSERT INTO `map_element` VALUES ('60', '160', '3', '1');
INSERT INTO `map_element` VALUES ('60', '180', '3', '1');
INSERT INTO `map_element` VALUES ('60', '200', '3', '1');
INSERT INTO `map_element` VALUES ('60', '220', '3', '1');
INSERT INTO `map_element` VALUES ('60', '240', '3', '1');
INSERT INTO `map_element` VALUES ('60', '260', '3', '1');
INSERT INTO `map_element` VALUES ('60', '280', '3', '1');
INSERT INTO `map_element` VALUES ('60', '300', '3', '1');
INSERT INTO `map_element` VALUES ('60', '320', '3', '1');
INSERT INTO `map_element` VALUES ('60', '340', '3', '1');
INSERT INTO `map_element` VALUES ('60', '360', '3', '1');
INSERT INTO `map_element` VALUES ('60', '380', '3', '1');
INSERT INTO `map_element` VALUES ('60', '400', '3', '1');
INSERT INTO `map_element` VALUES ('60', '420', '3', '1');
INSERT INTO `map_element` VALUES ('60', '440', '3', '1');
INSERT INTO `map_element` VALUES ('60', '460', '3', '1');
INSERT INTO `map_element` VALUES ('60', '480', '3', '1');
INSERT INTO `map_element` VALUES ('60', '500', '3', '1');
INSERT INTO `map_element` VALUES ('60', '520', '3', '1');
INSERT INTO `map_element` VALUES ('60', '540', '3', '1');
INSERT INTO `map_element` VALUES ('540', '60', '3', '1');
INSERT INTO `map_element` VALUES ('540', '80', '3', '1');
INSERT INTO `map_element` VALUES ('540', '100', '3', '1');
INSERT INTO `map_element` VALUES ('540', '120', '3', '1');
INSERT INTO `map_element` VALUES ('540', '140', '3', '1');
INSERT INTO `map_element` VALUES ('540', '160', '3', '1');
INSERT INTO `map_element` VALUES ('540', '180', '3', '1');
INSERT INTO `map_element` VALUES ('540', '200', '3', '1');
INSERT INTO `map_element` VALUES ('540', '220', '3', '1');
INSERT INTO `map_element` VALUES ('540', '240', '3', '1');
INSERT INTO `map_element` VALUES ('540', '260', '3', '1');
INSERT INTO `map_element` VALUES ('540', '280', '3', '1');
INSERT INTO `map_element` VALUES ('540', '300', '3', '1');
INSERT INTO `map_element` VALUES ('540', '320', '3', '1');
INSERT INTO `map_element` VALUES ('540', '340', '3', '1');
INSERT INTO `map_element` VALUES ('540', '360', '3', '1');
INSERT INTO `map_element` VALUES ('540', '380', '3', '1');
INSERT INTO `map_element` VALUES ('540', '400', '3', '1');
INSERT INTO `map_element` VALUES ('540', '420', '3', '1');
INSERT INTO `map_element` VALUES ('540', '440', '3', '1');
INSERT INTO `map_element` VALUES ('540', '460', '3', '1');
INSERT INTO `map_element` VALUES ('540', '480', '3', '1');
INSERT INTO `map_element` VALUES ('540', '500', '3', '1');
INSERT INTO `map_element` VALUES ('540', '520', '3', '1');
INSERT INTO `map_element` VALUES ('540', '540', '3', '1');
INSERT INTO `map_element` VALUES ('290', '290', '3', '1');
INSERT INTO `map_element` VALUES ('310', '290', '3', '1');
INSERT INTO `map_element` VALUES ('290', '310', '3', '1');
INSERT INTO `map_element` VALUES ('310', '310', '3', '1');
INSERT INTO `map_element` VALUES ('60', '60', '4', '2');
INSERT INTO `map_element` VALUES ('80', '60', '4', '2');
INSERT INTO `map_element` VALUES ('100', '60', '4', '2');
INSERT INTO `map_element` VALUES ('120', '60', '4', '2');
INSERT INTO `map_element` VALUES ('140', '60', '4', '2');
INSERT INTO `map_element` VALUES ('160', '60', '4', '2');
INSERT INTO `map_element` VALUES ('180', '60', '4', '2');
INSERT INTO `map_element` VALUES ('200', '60', '4', '2');
INSERT INTO `map_element` VALUES ('220', '60', '4', '2');
INSERT INTO `map_element` VALUES ('240', '60', '4', '2');
INSERT INTO `map_element` VALUES ('260', '60', '4', '2');
INSERT INTO `map_element` VALUES ('340', '60', '4', '2');
INSERT INTO `map_element` VALUES ('360', '60', '4', '2');
INSERT INTO `map_element` VALUES ('380', '60', '4', '2');
INSERT INTO `map_element` VALUES ('400', '60', '4', '2');
INSERT INTO `map_element` VALUES ('420', '60', '4', '2');
INSERT INTO `map_element` VALUES ('440', '60', '4', '2');
INSERT INTO `map_element` VALUES ('460', '60', '4', '2');
INSERT INTO `map_element` VALUES ('480', '60', '4', '2');
INSERT INTO `map_element` VALUES ('500', '60', '4', '2');
INSERT INTO `map_element` VALUES ('520', '60', '4', '2');
INSERT INTO `map_element` VALUES ('540', '60', '4', '2');
INSERT INTO `map_element` VALUES ('60', '460', '4', '2');
INSERT INTO `map_element` VALUES ('80', '460', '4', '2');
INSERT INTO `map_element` VALUES ('100', '460', '4', '2');
INSERT INTO `map_element` VALUES ('120', '460', '4', '2');
INSERT INTO `map_element` VALUES ('140', '460', '4', '2');
INSERT INTO `map_element` VALUES ('160', '460', '4', '2');
INSERT INTO `map_element` VALUES ('180', '460', '4', '2');
INSERT INTO `map_element` VALUES ('200', '460', '4', '2');
INSERT INTO `map_element` VALUES ('220', '460', '4', '2');
INSERT INTO `map_element` VALUES ('240', '460', '4', '2');
INSERT INTO `map_element` VALUES ('260', '460', '4', '2');
INSERT INTO `map_element` VALUES ('340', '460', '4', '2');
INSERT INTO `map_element` VALUES ('360', '460', '4', '2');
INSERT INTO `map_element` VALUES ('380', '460', '4', '2');
INSERT INTO `map_element` VALUES ('400', '460', '4', '2');
INSERT INTO `map_element` VALUES ('420', '460', '4', '2');
INSERT INTO `map_element` VALUES ('440', '460', '4', '2');
INSERT INTO `map_element` VALUES ('460', '460', '4', '2');
INSERT INTO `map_element` VALUES ('480', '460', '4', '2');
INSERT INTO `map_element` VALUES ('500', '460', '4', '2');
INSERT INTO `map_element` VALUES ('520', '460', '4', '2');
INSERT INTO `map_element` VALUES ('540', '460', '4', '2');
INSERT INTO `map_element` VALUES ('60', '140', '4', '2');
INSERT INTO `map_element` VALUES ('80', '140', '4', '2');
INSERT INTO `map_element` VALUES ('100', '140', '4', '2');
INSERT INTO `map_element` VALUES ('120', '140', '4', '2');
INSERT INTO `map_element` VALUES ('140', '140', '4', '2');
INSERT INTO `map_element` VALUES ('160', '140', '4', '2');
INSERT INTO `map_element` VALUES ('180', '140', '4', '2');
INSERT INTO `map_element` VALUES ('200', '140', '4', '2');
INSERT INTO `map_element` VALUES ('220', '140', '4', '2');
INSERT INTO `map_element` VALUES ('240', '140', '4', '2');
INSERT INTO `map_element` VALUES ('260', '140', '4', '2');
INSERT INTO `map_element` VALUES ('340', '140', '4', '2');
INSERT INTO `map_element` VALUES ('360', '140', '4', '2');
INSERT INTO `map_element` VALUES ('380', '140', '4', '2');
INSERT INTO `map_element` VALUES ('400', '140', '4', '2');
INSERT INTO `map_element` VALUES ('420', '140', '4', '2');
INSERT INTO `map_element` VALUES ('440', '140', '4', '2');
INSERT INTO `map_element` VALUES ('460', '140', '4', '2');
INSERT INTO `map_element` VALUES ('480', '140', '4', '2');
INSERT INTO `map_element` VALUES ('500', '140', '4', '2');
INSERT INTO `map_element` VALUES ('520', '140', '4', '2');
INSERT INTO `map_element` VALUES ('540', '140', '4', '2');
INSERT INTO `map_element` VALUES ('60', '540', '4', '2');
INSERT INTO `map_element` VALUES ('80', '540', '4', '2');
INSERT INTO `map_element` VALUES ('100', '540', '4', '2');
INSERT INTO `map_element` VALUES ('120', '540', '4', '2');
INSERT INTO `map_element` VALUES ('140', '540', '4', '2');
INSERT INTO `map_element` VALUES ('160', '540', '4', '2');
INSERT INTO `map_element` VALUES ('180', '540', '4', '2');
INSERT INTO `map_element` VALUES ('200', '540', '4', '2');
INSERT INTO `map_element` VALUES ('220', '540', '4', '2');
INSERT INTO `map_element` VALUES ('240', '540', '4', '2');
INSERT INTO `map_element` VALUES ('260', '540', '4', '2');
INSERT INTO `map_element` VALUES ('340', '540', '4', '2');
INSERT INTO `map_element` VALUES ('360', '540', '4', '2');
INSERT INTO `map_element` VALUES ('380', '540', '4', '2');
INSERT INTO `map_element` VALUES ('400', '540', '4', '2');
INSERT INTO `map_element` VALUES ('420', '540', '4', '2');
INSERT INTO `map_element` VALUES ('440', '540', '4', '2');
INSERT INTO `map_element` VALUES ('460', '540', '4', '2');
INSERT INTO `map_element` VALUES ('480', '540', '4', '2');
INSERT INTO `map_element` VALUES ('500', '540', '4', '2');
INSERT INTO `map_element` VALUES ('520', '540', '4', '2');
INSERT INTO `map_element` VALUES ('540', '540', '4', '2');
INSERT INTO `map_element` VALUES ('60', '60', '4', '2');
INSERT INTO `map_element` VALUES ('60', '80', '4', '2');
INSERT INTO `map_element` VALUES ('60', '100', '4', '2');
INSERT INTO `map_element` VALUES ('60', '120', '4', '2');
INSERT INTO `map_element` VALUES ('60', '140', '4', '2');
INSERT INTO `map_element` VALUES ('60', '160', '4', '2');
INSERT INTO `map_element` VALUES ('60', '180', '4', '2');
INSERT INTO `map_element` VALUES ('60', '200', '4', '2');
INSERT INTO `map_element` VALUES ('60', '220', '4', '2');
INSERT INTO `map_element` VALUES ('60', '240', '4', '2');
INSERT INTO `map_element` VALUES ('60', '260', '4', '2');
INSERT INTO `map_element` VALUES ('60', '340', '4', '2');
INSERT INTO `map_element` VALUES ('60', '360', '4', '2');
INSERT INTO `map_element` VALUES ('60', '380', '4', '2');
INSERT INTO `map_element` VALUES ('60', '400', '4', '2');
INSERT INTO `map_element` VALUES ('60', '420', '4', '2');
INSERT INTO `map_element` VALUES ('60', '440', '4', '2');
INSERT INTO `map_element` VALUES ('60', '460', '4', '2');
INSERT INTO `map_element` VALUES ('60', '480', '4', '2');
INSERT INTO `map_element` VALUES ('60', '500', '4', '2');
INSERT INTO `map_element` VALUES ('60', '520', '4', '2');
INSERT INTO `map_element` VALUES ('60', '540', '4', '2');
INSERT INTO `map_element` VALUES ('540', '60', '4', '2');
INSERT INTO `map_element` VALUES ('540', '80', '4', '2');
INSERT INTO `map_element` VALUES ('540', '100', '4', '2');
INSERT INTO `map_element` VALUES ('540', '120', '4', '2');
INSERT INTO `map_element` VALUES ('540', '140', '4', '2');
INSERT INTO `map_element` VALUES ('540', '160', '4', '2');
INSERT INTO `map_element` VALUES ('540', '180', '4', '2');
INSERT INTO `map_element` VALUES ('540', '200', '4', '2');
INSERT INTO `map_element` VALUES ('540', '220', '4', '2');
INSERT INTO `map_element` VALUES ('540', '240', '4', '2');
INSERT INTO `map_element` VALUES ('540', '260', '4', '2');
INSERT INTO `map_element` VALUES ('540', '340', '4', '2');
INSERT INTO `map_element` VALUES ('540', '360', '4', '2');
INSERT INTO `map_element` VALUES ('540', '380', '4', '2');
INSERT INTO `map_element` VALUES ('540', '400', '4', '2');
INSERT INTO `map_element` VALUES ('540', '420', '4', '2');
INSERT INTO `map_element` VALUES ('540', '440', '4', '2');
INSERT INTO `map_element` VALUES ('540', '460', '4', '2');
INSERT INTO `map_element` VALUES ('540', '480', '4', '2');
INSERT INTO `map_element` VALUES ('540', '500', '4', '2');
INSERT INTO `map_element` VALUES ('540', '520', '4', '2');
INSERT INTO `map_element` VALUES ('540', '540', '4', '2');
INSERT INTO `map_element` VALUES ('290', '290', '4', '2');
INSERT INTO `map_element` VALUES ('310', '290', '4', '2');
INSERT INTO `map_element` VALUES ('290', '310', '4', '2');
INSERT INTO `map_element` VALUES ('310', '310', '4', '2');
INSERT INTO `map_element` VALUES ('300', '40', '2', '5');
INSERT INTO `map_element` VALUES ('100', '500', '2', '4');
INSERT INTO `map_element` VALUES ('500', '500', '2', '4');
INSERT INTO `map_element` VALUES ('550', '300', '2', '4');
INSERT INTO `map_element` VALUES ('50', '300', '2', '4');
INSERT INTO `map_element` VALUES ('20', '580', '3', '4');
INSERT INTO `map_element` VALUES ('580', '580', '3', '4');
INSERT INTO `map_element` VALUES ('100', '400', '3', '4');
INSERT INTO `map_element` VALUES ('500', '300', '3', '4');
INSERT INTO `map_element` VALUES ('300', '20', '3', '5');
INSERT INTO `map_element` VALUES ('100', '400', '4', '4');
INSERT INTO `map_element` VALUES ('100', '500', '4', '4');
INSERT INTO `map_element` VALUES ('500', '300', '4', '4');
INSERT INTO `map_element` VALUES ('300', '500', '4', '4');
INSERT INTO `map_element` VALUES ('300', '20', '4', '5');

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  `map_progress` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_index` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES ('1', 'li', '12', '1');
INSERT INTO `player` VALUES ('2', 'liu', '12', '1');
INSERT INTO `player` VALUES ('3', 'xu', '12', '1');
INSERT INTO `player` VALUES ('4', 'ya', '12', '1');
INSERT INTO `player` VALUES ('5', 'yang', '1234', '1');
INSERT INTO `player` VALUES ('6', 'ye', '12', '1');
INSERT INTO `player` VALUES ('7', 'yu', '12', '1');

-- ----------------------------
-- Table structure for player_progress
-- ----------------------------
DROP TABLE IF EXISTS `player_progress`;
CREATE TABLE `player_progress` (
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `map_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `type` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of player_progress
-- ----------------------------
INSERT INTO `player_progress` VALUES ('70', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('110', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('150', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('190', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('230', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('270', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('310', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('430', '470', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('200', '290', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('220', '290', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('200', '310', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('220', '310', '1', '5', '1');
INSERT INTO `player_progress` VALUES ('486', '28', '1', '5', '5');
INSERT INTO `player_progress` VALUES ('320', '100', '1', '5', '4');
INSERT INTO `player_progress` VALUES ('266', '426', '1', '5', '4');
INSERT INTO `player_progress` VALUES ('492', '416', '1', '5', '4');
