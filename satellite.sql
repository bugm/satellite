# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.0.1    Database: satellite
# ------------------------------------------------------
# Server version 5.5.20

DROP DATABASE IF EXISTS `satellite`;
CREATE DATABASE `satellite` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `satellite`;

#
# Source for table orbitpara
#

DROP TABLE IF EXISTS `orbitpara`;
CREATE TABLE `orbitpara` (
  `id` char(10) NOT NULL,
  `epoch` int(10) DEFAULT NULL,
  `xincl` double DEFAULT NULL,
  `xnodeo` double DEFAULT NULL,
  `eo` double DEFAULT NULL,
  `omegao` double DEFAULT NULL,
  `xmo` double DEFAULT NULL,
  `xno` double DEFAULT NULL,
  `bstar` double DEFAULT NULL,
  `xndt2o` double DEFAULT NULL,
  `xndd6o` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table orbitpara
#

LOCK TABLES `orbitpara` WRITE;
/*!40000 ALTER TABLE `orbitpara` DISABLE KEYS */;
/*!40000 ALTER TABLE `orbitpara` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table property
#

DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `id` char(10) NOT NULL,
  `sname` char(15) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `volume` double DEFAULT NULL,
  `worktime` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table property
#

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table userinfo
#

DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` char(14) NOT NULL DEFAULT '',
  `passwd` char(14) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table userinfo
#

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;

#
#  Foreign keys for table orbitpara
#

ALTER TABLE `orbitpara`
ADD CONSTRAINT `orbitpara_ibfk_1` FOREIGN KEY (`id`) REFERENCES `property` (`id`) ON DELETE CASCADE;

insert into userinfo values('3012218119','123');

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
