-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.23 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2012-05-29 21:55:11
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for j2ee
DROP DATABASE IF EXISTS `j2ee`;
CREATE DATABASE IF NOT EXISTS `j2ee` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `j2ee`;


-- Dumping structure for table j2ee.t_account
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE IF NOT EXISTS `t_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `provider` char(8) NOT NULL,
  `external_id` char(64) NOT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_provider_external_id` (`provider`,`external_id`),
  KEY `ix_provider` (`provider`),
  KEY `ix_external_id` (`external_id`),
  KEY `ix_user_id` (`user_id`),
  KEY `ix_create_time` (`create_time`),
  CONSTRAINT `fk_account_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table j2ee.t_oauth_qq
DROP TABLE IF EXISTS `t_oauth_qq`;
CREATE TABLE IF NOT EXISTS `t_oauth_qq` (
  `token` char(64) NOT NULL,
  `token_secret` char(64) NOT NULL,
  `account_id` bigint(20) unsigned NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  PRIMARY KEY (`token`),
  KEY `ix_name` (`token`),
  KEY `ix_account_id` (`account_id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_expire_time` (`expire_time`),
  CONSTRAINT `fk_oauth_qq_account` FOREIGN KEY (`account_id`) REFERENCES `t_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table j2ee.t_oauth_sina
DROP TABLE IF EXISTS `t_oauth_sina`;
CREATE TABLE IF NOT EXISTS `t_oauth_sina` (
  `token` char(64) NOT NULL,
  `token_secret` char(64) NOT NULL,
  `account_id` bigint(20) unsigned NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  PRIMARY KEY (`token`),
  KEY `ix_name` (`token`),
  KEY `ix_account_id` (`account_id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_expire_time` (`expire_time`),
  CONSTRAINT `fk_oauth_sina_account` FOREIGN KEY (`account_id`) REFERENCES `t_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table j2ee.t_session
DROP TABLE IF EXISTS `t_session`;
CREATE TABLE IF NOT EXISTS `t_session` (
  `id` char(32) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_user_id` (`user_id`),
  CONSTRAINT `fk_session_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table j2ee.t_user
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for trigger j2ee.g_account_before_insert
DROP TRIGGER IF EXISTS `g_account_before_insert`;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `g_account_before_insert` BEFORE INSERT ON `t_account` FOR EACH ROW begin
	if (new.create_time is null) then
		set new.create_time = utc_timestamp();
	end if;
	if (new.user_id is null) then
		insert into t_user () values ();
		set new.user_id = last_insert_id();
	end if;
end//
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;


-- Dumping structure for trigger j2ee.g_oauth_sina_before_insert
DROP TRIGGER IF EXISTS `g_oauth_sina_before_insert`;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `g_oauth_sina_before_insert` BEFORE INSERT ON `t_oauth_sina` FOR EACH ROW begin
	if (new.create_time is null) then
		set new.create_time = utc_timestamp();
	end if;
	if (new.expire_time is null) then
		set new.expire_time = "2029-12-31 23:59:59";
	end if;
end//
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;


-- Dumping structure for trigger j2ee.g_user_before_insert
DROP TRIGGER IF EXISTS `g_user_before_insert`;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `g_user_before_insert` BEFORE INSERT ON `t_user` FOR EACH ROW begin
	if (new.create_time is null) then
		set new.create_time = utc_timestamp();
	end if;
end//
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
