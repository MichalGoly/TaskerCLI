-- MySQL dump 10.13  Distrib 5.5.44, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: TaskerSRV
-- ------------------------------------------------------
-- Server version	5.5.44-0ubuntu0.14.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `TaskerSRV`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `TaskerSRV` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `TaskerSRV`;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Admin` (
  `adminId` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES (1, 'nwh', '$2y$10$g55FAaRskZ1GNUS6olMHA.KbXwjcu7wgpSJGD3szL4e52lp8JMpLC');
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task` (
  `taskId` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) NOT NULL UNIQUE,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `taskStatus` int(11) NOT NULL,
  `TeamMember_email` varchar(80) NOT NULL,
  PRIMARY KEY (`taskId`),
  UNIQUE KEY `taskId` (`taskId`),
  KEY `Task_TeamMember` (`TeamMember_email`),
  CONSTRAINT `Task_TeamMember` FOREIGN KEY (`TeamMember_email`) REFERENCES `TeamMember` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task`
--

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
INSERT INTO `Task` VALUES (1,'Do shopping','2015-09-29','2015-10-30',1,'m.goly@goly2.com'),(2,'Apply to Uni','2010-09-29','2025-01-03',1,'m.goly@goly2.com'),(3,'Buy a new car','2000-09-29','2016-11-03',1,'bob@smith.com');
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TaskElement`
--

DROP TABLE IF EXISTS `TaskElement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TaskElement` (
  `taskElementId` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(250) NOT NULL,
  `comments` varchar(250) NOT NULL,
  `Task_taskId` bigint(20) NOT NULL,
  PRIMARY KEY (`taskElementId`),
  UNIQUE KEY `taskElementId` (`taskElementId`),
  KEY `TaskElement_Task` (`Task_taskId`),
  CONSTRAINT `TaskElement_Task` FOREIGN KEY (`Task_taskId`) REFERENCES `Task` (`taskId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TaskElement`
--

LOCK TABLES `TaskElement` WRITE;
/*!40000 ALTER TABLE `TaskElement` DISABLE KEYS */;
INSERT INTO `TaskElement` VALUES (1,'get out of the house','',1),(2,'drive to the store','',1),(3,'put items to your basket','',1),(4,'pay for the goods','',1),(5,'drive back home','',1),(6,'get out of your house','',3),(7,'go to the car saloon','',3),(8,'pick the make of the car','',3),(9,'pick the color of the car','',3),(10,'test drive','',3),(11,'make the purchase','',3),(12,'drive back home','',3),(13,'go to UCAS website','',2),(14,'pick your course','',2),(15,'decide where you want to apply','',2),(16,'apply','',2),(17,'go to the open day','',2),(18,'wait for the results','',2),(19,'celebrate','',2);
/*!40000 ALTER TABLE `TaskElement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TeamMember`
--

DROP TABLE IF EXISTS `TeamMember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TeamMember` (
  `email` varchar(80) NOT NULL,
  `firstName` varchar(40) NOT NULL,
  `lastName` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TeamMember`
--

LOCK TABLES `TeamMember` WRITE;
/*!40000 ALTER TABLE `TeamMember` DISABLE KEYS */;
INSERT INTO `TeamMember` VALUES ('bob@smith.com','Bob','Smith','$2y$10$pSWbF/IkvvXPghFgKoKq..w2y9NF9krJNMBUkcnAHrGYu2doQa5YK'),('jane@gmail.com','Jane','Adams','$2y$10$rNWP.zrrmUOefOeJoStu8u2QRkZSxZe1bxUZli5UtFqVZ1TwYrhFO'),('m.goly@goly2.com','Michal','Goly','$2y$10$QT3etHPuD9Azt2JGBsLra.4zPTobVf2S17unxeNIUzF6GlZHRad72');
/*!40000 ALTER TABLE `TeamMember` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-22 15:01:10
