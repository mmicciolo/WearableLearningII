CREATE DATABASE  IF NOT EXISTS `wearablelearning` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wearablelearning`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: wearablelearning
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class` (
  `classId` int(11) NOT NULL AUTO_INCREMENT,
  `teacherId` int(11) NOT NULL,
  `className` varchar(45) NOT NULL,
  `grade` int(11) NOT NULL,
  `school` varchar(45) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,1,'A Group',12,'UMass',2017),(2,1,'B Group',12,'UMass',2017),(3,1,'C Group',12,'UMass',2017);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devices` (
  `deviceId` int(11) NOT NULL AUTO_INCREMENT,
  `ipAddress` varchar(45) DEFAULT NULL,
  `macAddress` varchar(45) DEFAULT NULL,
  `studentId` int(11) DEFAULT NULL,
  `connected` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`deviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gameinstance`
--

DROP TABLE IF EXISTS `gameinstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gameinstance` (
  `gameInstanceId` int(11) NOT NULL AUTO_INCREMENT,
  `gameId` int(11) DEFAULT NULL,
  PRIMARY KEY (`gameInstanceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gameinstance`
--

LOCK TABLES `gameinstance` WRITE;
/*!40000 ALTER TABLE `gameinstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `gameinstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games` (
  `gameId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `teamCount` int(11) NOT NULL,
  `playersPerTeam` int(11) NOT NULL,
  PRIMARY KEY (`gameId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gamestate`
--

DROP TABLE IF EXISTS `gamestate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gamestate` (
  `gameStateId` int(11) NOT NULL AUTO_INCREMENT,
  `gameId` int(11) DEFAULT NULL,
  `teamId` int(11) DEFAULT NULL,
  `playerId` int(11) DEFAULT NULL,
  `hintSetId` int(11) DEFAULT NULL,
  `textContent` varchar(1000) DEFAULT NULL,
  `ledColor` varchar(45) DEFAULT NULL,
  `ledDuration` int(11) DEFAULT NULL,
  `buzzerState` tinyint(1) DEFAULT NULL,
  `buzzerDuration` int(11) DEFAULT NULL,
  `buttonInputType` varchar(45) DEFAULT NULL,
  `rfid` varchar(45) DEFAULT NULL,
  `gameStateCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`gameStateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gamestate`
--

LOCK TABLES `gamestate` WRITE;
/*!40000 ALTER TABLE `gamestate` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamestate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gamestatetransitions`
--

DROP TABLE IF EXISTS `gamestatetransitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gamestatetransitions` (
  `gameStateTransitionId` int(11) NOT NULL AUTO_INCREMENT,
  `gameStateId` int(11) DEFAULT NULL,
  `singlePushButtonColor` int(11) DEFAULT NULL,
  `fourButtonPush0` varchar(45) DEFAULT NULL,
  `fourButtonPush1` varchar(45) DEFAULT NULL,
  `fourButtonPush2` varchar(45) DEFAULT NULL,
  `fourButtonPush3` varchar(45) DEFAULT NULL,
  `nextGameStateTransition` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gameStateTransitionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gamestatetransitions`
--

LOCK TABLES `gamestatetransitions` WRITE;
/*!40000 ALTER TABLE `gamestatetransitions` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamestatetransitions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hint`
--

DROP TABLE IF EXISTS `hint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hint` (
  `hintId` int(11) NOT NULL AUTO_INCREMENT,
  `hintSetId` int(11) DEFAULT NULL,
  `gameStateId` int(11) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `textContent` varchar(45) DEFAULT NULL,
  `gameInstanceId` int(11) NOT NULL,
  PRIMARY KEY (`hintId`),
  KEY `fk_hint_gameInstance_idx` (`gameInstanceId`),
  CONSTRAINT `fk_hint_gameInstance` FOREIGN KEY (`gameInstanceId`) REFERENCES `gameinstance` (`gameInstanceId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hint`
--

LOCK TABLES `hint` WRITE;
/*!40000 ALTER TABLE `hint` DISABLE KEYS */;
/*!40000 ALTER TABLE `hint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mathskills`
--

DROP TABLE IF EXISTS `mathskills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mathskills` (
  `mathSkillId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `subject` varchar(45) DEFAULT NULL,
  `domainName` varchar(45) DEFAULT NULL,
  `clusterName` varchar(45) DEFAULT NULL,
  `standardName` varchar(45) DEFAULT NULL,
  `standardDescription` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`mathSkillId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mathskills`
--

LOCK TABLES `mathskills` WRITE;
/*!40000 ALTER TABLE `mathskills` DISABLE KEYS */;
/*!40000 ALTER TABLE `mathskills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `players` (
  `playerId` int(11) NOT NULL AUTO_INCREMENT,
  `gameInstanceId` int(11) DEFAULT NULL,
  `studentId` int(11) DEFAULT NULL,
  `currentGameState` int(11) DEFAULT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `studentId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `classId` int(11) NOT NULL,
  PRIMARY KEY (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Jack','B','',0,1),(2,'Manjusha','C','',0,1),(3,'Elianna','D','',0,1),(4,'Veronika','E','',0,1),(5,'Melissa','F','',0,1),(6,'Sean','F','',0,1),(7,'Jack','M','',0,1),(8,'Trevor','M','',0,1),(9,'Nathan','N','',0,1),(10,'Kent','P','',0,1),(11,'Jenna','P','',0,1),(12,'Ritvik','R','',0,1),(13,'Kira','S','',0,1),(14,'Rithika','S','',0,1),(15,'Jonathan','S','',0,1),(16,'Sally','Z','',0,1),(17,'Nils','B','',0,2),(18,'Numaan','D','',0,2),(19,'Sarah','D','',0,2),(20,'Tamsin','E','',0,2),(21,'Cameron','H','',0,2),(22,'Keving','H','',0,2),(23,'Greta','J','',0,2),(24,'Sameer','K','',0,2),(25,'Sathwik','K','',0,2),(26,'Yasaman','K','',0,2),(27,'Abigail','L','',0,2),(28,'Michelle','L','',0,2),(29,'Sam','O','',0,2),(30,'Daya','S','',0,2),(31,'Varnika','S','',0,2),(32,'Patrick','W','',0,2),(33,'Jinchao','Y','',0,2),(34,'Nilay','B','',0,3),(35,'Emma','B','',0,3),(36,'Celine','B','',0,3),(37,'Elena','C','',0,3),(38,'Niall','D','',0,3),(39,'George','G','',0,3),(40,'Gia','H','',0,3),(41,'Aditya','H','',0,3),(42,'Tao','L','',0,3),(43,'Rinija','R','',0,3),(44,'Ramviknesh','R','',0,3),(45,'Sarah','R','',0,3),(46,'Jessica','R','',0,3),(47,'Charan','S','',0,3),(48,'Aarthi','S','',0,3),(49,'John','T','',0,3);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentmathskills`
--

DROP TABLE IF EXISTS `studentmathskills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentmathskills` (
  `studentMathSkillId` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` int(11) DEFAULT NULL,
  `mathSkillId` int(11) DEFAULT NULL,
  `stat` int(11) DEFAULT NULL,
  PRIMARY KEY (`studentMathSkillId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentmathskills`
--

LOCK TABLES `studentmathskills` WRITE;
/*!40000 ALTER TABLE `studentmathskills` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentmathskills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `teacherId` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `school` varchar(45) NOT NULL,
  PRIMARY KEY (`teacherId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'umass','FFBAAE3922F903EB4A886E6CFB0C5C17','Matthew','Micciolo','UMass');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `teamId` int(11) NOT NULL AUTO_INCREMENT,
  `gameId` int(11) DEFAULT NULL,
  `teamName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`teamId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teamstudents`
--

DROP TABLE IF EXISTS `teamstudents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teamstudents` (
  `teamStudentId` int(11) NOT NULL AUTO_INCREMENT,
  `playerId` int(11) DEFAULT NULL,
  `teamId` int(11) DEFAULT NULL,
  PRIMARY KEY (`teamStudentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teamstudents`
--

LOCK TABLES `teamstudents` WRITE;
/*!40000 ALTER TABLE `teamstudents` DISABLE KEYS */;
/*!40000 ALTER TABLE `teamstudents` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-17  1:05:53
