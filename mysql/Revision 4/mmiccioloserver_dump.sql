CREATE DATABASE  IF NOT EXISTS `wearablelearning` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wearablelearning`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 71.162.250.48    Database: wearablelearning
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.1

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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,1,'My Class 1',10,'WPI',2016),(4,1,'Class2',12,'School',2015),(5,1,'My Class',10,'My School',2015),(7,1,'CS3049',16,'WPI',2015),(11,1,'Class',12,'School',2015),(20,1,'Algebra23',5,'WPI',2015),(21,1,'Algebra',5,'WPI',2),(50,1,'My Class Another',2,'School 3',2016),(51,1,'Class',5,'Class',5),(52,1,'New Class',10,'New School',2015),(54,6,'Algebra 1',9,'CRN',2016),(55,6,'Geometry',10,'CRN',2016),(56,6,'Algebra 2',10,'CRN',2016),(57,6,'Calculus AB',12,'CRN',2016),(58,6,'Trigonometry',11,'CRN',2016),(60,1,'Stress Test',12,'WPI',2017);
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
-- Table structure for table `gameInstance`
--

DROP TABLE IF EXISTS `gameInstance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gameInstance` (
  `gameInstanceId` int(11) NOT NULL AUTO_INCREMENT,
  `gameId` int(11) DEFAULT NULL,
  PRIMARY KEY (`gameInstanceId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gameInstance`
--

LOCK TABLES `gameInstance` WRITE;
/*!40000 ALTER TABLE `gameInstance` DISABLE KEYS */;
INSERT INTO `gameInstance` VALUES (9,17);
/*!40000 ALTER TABLE `gameInstance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gameState`
--

DROP TABLE IF EXISTS `gameState`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gameState` (
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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gameState`
--

LOCK TABLES `gameState` WRITE;
/*!40000 ALTER TABLE `gameState` DISABLE KEYS */;
INSERT INTO `gameState` VALUES (19,10,0,0,0,'Welcome to the game!!','17d43a',0,1,14,'Single','0',1),(20,10,0,0,0,'Welcome to state 2!','e0d01b',0,0,0,'Single','0',2),(21,12,0,0,0,'Welcome to my first game, state 1. Press any button to go to state 2.','',0,0,0,'Single','0',1),(22,12,0,0,0,'Welcome to state 2. Press any button to go to state 1.','',0,0,0,'Single','0',2),(31,15,0,0,0,'Welcome all players! Press any button to go to team state.','',0,0,0,'Single','0',1),(32,15,1,0,0,'Hello Team 1! Press a button to go to a player state.','',0,0,0,'Single','0',2),(33,15,0,1,0,'Player 1 state. Press a button to go to state 1.','',0,0,0,'Single','0',3),(34,15,0,2,0,'Player 2 state. Press a button to go to state 1.','',0,0,0,'Single','0',4),(35,16,0,0,0,'','',0,0,0,'Single','0',1),(36,17,0,0,0,'Welcome to the stress test!','',0,0,0,'Single','0',1),(37,17,1,0,0,'Welcome to team 1!','',0,0,0,'Single','0',2),(38,17,2,0,0,'Welcome to team 2!','',0,0,0,'Single','0',3),(39,17,3,0,0,'Welcome to team 3!','',0,0,0,'Single','0',4),(40,17,0,1,0,'Team 1, Player 1.','',0,0,0,'Single','0',5),(41,17,0,2,0,'Team 1, Player 2.','',0,0,0,'Single','0',6),(42,17,0,3,0,'Team 1, Player 3.','',0,0,0,'Single','0',7),(43,17,0,1,0,'Team 2, Player 1.','',0,0,0,'Single','0',8),(44,17,0,2,0,'Team 2, Player 2.','',0,0,0,'Single','0',9),(45,17,0,3,0,'Team 2, Player 3','',0,0,0,'Single','0',10),(46,17,0,1,0,'Team 3, Player 1.','',0,0,0,'Single','0',11),(47,17,0,2,0,'Team 3, Player 2.','',0,0,0,'Single','0',12),(48,17,0,3,0,'Team 3, Player 3.','',0,0,0,'Single','0',13);
/*!40000 ALTER TABLE `gameState` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gameStateTransitions`
--

DROP TABLE IF EXISTS `gameStateTransitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gameStateTransitions` (
  `gameStateTransitionId` int(11) NOT NULL AUTO_INCREMENT,
  `gameStateId` int(11) DEFAULT NULL,
  `singlePushButtonColor` int(11) DEFAULT NULL,
  `fourButtonPush0` varchar(45) DEFAULT NULL,
  `fourButtonPush1` varchar(45) DEFAULT NULL,
  `fourButtonPush2` varchar(45) DEFAULT NULL,
  `fourButtonPush3` varchar(45) DEFAULT NULL,
  `nextGameStateTransition` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gameStateTransitionId`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gameStateTransitions`
--

LOCK TABLES `gameStateTransitions` WRITE;
/*!40000 ALTER TABLE `gameStateTransitions` DISABLE KEYS */;
INSERT INTO `gameStateTransitions` VALUES (73,19,0,'','','','','2'),(74,19,1,'','','','','1'),(75,19,2,'','','','','2'),(76,19,3,'','','','','1'),(77,20,0,'','','','','1'),(78,20,1,'','','','','2'),(79,20,2,'','','','','1'),(80,20,3,'','','','','2'),(81,21,0,'','','','','2'),(82,21,1,'','','','','2'),(83,21,2,'','','','','2'),(84,21,3,'','','','','2'),(85,22,0,'','','','','1'),(86,22,1,'','','','','1'),(87,22,2,'','','','','1'),(88,22,3,'','','','','1'),(129,31,0,'','','','','2'),(130,31,1,'','','','','2'),(131,31,2,'','','','','2'),(132,31,3,'','','','','2'),(133,32,0,'','','','','3'),(134,32,1,'','','','','3'),(135,32,2,'','','','','3'),(136,32,3,'','','','','3'),(137,32,0,'','','','','4'),(138,32,1,'','','','','4'),(139,32,2,'','','','','4'),(140,32,3,'','','','','4'),(141,33,0,'','','','','1'),(142,33,1,'','','','','1'),(143,33,2,'','','','','1'),(144,33,3,'','','','','1'),(145,34,0,'','','','','1'),(146,34,1,'','','','','1'),(147,34,2,'','','','','1'),(148,34,3,'','','','','1'),(149,35,0,'','','','','1'),(150,35,1,'','','','','1'),(151,35,2,'','','','','1'),(152,35,3,'','','','','1'),(153,36,0,'','','','','2'),(154,36,1,'','','','','2'),(155,36,2,'','','','','2'),(156,36,3,'','','','','2'),(157,36,0,'','','','','3'),(158,36,1,'','','','','3'),(159,36,2,'','','','','3'),(160,36,3,'','','','','3'),(161,36,0,'','','','','4'),(162,36,1,'','','','','4'),(163,36,2,'','','','','4'),(164,36,3,'','','','','4'),(165,37,0,'','','','','5'),(166,37,1,'','','','','5'),(167,37,2,'','','','','5'),(168,37,3,'','','','','5'),(169,37,0,'','','','','6'),(170,37,1,'','','','','6'),(171,37,2,'','','','','6'),(172,37,3,'','','','','6'),(173,37,0,'','','','','7'),(174,37,1,'','','','','7'),(175,37,2,'','','','','7'),(176,37,3,'','','','','7'),(177,38,0,'','','','','8'),(178,38,1,'','','','','8'),(179,38,2,'','','','','8'),(180,38,3,'','','','','8'),(181,38,0,'','','','','9'),(182,38,1,'','','','','9'),(183,38,2,'','','','','9'),(184,38,3,'','','','','9'),(185,38,0,'','','','','10'),(186,38,1,'','','','','10'),(187,38,2,'','','','','10'),(188,38,3,'','','','','10'),(189,39,0,'','','','','11'),(190,39,1,'','','','','11'),(191,39,2,'','','','','11'),(192,39,3,'','','','','11'),(193,39,0,'','','','','12'),(194,39,1,'','','','','12'),(195,39,2,'','','','','12'),(196,39,3,'','','','','12'),(197,39,0,'','','','','13'),(198,39,1,'','','','','13'),(199,39,2,'','','','','13'),(200,39,3,'','','','','13'),(201,40,0,'','','','','1'),(202,40,1,'','','','','1'),(203,40,2,'','','','','1'),(204,40,3,'','','','','1'),(205,41,0,'','','','','1'),(206,41,1,'','','','','1'),(207,41,2,'','','','','1'),(208,41,3,'','','','','1'),(209,42,0,'','','','','1'),(210,42,1,'','','','','1'),(211,42,2,'','','','','1'),(212,42,3,'','','','','1'),(213,43,0,'','','','','1'),(214,43,1,'','','','','1'),(215,43,2,'','','','','1'),(216,43,3,'','','','','1'),(217,44,0,'','','','','1'),(218,44,1,'','','','','1'),(219,44,2,'','','','','1'),(220,44,3,'','','','','1'),(221,45,0,'','','','','1'),(222,45,1,'','','','','1'),(223,45,2,'','','','','1'),(224,45,3,'','','','','1'),(225,46,0,'','','','','1'),(226,46,1,'','','','','1'),(227,46,2,'','','','','1'),(228,46,3,'','','','','1'),(229,47,0,'','','','','1'),(230,47,1,'','','','','1'),(231,47,2,'','','','','1'),(232,47,3,'','','','','1'),(233,48,0,'','','','','1'),(234,48,1,'','','','','1'),(235,48,2,'','','','','1'),(236,48,3,'','','','','1');
/*!40000 ALTER TABLE `gameStateTransitions` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (10,'My Game',1,1),(12,'My First Game',1,1),(15,'My Second Game',1,2),(16,'g',1,1),(17,'Stress Test',3,3);
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
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
  CONSTRAINT `fk_hint_gameInstance` FOREIGN KEY (`gameInstanceId`) REFERENCES `gameInstance` (`gameInstanceId`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
-- Table structure for table `mathSkills`
--

DROP TABLE IF EXISTS `mathSkills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mathSkills` (
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
-- Dumping data for table `mathSkills`
--

LOCK TABLES `mathSkills` WRITE;
/*!40000 ALTER TABLE `mathSkills` DISABLE KEYS */;
/*!40000 ALTER TABLE `mathSkills` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (2,'Matthew','Micciolo','Male',22,4),(3,'Bob','Nickson','Male',16,20),(4,'A student','Last Name','Female',22,7),(5,'Nicole','Moore','Female',18,21),(7,'Test Student 1','Test Student 1','Male',1,60),(8,'Test Student 2','Test Student 2','Male',1,60),(9,'Test Student 3','Test Student 3','Male',1,60),(10,'Test Student 4','Test Student 4','Male',1,60),(11,'Test Student 5','Test Student 5','Male',1,60),(12,'Test Student 6','Test Student 6','Male',1,60),(13,'Test Student 7','Test Student 7','Male',1,60),(14,'Test Student 8','Test Student 8','Male',1,60),(15,'Test Student 9','Test Student 9','Male',1,60);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentMathSkills`
--

DROP TABLE IF EXISTS `studentMathSkills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentMathSkills` (
  `studentMathSkillId` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` int(11) DEFAULT NULL,
  `mathSkillId` int(11) DEFAULT NULL,
  `stat` int(11) DEFAULT NULL,
  PRIMARY KEY (`studentMathSkillId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentMathSkills`
--

LOCK TABLES `studentMathSkills` WRITE;
/*!40000 ALTER TABLE `studentMathSkills` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentMathSkills` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'s','03c7c0ace395d80182db07ae2c30f034','s','s','s'),(2,'mmicciolo@wpi.edu','matthew','Matthew','Micciolo','WPI'),(3,'m@m.com','matthew','matthe','micciolo','m shcool'),(6,'test','test','Matthew','M','test'),(7,'t','098F6BCD4621D373CADE4E832627B4F6','m','m','m');
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
-- Table structure for table `teamStudents`
--

DROP TABLE IF EXISTS `teamStudents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teamStudents` (
  `teamStudentId` int(11) NOT NULL AUTO_INCREMENT,
  `playerId` int(11) DEFAULT NULL,
  `teamId` int(11) DEFAULT NULL,
  PRIMARY KEY (`teamStudentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teamStudents`
--

LOCK TABLES `teamStudents` WRITE;
/*!40000 ALTER TABLE `teamStudents` DISABLE KEYS */;
/*!40000 ALTER TABLE `teamStudents` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-21  0:52:06
