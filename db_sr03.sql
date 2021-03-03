/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : db_sr03

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 26/05/2020 21:29:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum
-- ----------------------------
DROP TABLE IF EXISTS `forum`;
CREATE TABLE `forum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL DEFAULT 'new forum',
  `owner` int(11) NOT NULL,
  `description` mediumtext,
  PRIMARY KEY (`id`),
  KEY `user_owner_idx` (`owner`),
  CONSTRAINT `user_owner` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` mediumtext,
  `editor` int(11) NOT NULL,
  `destination` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_editor_idx` (`editor`),
  KEY `Fk_dest_idx` (`destination`),
  CONSTRAINT `FK_editor` FOREIGN KEY (`editor`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Fk_dest` FOREIGN KEY (`destination`) REFERENCES `forum` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for subscriptions
-- ----------------------------
DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE `subscriptions` (
  `id_user` int(11) NOT NULL,
  `id_forum` int(11) NOT NULL,
  PRIMARY KEY (`id_user`,`id_forum`),
  KEY `FK_subs_forum_idx` (`id_forum`),
  CONSTRAINT `FK_subs_forum` FOREIGN KEY (`id_forum`) REFERENCES `forum` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_subs_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(20) DEFAULT NULL,
  `lname` varchar(20) DEFAULT NULL,
  `login` varchar(45) NOT NULL,
  `is_admin` tinyint(4) DEFAULT '0',
  `gender` varchar(10) DEFAULT 'male',
  `pwd` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `user` VALUES (1, 'anonyme', 'anonyme', 'anonyme@anonyme.anonyme', 0, 'male', 'anonymePwd');