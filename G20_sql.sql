-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gonature
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `OrderNumber` int NOT NULL,
  `ParkName` varchar(45) DEFAULT NULL,
  `VisitDate` date DEFAULT NULL,
  `VisitStartTime` time DEFAULT NULL,
  `VisitEndTime` time DEFAULT NULL,
  `NumberOfVisitors` int DEFAULT NULL,
  `VisitType` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `VisitorID` varchar(45) DEFAULT NULL,
  `VisitDuration` int DEFAULT NULL,
  `Visit_Time_And_Date` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Payment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`OrderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1002,'Hyde Park','2024-03-07','11:00:00','15:00:00',10,'Guided','janedoe@example.com','055-234-5678','301234568',4,'07.03.2024 11:00',NULL,'YES'),(1004,'YellowStone','2024-03-09','13:00:00','17:00:00',12,'Guided','sarahjones@example.com','054-456-7890','302345670',4,'09.03.2024 13:00',NULL,NULL),(1005,'Hyde Park','2024-03-10','14:00:00','18:00:00',1,'Solo','michaelbrown@example.com','055-567-8901','202345671',4,'10.03.2024 14:00',NULL,'NO'),(1007,'YellowStone','2024-03-12','16:00:00','20:00:00',15,'Guided','christopherdavis@example.com','054-789-0123','303456783',4,'12.03.2024 16:00',NULL,NULL),(1008,'Hyde Park','2024-03-13','07:00:00','11:00:00',1,'Solo','amandajohnson@example.com','055-890-1234','204567894',4,'13.03.2024 07:00',NULL,NULL),(1010,'YellowStone','2024-03-15','09:00:00','13:00:00',1,'Solo','elizabethanderson@example.com','054-012-3456','205678906',4,'15.03.2024 09:00',NULL,NULL),(1011,'Hyde Park','2024-03-16','10:00:00','14:00:00',1,'Solo','justinlee@example.com','055-123-4567','206789017',4,'16.03.2024 10:00',NULL,NULL),(1013,'YellowStone','2024-03-18','12:00:00','16:00:00',11,'Guided','ryanmartin@example.com','054-345-6789','306789019',4,'18.03.2024 12:00',NULL,NULL),(1014,'Hyde Park','2024-03-19','13:00:00','17:00:00',1,'Solo','danielhernandez@example.com','055-456-7890','207890120',4,'19.03.2024 13:00',NULL,NULL),(1016,'YellowStone','2024-03-21','15:00:00','19:00:00',1,'Solo','jessicawhite@example.com','054-678-9012','208901232',4,'21.03.2024 15:00',NULL,NULL),(1017,'Hyde Park','2024-03-22','16:00:00','20:00:00',12,'Guided','matthewlee@example.com','055-789-0123','308901233',4,'22.03.2024 16:00',NULL,NULL),(1019,'YellowStone','2024-03-24','08:00:00','12:00:00',7,'Guided','laurenbrown@example.com','054-901-2345','309012345',4,'24.03.2024 08:00',NULL,NULL),(1020,'Hyde Park','2024-03-25','09:00:00','13:00:00',1,'Solo','ethanjohnson@example.com','055-012-3456','210123456',4,'25.03.2024 09:00',NULL,NULL),(1022,'YellowStone','2024-03-27','11:00:00','15:00:00',8,'Guided','williamdavis@example.com','054-234-5678','310123468',4,'27.03.2024 11:00',NULL,NULL),(1023,'Hyde Park','2024-03-28','12:00:00','16:00:00',1,'Solo','emilywilson@example.com','055-345-6789','211234579',4,'28.03.2024 12:00',NULL,NULL),(1025,'YellowStone','2024-03-30','14:00:00','18:00:00',1,'Solo','oliviamartinez@example.com','054-567-8901','212345691',4,'30.03.2024 14:00',NULL,NULL),(1026,'Hyde Park','2024-03-31','15:00:00','19:00:00',1,'Solo','charlottejackson@example.com','055-678-9012','312345692',4,'31.03.2024 15:00',NULL,NULL),(1028,'YellowStone','2024-04-02','07:00:00','11:00:00',1,'Solo','zoewilson@example.com','054-890-1234','214567814',4,'02.04.2024 07:00',NULL,NULL),(1029,'Hyde Park','2024-04-03','08:00:00','12:00:00',11,'Guided','jacobthompson@example.com','055-901-2345','313456815',4,'03.04.2024 08:00',NULL,NULL),(1031,'YellowStone','2024-04-05','10:00:00','14:00:00',8,'Guided','johndoe@example.com','054-123-4567','201234567',4,'05.04.2024 10:00',NULL,NULL),(1032,'Hyde Park','2024-04-06','11:00:00','15:00:00',1,'Solo','janedoe@example.com','055-234-5678','301234568',4,'06.04.2024 11:00',NULL,NULL),(1034,'YellowStone','2024-04-08','13:00:00','17:00:00',1,'Solo','sarahjones@example.com','054-456-7890','302345670',4,'08.04.2024 13:00',NULL,NULL),(1035,'Hyde Park','2024-04-09','14:00:00','18:00:00',9,'Guided','michaelbrown@example.com','055-567-8901','202345671',4,'09.04.2024 14:00',NULL,NULL),(1037,'YellowStone','2024-04-11','16:00:00','20:00:00',7,'Guided','christopherdavis@example.com','054-789-0123','303456783',4,'11.04.2024 16:00',NULL,NULL),(1038,'Hyde Park','2024-04-12','07:00:00','11:00:00',1,'Solo','amandajohnson@example.com','055-890-1234','204567894',4,'12.04.2024 07:00',NULL,NULL),(1040,'YellowStone','2024-04-14','09:00:00','13:00:00',1,'Solo','elizabethanderson@example.com','054-012-3456','205678906',4,'14.04.2024 09:00',NULL,NULL),(1041,'Hyde Park','2024-04-15','10:00:00','14:00:00',1,'Solo','justinlee@example.com','055-123-4567','206789017',4,'15.04.2024 10:00',NULL,NULL),(1043,'YellowStone','2024-04-17','12:00:00','16:00:00',1,'Solo','ryanmartin@example.com','054-345-6789','306789019',4,'17.04.2024 12:00',NULL,NULL),(1044,'Hyde Park','2024-04-18','13:00:00','17:00:00',15,'Guided','danielhernandez@example.com','055-456-7890','207890120',4,'18.04.2024 13:00',NULL,NULL),(1046,'YellowStone','2024-04-20','15:00:00','19:00:00',8,'Guided','jessicawhite@example.com','054-678-9012','208901232',4,'20.04.2024 15:00',NULL,NULL),(1047,'Hyde Park','2024-04-21','16:00:00','20:00:00',1,'Solo','matthewlee@example.com','055-789-0123','308901233',4,'21.04.2024 16:00',NULL,NULL),(1049,'YellowStone','2024-04-23','08:00:00','12:00:00',1,'Solo','laurenbrown@example.com','054-901-2345','309012345',4,'23.04.2024 08:00',NULL,NULL),(1050,'Hyde Park','2024-04-24','09:00:00','13:00:00',12,'Guided','ethanjohnson@example.com','055-012-3456','210123456',4,'24.04.2024 09:00',NULL,NULL),(1052,'YellowStone','2024-04-26','11:00:00','15:00:00',1,'Solo','williamdavis@example.com','054-234-5678','310123468',4,'26.04.2024 11:00',NULL,NULL),(1053,'Hyde Park','2024-04-27','12:00:00','16:00:00',8,'Guided','emilywilson@example.com','055-345-6789','211234579',4,'27.04.2024 12:00',NULL,NULL),(1055,'YellowStone','2024-04-29','14:00:00','18:00:00',10,'Guided','oliviamartinez@example.com','054-567-8901','212345691',4,'29.04.2024 14:00',NULL,NULL),(1056,'Hyde Park','2024-04-30','15:00:00','19:00:00',1,'Solo','charlottejackson@example.com','055-678-9012','312345692',4,'30.04.2024 15:00',NULL,NULL),(1058,'YellowStone','2024-05-02','07:00:00','11:00:00',1,'Solo','zoewilson@example.com','054-890-1234','214567814',4,'02.05.2024 07:00',NULL,NULL),(1059,'Hyde Park','2024-05-03','08:00:00','12:00:00',11,'Guided','jacobthompson@example.com','055-901-2345','313456815',4,'03.05.2024 08:00',NULL,NULL),(1061,'YellowStone','2024-05-05','10:00:00','14:00:00',12,'Guided','johndoe@example.com','054-123-4567','201234567',4,'05.05.2024 10:00',NULL,NULL),(1062,'Hyde Park','2024-05-06','11:00:00','15:00:00',1,'Solo','janedoe@example.com','055-234-5678','301234568',4,'06.05.2024 11:00',NULL,NULL),(1064,'YellowStone','2024-05-08','13:00:00','17:00:00',1,'Solo','sarahjones@example.com','054-456-7890','302345670',4,'08.05.2024 13:00',NULL,NULL),(1065,'Hyde Park','2024-05-09','14:00:00','18:00:00',10,'Guided','michaelbrown@example.com','055-567-8901','202345671',4,'09.05.2024 14:00',NULL,NULL),(1067,'YellowStone','2024-05-11','16:00:00','20:00:00',8,'Guided','christopherdavis@example.com','054-789-0123','303456783',4,'11.05.2024 16:00',NULL,NULL),(1068,'Hyde Park','2024-05-12','07:00:00','11:00:00',1,'Solo','amandajohnson@example.com','055-890-1234','204567894',4,'12.05.2024 07:00',NULL,NULL),(1070,'YellowStone','2024-05-14','09:00:00','13:00:00',1,'Solo','laurenbrown@example.com','054-901-2345','309012345',4,'14.05.2024 09:00',NULL,NULL),(1071,'Hyde Park','2024-05-15','10:00:00','14:00:00',12,'Guided','ethanjohnson@example.com','055-012-3456','210123456',4,'15.05.2024 10:00',NULL,NULL),(1073,'YellowStone','2024-05-17','12:00:00','16:00:00',7,'Guided','williamdavis@example.com','054-234-5678','310123468',4,'17.05.2024 12:00',NULL,NULL),(1074,'Hyde Park','2024-05-18','13:00:00','17:00:00',1,'Solo','emilywilson@example.com','055-345-6789','211234579',4,'18.05.2024 13:00',NULL,NULL),(1076,'YellowStone','2024-05-20','15:00:00','19:00:00',1,'Solo','oliviamartinez@example.com','054-567-8901','212345691',4,'20.05.2024 15:00',NULL,NULL),(1077,'Hyde Park','2024-05-21','16:00:00','20:00:00',1,'Solo','charlottejackson@example.com','055-678-9012','312345692',4,'21.05.2024 16:00',NULL,NULL),(1079,'YellowStone','2024-05-23','08:00:00','12:00:00',11,'Guided','emilywilson@example.com','053-678-9012','203456782',4,'23.05.2024 08:00',NULL,NULL),(1080,'Hyde Park','2024-05-24','09:00:00','13:00:00',1,'Solo','masondavis@example.com','053-789-0123','213456703',4,'24.05.2024 09:00',NULL,NULL),(1082,'YellowStone','2024-05-26','11:00:00','15:00:00',1,'Solo','jacobthompson@example.com','055-901-2345','313456815',4,'26.05.2024 11:00',NULL,NULL),(1085,'YellowStone','2024-05-29','14:00:00','18:00:00',8,'Guided','williamdavis@example.com','054-234-5678','310123468',4,'29.05.2024 14:00',NULL,NULL),(1086,'Hyde Park','2024-05-30','15:00:00','19:00:00',1,'Solo','marksmith@example.com','053-345-6789','201234569',4,'30.05.2024 15:00',NULL,NULL),(1088,'YellowStone','2024-06-01','07:00:00','11:00:00',1,'Solo','sophiathompson@example.com','053-567-8901','307890121',4,'01.06.2024 07:00',NULL,NULL),(1089,'Hyde Park','2024-06-02','08:00:00','12:00:00',12,'Guided','jessicawhite@example.com','054-678-9012','208901232',4,'02.06.2024 08:00',NULL,NULL),(1091,'YellowStone','2024-06-04','10:00:00','14:00:00',7,'Guided','kevinmartinez@example.com','053-901-2345','304567895',4,'04.06.2024 10:00',NULL,NULL),(1092,'Hyde Park','2024-06-05','11:00:00','15:00:00',1,'Solo','elizabethanderson@example.com','054-012-3456','205678906',4,'05.06.2024 11:00',NULL,NULL),(1094,'YellowStone','2024-06-07','13:00:00','17:00:00',1,'Solo','rebeccawilson@example.com','053-234-5678','305678918',4,'07.06.2024 13:00',NULL,NULL),(1095,'Hyde Park','2024-06-08','14:00:00','18:00:00',8,'Guided','ryanmartin@example.com','054-345-6789','306789019',4,'08.06.2024 14:00',NULL,'YES'),(1097,'YellowStone','2024-06-10','16:00:00','20:00:00',11,'Guided','sophiathompson@example.com','053-567-8901','307890121',4,'10.06.2024 16:00',NULL,NULL),(1124,'Hyde Park','2024-04-05','13:00:00','17:00:00',3,'Guided','reemi@gh.com','020-120-1230','111111111',4,'05.04.2024 13:00','Pending','NO');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cancellation`
--

DROP TABLE IF EXISTS `cancellation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancellation` (
  `OrderNumber` int NOT NULL,
  `ParkName` varchar(45) DEFAULT NULL,
  `Visit_Time_And_Date` varchar(45) DEFAULT NULL,
  `NumberOfVisitors` int DEFAULT NULL,
  `VisitType` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `VisitorID` varchar(45) DEFAULT NULL,
  `VisitDuration` int DEFAULT NULL,
  PRIMARY KEY (`OrderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancellation`
--

LOCK TABLES `cancellation` WRITE;
/*!40000 ALTER TABLE `cancellation` DISABLE KEYS */;
INSERT INTO `cancellation` VALUES (1100,'BlackForest','01.04.2024 09:00',1,'Solo','asd@asd.asd','123-123-1231','123123123',4),(1102,'BlackForest','04.04.2024 09:00',2,'Group','zzz@zzz.zzz','222-222-2222','123123123',4),(1103,'Hyde Park','06.04.2024 09:00',5,'Group','asd@asd.asd','123-123-1231','123123123',4),(1105,'BlackForest','01.04.2024 10:00',1,'Solo','asd@asd.asd','111-111-1111','123123123',4),(1107,'Hyde Park','06.04.2024 09:00',5,'Group','zs@asd.asd','222-222-2222','123123123',4),(1108,'BlackForest','26.03.2024 08:00',3,'Group','asd@asd.asd','123-123-1231','123123123',1),(1109,'BlackForest','26.03.2024 08:00',1,'Solo','asd@asd.asd','123-123-1231','123123123',1),(1110,'BlackForest','26.03.2024 08:00',1,'Solo','asd@asd.asd','123-123-1231','123123123',1),(1111,'BlackForest','26.03.2024 09:00',3,'Group','asd@asd.asd','123-123-1231','123123123',1),(1112,'BlackForest','01.04.2024 08:00',4,'Group','asd@asd.asd','123-123-1231','123123123',1),(1113,'BlackForest','01.04.2024 08:00',4,'Group','asd@asd.asd','123-123-1231','123123123',1),(1114,'BlackForest','01.04.2024 08:00',3,'Group','asd@sad.asd','123-321-1231','123123123',1),(1115,'BlackForest','01.04.2024 08:00',2,'Group','aaa@aaa.aaa','123-321-1231','123123123',1),(1117,'BlackForest','01.04.2024 08:00',1,'Solo','asd@asd.asd','123-123-1231','123123123',4),(1118,'BlackForest','01.04.2024 08:00',1,'Solo','asd@asd.asd','123-123-1231','123123123',4),(1119,'BlackForest','01.04.2024 08:00',2,'Group','asd@asd.asd','123-123-1231','123123123',4),(1120,'BlackForest','01.04.2024 08:00',1,'Guided','asd@asd.asd','123-123-1231','111111111',4),(1121,'BlackForest','01.04.2024 12:00',1,'Guided','asd@asd.asd','111-111-1111','111111111',4),(1122,'Hyde Park','04.04.2024 10:00',3,'Group','sys@sy.sa','321-567-7777','123123123',4),(1123,'Hyde Park','04.04.2024 10:00',1,'Guided','sys@so.com','055-882-1000','111111111',4);
/*!40000 ALTER TABLE `cancellation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `commingbooks`
--

DROP TABLE IF EXISTS `commingbooks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `commingbooks` (
  `OrderNumber` int NOT NULL,
  `ParkName` varchar(45) DEFAULT NULL,
  `Visit_Time_And_Date` varchar(45) DEFAULT NULL,
  `VisitorID` varchar(45) DEFAULT NULL,
  `AddedDate` date DEFAULT NULL,
  `AddedTime` time DEFAULT NULL,
  `AddedDelTime` time DEFAULT NULL,
  PRIMARY KEY (`OrderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commingbooks`
--

LOCK TABLES `commingbooks` WRITE;
/*!40000 ALTER TABLE `commingbooks` DISABLE KEYS */;
/*!40000 ALTER TABLE `commingbooks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `managerrequest`
--

DROP TABLE IF EXISTS `managerrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `managerrequest` (
  `parkName` varchar(255) DEFAULT NULL,
  `changeTo` varchar(255) DEFAULT NULL,
  `amountTo` varchar(255) DEFAULT NULL,
  `requestNumber` int NOT NULL AUTO_INCREMENT,
  `changes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`requestNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `managerrequest`
--

LOCK TABLES `managerrequest` WRITE;
/*!40000 ALTER TABLE `managerrequest` DISABLE KEYS */;
INSERT INTO `managerrequest` VALUES ('BlackForest','Set Park Capacity of ','90',39,'Set Park Capacity of null To 11111111111111111111.'),('Hyde Park','Set Park Capacity of ','80',40,'Set Park Capacity of Hyde Park To .'),('Hyde Park','Set Park Capacity of ','55',41,'Set Park Capacity of Hyde Park To 55.'),('Hyde Park','Set Online Booking Capacity of ','8',42,'Set Online Booking Capacity of Hyde Park To 8.'),('Hyde Park','Set Park Stay Time of ','10',43,'Set Park Stay Time of Hyde Park To 10.'),('Hyde Park','Set Park Capacity of ','11',44,'Set Park Capacity of Hyde Park To 11111111111111111111.'),('Hyde Park','Set Park Capacity of ','',45,'Set Park Capacity of Hyde Park To .'),('Hyde Park','Set Online Booking Capacity of ','',46,'Set Online Booking Capacity of Hyde Park To .');
/*!40000 ALTER TABLE `managerrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park`
--

DROP TABLE IF EXISTS `park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park` (
  `ParkName` varchar(45) NOT NULL,
  `MaxOccupancy` int DEFAULT NULL,
  `AvailableCapacity` int DEFAULT NULL,
  `MaxVisitorStayTime` int DEFAULT NULL,
  `CurrentOccupancy` int DEFAULT NULL,
  PRIMARY KEY (`ParkName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park`
--

LOCK TABLES `park` WRITE;
/*!40000 ALTER TABLE `park` DISABLE KEYS */;
INSERT INTO `park` VALUES ('BlackForest',8,4,4,0),('Hyde Park',120,6,4,0),('YellowStone',100,8,4,0);
/*!40000 ALTER TABLE `park` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkvisits`
--

DROP TABLE IF EXISTS `parkvisits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkvisits` (
  `OrderNumber` int NOT NULL,
  `StartTime` time DEFAULT NULL,
  `EndTime` time DEFAULT NULL,
  `VisitType` varchar(45) DEFAULT NULL,
  `NumOfVisitors` int DEFAULT NULL,
  `ParkCapacity` int DEFAULT NULL,
  `VisitDate` date DEFAULT NULL,
  `VisitDuration` int DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `MaxOccupancy` int DEFAULT NULL,
  PRIMARY KEY (`OrderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkvisits`
--

LOCK TABLES `parkvisits` WRITE;
/*!40000 ALTER TABLE `parkvisits` DISABLE KEYS */;
INSERT INTO `parkvisits` VALUES (10101,'09:00:00','13:00:00','Solo',4,133,'2024-03-24',240,'Hyde Park',200),(10102,'10:30:00','14:30:00','Group',8,466,'2024-03-24',240,'Hyde Park',500),(10103,'12:00:00','16:00:00','Guided',3,220,'2024-03-24',240,'Hyde Park',300),(10104,'08:30:00','12:30:00','Group',6,340,'2024-03-24',240,'Hyde Park',400),(10105,'11:00:00','15:00:00','Solo',2,200,'2024-03-25',240,'Hyde Park',200),(10106,'13:30:00','17:30:00','Group',10,500,'2024-03-25',240,'Hyde Park',500),(10107,'10:00:00','14:00:00','Guided',5,222,'2024-03-25',240,'Hyde Park',300),(10108,'09:30:00','13:30:00','Group',7,400,'2024-03-25',240,'Hyde Park',400),(10109,'12:30:00','16:30:00','Solo',3,200,'2024-03-26',240,'Hyde Park',200),(10110,'08:00:00','12:00:00','Group',9,200,'2024-03-26',240,'Hyde Park',500),(10111,'11:30:00','15:30:00','Guided',4,270,'2024-03-26',240,'Hyde Park',300),(10112,'10:15:00','14:15:00','Group',6,400,'2024-03-26',240,'Hyde Park',400),(10113,'09:45:00','13:45:00','Solo',5,150,'2024-03-27',240,'Hyde Park',200),(10114,'12:15:00','16:15:00','Group',8,500,'2024-03-27',240,'Hyde Park',500),(10115,'08:45:00','12:45:00','Guided',3,300,'2024-03-27',240,'Hyde Park',300),(10116,'11:00:00','15:00:00','Group',7,100,'2024-03-27',240,'Hyde Park',400),(10117,'09:30:00','13:30:00','Solo',4,200,'2024-03-28',240,'Hyde Park',200),(10118,'12:00:00','16:00:00','Group',9,500,'2024-03-28',240,'Hyde Park',500),(10119,'08:30:00','12:30:00','Guided',5,300,'2024-03-28',240,'Hyde Park',300),(10120,'11:15:00','15:15:00','Group',6,400,'2024-03-28',240,'Hyde Park',400),(10121,'09:45:00','13:45:00','Solo',5,200,'2024-03-29',240,'Hyde Park',200),(10122,'12:30:00','16:30:00','Group',8,429,'2024-03-29',240,'Hyde Park',500),(10123,'08:15:00','12:15:00','Guided',3,300,'2024-03-29',240,'Hyde Park',300),(10124,'10:45:00','14:45:00','Group',7,400,'2024-03-29',520,'Hyde Park',400),(10125,'09:30:00','13:30:00','Solo',4,200,'2024-03-30',240,'Hyde Park',200),(10126,'08:00:00','12:00:00','Solo',3,150,'2024-03-31',86640,'BlackForest',200),(10127,'10:30:00','14:30:00','Group',8,450,'2024-03-31',86640,'BlackForest',500),(10128,'12:00:00','16:00:00','Guided',5,250,'2024-03-31',86640,'BlackForest',300),(10129,'09:00:00','13:00:00','Group',6,350,'2024-04-01',240,'YellowStone',400),(10130,'10:00:00','12:30:00','Solo',2,100,'2024-04-01',150,'YellowStone',150),(10131,'11:30:00','15:30:00','Guided',4,200,'2024-04-02',240,'YellowStone',250),(10132,'08:45:00','12:45:00','Group',7,400,'2024-04-02',240,'YellowStone',450),(10133,'13:00:00','17:00:00','Solo',3,200,'2024-04-03',240,'YellowStone',250),(10134,'10:15:00','14:15:00','Group',5,300,'2024-04-03',240,'YellowStone',350),(10135,'09:30:00','12:00:00','Guided',3,150,'2024-04-04',150,'YellowStone',200),(10136,'12:30:00','16:30:00','Group',9,500,'2024-04-04',240,'YellowStone',550),(10137,'08:00:00','11:00:00','Solo',2,100,'2024-04-05',180,'YellowStone',150),(10138,'09:45:00','13:45:00','Group',8,450,'2024-04-05',240,'YellowStone',500),(10139,'12:15:00','15:15:00','Guided',4,200,'2024-04-06',180,'YellowStone',250),(10140,'10:00:00','14:00:00','Group',6,300,'2024-04-06',240,'YellowStone',350),(10141,'11:30:00','15:30:00','Solo',3,150,'2024-04-07',240,'YellowStone',200),(10142,'12:00:00','16:00:00','Group',7,400,'2024-04-07',240,'YellowStone',450),(10143,'08:15:00','12:15:00','Guided',5,250,'2024-04-08',240,'YellowStone',300),(10144,'10:45:00','14:45:00','Group',8,450,'2024-04-08',240,'YellowStone',500),(10145,'09:30:00','13:30:00','Solo',4,200,'2024-04-09',240,'YellowStone',250),(10146,'12:30:00','16:30:00','Group',9,500,'2024-04-09',240,'YellowStone',550),(10147,'08:00:00','12:00:00','Guided',3,150,'2024-04-10',240,'YellowStone',200),(10148,'09:45:00','13:45:00','Group',8,450,'2024-04-10',240,'YellowStone',500),(10149,'12:15:00','15:15:00','Solo',4,200,'2024-04-11',180,'YellowStone',250),(10150,'10:00:00','14:00:00','Group',6,300,'2024-04-11',240,'YellowStone',350);
/*!40000 ALTER TABLE `parkvisits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkworker`
--

DROP TABLE IF EXISTS `parkworker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkworker` (
  `WorkerID` int NOT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `Position` varchar(45) DEFAULT NULL,
  `Park` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `UserName` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `IsLogged` tinyint DEFAULT NULL,
  PRIMARY KEY (`WorkerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkworker`
--

LOCK TABLES `parkworker` WRITE;
/*!40000 ALTER TABLE `parkworker` DISABLE KEYS */;
INSERT INTO `parkworker` VALUES (1,'Alon','Barak','Park Manager','Hyde Park','alonos@gonature.com','052-123-1233','alon','123456',0),(2,'Adam','Kayal','Department Manager','0','adamos@gonature.com','052-134-2195','adam','123456',0),(3,'Ofir','Oziel','Park Manager','YellowStone Park','ofiros@gonature.com','054-672-9182','ofir','123456',0),(4,'Reeme','Cohen','Park Manager','BlackForest','reemos@gonature.com','053-928-4672','reem','123456',0),(5,'Ronen','Fridman','Park Worker','Hyde Park','ronens@gonature.com','054-293-8214','ronen','123456',0),(6,'Arianna',' Grande','Park Worker','BlackForst','ariannos@gonature.com','052-324-1234','arianna','123456',0),(7,'Chester','Chester','Park Worker','YellowStone Park','chesteros@gonature.com','055-312-6543','chester','123456',0);
/*!40000 ALTER TABLE `parkworker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `ReportID` int NOT NULL AUTO_INCREMENT,
  `ReportTitle` varchar(255) DEFAULT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `parkName` varchar(255) DEFAULT NULL,
  `file` longblob,
  PRIMARY KEY (`ReportID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1,'Visitor Statistic Report','2024-03-01','2024-04-02','Hyde Park',_binary '%PDF-1.4\n%ö\äü\ß\n1 0 obj\n<<\n/Type /Catalog\n/Version /1.4\n/Pages 2 0 R\n>>\nendobj\n2 0 obj\n<<\n/Type /Pages\n/Kids [3 0 R]\n/Count 1\n>>\nendobj\n3 0 obj\n<<\n/Type /Page\n/MediaBox [0.0 0.0 595.27563 841.8898]\n/Parent 2 0 R\n/Contents 4 0 R\n/Resources 5 0 R\n>>\nendobj\n4 0 obj\n<<\n/Length 146\n/Filter /FlateDecode\n>>\nstream\r\nxœ}Œ=\Â@DûıS‰Iv×œ¤*Zùu\ØsSb49ş{O±–†×¼)-ek°\r©Á‚¶¦\é\Ù>t=N¡\n~ş‚£{t}˜Á^£¡üc0ı/`\×`óªöUC\é\Â8º;”5Ox°L~«\é÷}e\éIFLª\Ì,\à5yZÄ…Æ¥¥l\Û\n–\è\r\"@2i\r\nendstream\nendobj\n5 0 obj\n<<\n/Font 6 0 R\n/XObject <<\n/Im1 7 0 R\n>>\n>>\nendobj\n6 0 obj\n<<\n/F1 8 0 R\n/F2 9 0 R\n>>\nendobj\n7 0 obj\n<<\n/Length 14596\n/Type /XObject\n/Subtype /Image\n/Filter /FlateDecode\n/BitsPerComponent 8\n/Width 736\n/Height 364\n/ColorSpace /DeviceRGB\n/DecodeParms 10 0 R\n/SMask 11 0 R\n>>\nstream\r\nxœ\í\İ{p×\èñ‡\ŞINb°°\å8¤Øº!`\×öúQµ,q\\»kV\ìî¥¬¤bªb)\å[{]„T,o•—J\å^W„w¯\ã]C¹n\ÊD\É¶£x÷:&Ä—µ‘³›@ü\Ê\"\ëldAd@z\ÍL\ß\ßô5M4õ\é\Ñ÷=gF­Ó§Owÿ¦»s\"¦i\Z\0\0\0:‰]\0\0\07\0\0 \0\0 \0\0 \0\0 \0\0 \0\0 \0\0 \0\0 \0\0 \0\0 HPcñôõõ=z´½½}pp°££\Ã.—\éõ\ë\×\ËDWW—*\ì\é\é	¤’\0\0 ñ@~kgg§<®^½Z›šš\ìøc×®]*:Q\Ú\Ú\Ú6o\ŞH\r\0@€‚	Pº»»ûûû:\ä,\ì\í\íı\Â¾`?\Z\ZZºt\éœW\r\0\0/˜\0eFp/\ÙmY³fÍ;T\Éñ\ã\Ç\Ç\ÆÆ²vdd\ä\Ê+¯¬«««®®^¸p\á\ä\ä\ä…\ÔKÁ–LLL|\ä#™ššÒ¤>—\Ô\ÖÖ;w.\ZjRJ$—§ñx\\“ú\èP\"²9/^¼X“ú^RUUuñ\âE\Ù\ËiRJ\ä\é\èè¨´Œ&õÑ¡D5²w•mG“ú\ÌV\"5,\äD`÷ ¨3(\í\í\í\êi__Ÿ<:¯\ï\Øö\ì\Ùs\ã7ª—$I&“\Ù\ïy\ï½÷®ºêª††Y7²£O¥R‰DB½l‰{š››¥‘5©O\à%\Ò ,Z´H“ú\èP\"m$‘-V“ú\èPrşüù\Æ\ÆF	g5©O\à%\â\Ã?\\¶l™&õÑ¡D¢ù¸`ÁM\ê£C\ÉğğpMMl8š\Ôg¶\Ù\İI%|t	Pv\í\Ú%\ÓMMM\Ù\ï”\0eÃ†\r­­­9\æv\äÈ‘«¯¾º¾¾¾,u-ÁÀÀ€(rø	º\"º\0_=\\¼s’}ŠôµŸ…rö\ìY‰bÕ‡cÙ¹Ÿ9sFv&AWD#ccc \\q\ÅAWD#\Åx\ì–ú<–÷mº\\\â‘¹tvvnß¾]Â—ıû÷\Ë\Ó;\î¸#wt¢3	f%$@±ISp\Ôq‰\Åbô\é$…\ì¿\æ\é!\Õ\Õ\ÕA\×B/\êsyĞµĞ‹4H%m8Añ—¶gP\0\0€SgP*\'\Ô\Ò\Ó\ä\äd\ĞUĞ‹\ÄSSSA\×B/‰Db\Æ;«\æ3\é$•ñ\Ù\É/l8\Ù\\÷\èÀ°v&\Ò,A\×\Â7(\å544\Ä~\ÖI¶Ÿó\ç\Ï]½ŒY‚®…^¤“p\ìq’İˆ\Êö‚mbbbdd$\èZ\èexx¸’>‡õ+ß¸’bF\0˜·\ÌÁM½óx$bD\Ò7\ŞÈ£‘~´\îÑ²o\ÔR÷l\ÅR©zÓŒYW\nÔ§@ù0(S™GS&¢»+\Şz ‚Ò…5@iiiq—x%À\Ê\0\0ü1y\Îøı‘ô„¡\\ŠQTI\æ…ôDÔŠHŒttbFL+F±B\ë1}\æ\ÚL\æŸnIPª°(®œLm“ \È\âq!‹\'Y<\Ù\È\âq!‹…¨°,°(a±xñâ « \Ù~\ìoiƒBöY6:‰‹(\ìLWccc\ĞUğS\å„Zzª¤û•|A2B6²x²‘\Å\ãÂ†ƒBÅƒ\"\Å\ãBO6²x²‘\Å\ãB\nQaY<(\0\0@;a½…4c\0\0*XX”°¤“\Å\ãBO6²x²‘\Å\ãB\nAÂ’fÌ÷.dñd#‹\'Ä…,‚,¡’\îWò\É\Ù\È\â\ÉF\nAŠ@Y<\Ù\È\â\ÉFY<(Y<\0\0\0\åE€\0\0´Ö›dÃ’fLY<\Ù\È\â\ÉFY<(Y<ZKš17Ş»Å“,lt²xPˆ\n\Ë\â	k€–4\ã\É\ÉI>÷8™¦™H$8‰\â$\r\"8‹]LMMI,«\ív=÷\ØpP\é$QK\ĞñG…,†¶\È\âq!‹\'Y<\Ù\È\âq!‹… ‹\0\0 ¼P\0\0€v\ÂzJX\Å\ãBO6²x²‘\Å\ãB\nANŸ>=11a?\Õ6Í˜\ï]\È\â\ÉFO6:‰Y<(Y<ZX°`Amm­ıtxx8À\Ê\ä@\É\Ù\È\â\ÉF\nA–,Y\Ò\ätufEY<\Ù\È\â\ÉFY<(Y<\0\0\0\å\Ø%¾¾¾£G¶··\Ëtoo\ï¾}ûdbÍš5;v\ì°ß³eË–\ìB\0\0Pñ‚	P:;;\åqõ\ê\ÕvIWWWkk«ó={ö\ì\é\è\èX¿~½LH4#s]K?\Å\ãBO6²x²‘\Å\ãB\nQaY<Á,Iww÷ö\í\Û\í§gÎœY²d‰\ë=o½õ–\nJ6l\Øpô\è\Ñ9­Ÿ/^\\Iİ¥tdñd«¯¯¯««ºz‘N\Â]\ÃNdñ •\È\ê’\Å\Ó\Ñ\Ñ!mmm›7oV%---\ÙoseÛ¤ğÜ¹s\ê\Îÿ††™¸xñ¢z)\Ø\Ã\Ú\Õ\êSŸ2•D\Æj=!Ÿz\ÍT*™JF\ÒûS#\Z‰\È\ÛRijXÇˆ”\Ä\âñd2¡\Ò\ÂM3½ÛµJR‰D2e¦KŒˆ¿ò“\æªv–«”’ºs?Kú¡,©j\r\ë\ÔQ<\İ>\Ö —ª$O·\ØT:‹Gı\\¦\ÅÉ¤4E*}wu$‹\ËÓ‰\æ»Í¦\r:,W)%‰·»c£\'dÉ£Ó­1\İ7Œh4³\ì\Ò¤1R¦©J\ä©j1i\é/ª·L%’é¬–Š5´\è°\\K\"f¢\æ\íoD#\ÑøtkD.õ¸iªeX…\ÒjòQ\ÇLZ}\Ã\êB‘hlzÛ±\î¢5j–\Ö}öQ–«”’\êñq°Ò¦ö]ùº-i9J\Ò;\ë,µ&õ™­drr²€[‹\0¥\İbX—~Ö­[§²r\ì7,[¶LM¸²‹mCCC5559ªO]\ÑhÔ\"ƒ-‘mc\áÂ…úÔ§L%‘ññ\Ø\ï~lZ»\Ò\êô®\Ô\È\ìg\å=Ö¡7’ù“µv¯f\æOúo4e\Ä\Ó\ÇcS\Éº¤’c7|I‡\å*©\äÂ¯#¿ı¿q«\"ªM\"\é3–q»9Ô‘\Ç*1m±š(•ùk¦c9Ó¨º\âS†5ó\à—«„’ø‡‡cC¿ˆL/¼´L\\u\éN\"\íSQM‘iiœ¸\ÕO2\rbŠc\Öt\Õ\'¾³.¾\\\ŞK’©ø\ï^¶+ \ÏD\'—\Ú$\î\ØpT\'‰©¶±¦¥¬m\Ç\Ì4NıÕº,W	%r03ü\ã¼4¦Û’–£dttT«´\ß.\äx=\Ó\êr\Ó\"@q²s†W¯^­n=9t\èĞ†\rTaö• \åı÷ß¯³¨§\Ò®s\æA•¨\à]Ÿú”©Äœ¨ñ÷›ò4Y®RJüm\Ù\ã\Ä3\×jI/1|½\Ü)ŸU\"\ÓA›nKZ`‰™ğ³Ÿ¨;™tX®RJ\Ìx\Ü\ß6\Ñd¹\æ¦d||¼Ú¢I}f+™ñDC6-n\è\ì\ì\Üb¹\ï¾û\Ô\Óşşşöööİ»w«D\×ı³\0\0 ²v¥Õ¢¦»»»/\ÙO{zz\æºZ~#‹\007*,‹G»K<†\ï\0s£\Â\Æâ©œPKO•ô­\Ã\0\0%	m‡\Îõ ¬gP\Â2šñ\Ğ\ĞPss3—x\0\0\å6<<\\WWW\à-¨úk€–ÑŒ\0€a\rP\\ù\Æ\ï¿ÿ~P5\0\0¾\ã”òRY<A\×\0Pù\È\âA\È\â\0\Ì\r²xP²x\0\0s£Â²xP\ÊkhhˆK<\0€90<<\\IŸŠ\Ãz‰\',i\Æ\0\0”\Â<õ‚ñû\ÃÖ˜’™¡F§\Ç\×4\ìÿ\Ôô¢‰‰h,–p13ş¨\Z<ı19=@ºõ4ºrkôŠpŒ\Ö\0…4c\0À|`ù¹y\â™‘\á#*B±¦\r÷\Ğ\èéƒ¢=Üµ:wo…&\é\è$•õ:rÕ­JY…%Í˜±x\0\0ğ ¬JX\Å\0€\Ü$[^•t¿\0\0s†\0¥¼\È\â\0À\0\0 °ŞƒBš1\0\0,¬JXÒŒ\É\â\0Àƒ°(aI3&‹\0\0¸¥¼\È\â\0À”ò\"‹\0\0P\0\0€vP\0\0€v\Âz“\ìÀÀ€óöm/£\Å\0€a\rPÂ‚,\0\0<k€\Ò\Ò\Ò\â|zö\ìÙ j’\Û\ä\äduuuĞµ\0\0 d¸¥¼\È\â\0À\0\0 À”¾¾¾={ö¨\é\Ş\Ş\Ş-)´\ß\Ğ\ßß¿eZ@u\0\0Á\æ”\Î\ÎNy\\½zµzºt\éÒU¾~ızûmmmm›7o¤†~!‹\0\0‚	Pº»»ûûû:¤:ƒ\Û\ĞĞ.®BWv±mbbbxx8™L\Æ\ãñºº:™¸xñ¢z)Ø’\Ú\Ú\Úh4ªO}\ÊT­\Ë^+%\Ğd¹J)©ñµA¤‡Y#b¾\\¥”¤·Pÿ\Údtt4\Óa¹¼—$\Ç\ëık5¨»\ËUBIõä¤D>\ÚC\Éê¶¤…—ø{#\ã\ØØ˜4J°\Ë%\Ó.\Ì[U\îAÙµk\×Î;%»w\ïŞ²e‹”U¥\Ò%‰ «\0\0@øD‚\Ê1QgP\Ú\Û\Û\ÕS	DºººZ[[³ß¹gÏo¼qÆ³,¶#G\\}õ\Õõõ>~\Zñ\ÇÀÀ@sss\Å_\â1\Ïõ§öÿ±,¤,h4’^\\5^lk\Ñ#™?Ö›­gfş¤ÿ\Ê¿”)\ÍôcÊˆ´\ÜRı_÷·4şHıóo\É\"GeÁU›¨f1»1\ì~\ánS5ˆ\Õ&V\ã\Ä>³3~Ã¶ –\Å/\ÉW¶F\Îş\Ût÷ˆX½%]±;\Éô´iL7GfbºAR\Óı\Ä4jş\è\åH\ãµA-‹/\Ì\ÄX\ê¹Õ™¾‰D\ÕV\ãj“\ì\rgz\Ú\Õ f\ÃòšM‚Yÿ˜\ïÿ8õúWfÛ™8Z\Åzs\r\Çjœ\èÊ¿¨úƒG‚[\Z¤~±\Ó<ñƒhö†c¸w&\æô¿\é-\È\Ñ&©L?‰\ßòTì£·¶0–ºººh4ÿù-¾¥³³Sİƒ2›%K–\ÌYe\0\0@\à´P>ø\à;U§««\ë\É\'ŸÜ¾}û¡C‡ö\ï\ß/%w\ÜqÇŒgV\0\0@¥\n,@iµ¨i\×\é“\î\înõûPx‘\Å\0€ZœA©`Œ\Å\0€a\rP\Â2š1cñ\0\0\àFi\Æ‰±x\0\0ğ ¬gP\Â2š1\0\0ğ€3(\0\0@;(å¥²x‚®\0\0!\ÖK<aA\0\0p¥¼f\Ú\0\0\ä\Ö3(aI3\Z\Zšcñ\0\0\à/Î \0\0\0\í„õ\ni\Æ\0\0T0Î ”Y<\0\0x\Ö3(aA\0\0p¥¼\È\â\0À”òb,\0\0<\ë%9ğ\'‰ k\0\0\Ê\"¬\Ê\È\È\È\ÄÄ„ı4•JX\0\0à¯°(Ë—/w>=r\äHP5\ÉMeñğEm\0\0%¬JX\Å\0€\Ü$[^dñ\0\0\àJy‘\Å\0€ş(Y\Ûö\Ì)_f\0\0\à\Ï=(=|\Í3mk×¾k\Üğ\àsû¶­ğe¦9‘f\0@ó\í&\Ù\Ûö\Şf§Tœblzüğ£ıš÷Â’fL\0\0øÅ³\â\ÚU†!Ê‹­}±œaJXÒŒ\É\â\0À\ß”SÏ´\İû„:urø\ÑKem\Ï\\3\'—|t599Y]]t-\0\0”ƒ¬}\ê\Ú\çvE\"+n½\Ó\Ø{Rş÷å—„\Ò\Ğ\ĞPss3—x\0\0(Šo7\É\Îx%gÅ¶}\ÎT.úúú=\Ú\Ş\Ş.\Óııı]]]2\Ñ\ÖÖ¶yófû=[¶l‘\Ç5k\Ö\ìØ±Ã—z\0€P()@9ø\ÈÚ‡^œù¥\ÜwŸtvv\Ê\ã\êÕ«\Õ\Ó\'Ÿ|r÷\î\İMMMR¾n\İ:™\Â={öttt¬_¿^&$š‘‰Rª\n\0\0B¤¤\0eã£—\î7)Jwwwÿ¡C‡dzpp°¥¥E%·\ß~ûñ\ã\Ç\Õô[o½¥Î¯lØ°AŞ©”Ù²‹§¦¦\Æ\Æ\Æ\"‘H,«®®N&“öW¸[RUUešf*•Ò¤>e*‰LLT\åZ\áE\Ód¹J)ñ÷şó)«‹\ë°\\¥”È†ó¯M\Æ\Ç\Çc5“:,—÷’ä¸w¨©\ï„\Ôb¹J(‰\'>~¨´É˜µ\á¾\\¥”øûmŸé™»\\r°®¯¯\Ï[\Õ`Î ¸¨ˆ\ÄE¢–\ìBWv±Mv|R·HC8‚`Kjkk£Ñ¨\\4©O™Jd	ı\rP4Y®RJü\rP$.OY3|¹J)ñw?+›UjjJ‡\åò^’œô=@\Ñb¹J(‘¾\îo›h²\\¥”¤¿«Â¿6Q;“`—KB–²(Ï ¸\Ú\ÓK–,Qv\á²e\ËÔ„+»\Ø6<<|\ÅW\Ø\\UUµh\Ñ\"\ç‚*Q\ëCŸú”©\Ä4øûE4š,W)%ş6H]]]\Ü1s­–´ğ’d\Ì\Ç(Fccc¤¡¡\Üu.k‰™¨ö±Ÿ\Èg¡9¨s¹K\Ì\áZ\ÛD“\å*¥D–\Â\Ç\Ğ^”±\éùµ\\²C+¤ªÁ\Å\Ó\Ô\Ô$±ˆŠQ8\Ğ\ÚÚª\ÊW¯^\İ\×\×\'‡ºşúëƒ¬b	‹\0\0‚P\Äö\í\Û;::¶l\Ùr\ß}÷\Ö-´ııı\í\í\í»w\ïV‰<v\Ô\0\0\æŸ.”OM›-\ï=(­{º§§\Ç~©»»[M8\0Àü\á\Óh\Æ{ŸXõø\áÃoº\áÁ\ç§ÿ\ßt[9\â	5OĞµ\0\0 düJ5¸\á\Úk\äñZ\ã§\'\rc\Å5\×\Û{\Ğ\ØX\Î%,£3\0\0øôM²·­zh\ïÁmnû²±v\íZÃº\Â\ãËŒg–ÑŒ\'‹\0€\âùteú»\îıJ<\Î+,£3\0\0øt\Ê#mÏœºôô\Ô3môe\Æ\0\0`>*Kšñ\É\ï\æ\0\0À,J¾\Äc\İı‹kŸ¸Tº\éñ9¹Ğ£?•\Å\Ã%\0\0ŠRr€b\İurğ‘¶÷\ïÛ¶Â\ZU²x\0\0ğÀ§,G÷\Íñ÷„%Í˜,\0\0<ğw\ÈÕ¹–4c²x\0\0ğ ¬JXÒŒ\0€Z\0\0\à\ä\×÷ ¬U_„rê™¶µ\â²oE™\×‹\0\0ü:ƒrÃ·®ğd\Ç\Ëw>wøğsw¾¼—/j³,^¼8\Z\å4\0\0\Åñ\çØ¹ñş;_¾w\í\Ú{ŸXõ\åm§¼ò²q\í5¾\Ì8ô&\'\'ƒ®\0\0\á\ã\ÓM²+¶\í;¼\ÍñdŸ?³\İ\È\ÈH2™´Ÿj{…,\0\0<\ëX<§´\rP\0\0€eI3N\Åsm9f|\ÉÊ•+OI3\0 ’0Oy1\0\00Oy1\0\0„u,°`,\0\0<()@9õLÛ½/\ßùÜ¾m\'§¯ó\Ø6=~øQb²x\0\0ğ¤¤\0\ÅN.^‘¾\Î\ãK}\n–4c\0\0\àAX³Ÿ \0\0PI\Â:\ÏÊ•+?\å \í\×\É3\0\00Oy1\0\00Oy1\0\0?Ooo\ï>\Çû{zz\\\åkÖ¬Ù±c‡?õœsdñ\0\0\àAğ7\Én¶\ÈDÿ{\ï½\ç|©«««µµ5 z\0€Àhô=(\Ï?ÿ¼óLÉ™3gÖ­[\çz+»\Ø&…ñx<\Z\Êc*•J$\ê¥`K}\êS®’©©XöZ).\ËUB‰¿wÉœS\Ö\å\ÂÀ—«”’ô°şµ\É\Ô\ÔT4‘\Ğa¹¼—$ı\Üp\ÔıøZ,W)%É¤¿\'œ\í\ë\ì\Ú-iÁ%şfZ\ÈÌ“““Á.W,«­­\Í[U]¾¥¯¯ï¦›nrvtt\Èc[[›:\ÅbdeÛ¤J\ì\"\Ë_]]\İ\Ø\Ø(­0<<¬^\n¶DVƒô-}\êS¦’\è\è\è\Â\ìµRM–«”’_D\â\ï„5óÀ—«”3™ô1nW\é°\\\ŞK’\ãWø\× \ê0¦\År•PR79Y\ã_›\ÈAaD\å*¥D–\Â\Ç\rG¡\É\á\á`—+™L DJ	\Í\Ò{ğ9?\áÙµk\×l7štvv\îÜ¹³©©)Ç9r\äê«¯®¯¯/¹\"ğ\Â<×Ÿ\Úÿ\Çò¹\'1¢‘ô-7j:ıI\Èú4\Éü±\Şlõ83ó\'ı7•2R¦ü5Ó)#\ÒrKõ\İ\Ü\Òø#uô\Ìw¾%‹•Wm¢š\Å0\ìÆ°\ïMr·‰©\Z\Äj«qbŸ\Ù¿a[P\Ë\â—\ä+[#gÿmº{D¬Ş’.Ødz\Ú4¦›#31\İ ©\é~b\Z5ôr¤±\ÌÃ¦—™™K=·:\Ó7\"‘¨\Új\\m’½\áLO»\Z\ÄlX^³\é@0K\âóı§^ÿ\Êl;G«XoÎ±\áX]ùUğHpK\ã\Ô/vš\'~\Í\Şp÷\ÎÄœş7½9\Ú$•\é\'ñ[Š}ô¶À\ÆRWWW\ÈY\æ’Â²ô@_>q\ïÚµ%~÷\É\à\à`\î7\äNtF\0\0ø3šqz\Âú¶weâ†¢Ï©?~Ü¾¾#ÁJGGGOOOgg\ç|`L_\è	)²x\0\0ğÀ¿,û†+RYU\ÌM²\ë×¯·§›ššT¦qww·ou\0\0¡\â_€’N\éy\"}%Ás˜‘Œ\0€g%(\í\ã9K\Â2š±\Z‹‡K<\0\0¥¤\0%“\Åsøp\éY<\Å\n\ËhÆ‹/º\n\0\0„OIJúY¿*R¤•+W:Ÿ9r$ Š\ä199Y]]t-\0\0\Ú-¯¡¡!m\Ï\î\0\0 -\0\0 \0\0 ”òRY<A\×\0€ñ\ï{P\æ\Ö\ä\äd*•\nºù‘\Å\0€a\rP?~ñ\âEû©¶_4B\0\0„5@ù\Ä\'>\á|ªmš1cñ\0\0\à÷ \0\0\0\í \0\0\0\í ”Y<\0\0x\Ö{PÂ‚,\0\0<k€–4c²x\0\0ğ ¬JXÒŒ\É\â\0Àƒ°(aI3\0\0p“,\0\0\ĞJy‘\Å\0€a½\Ädñ\0\0\àgP\Êkrr2\è*\0\0>a=ƒ–4c²x\0\0ğ ¬JXÒŒ\0€a\rPH3\0 ‚qJy‘\Å\0€a=ƒdñ\0\0\àgP:;;·Xúúú\ì\Âşş~U\Ø\Û\Û`\İJD\0\0hq¥¥¥¥»»\ÛUø\ä“O\îŞ½»©©IÂ—u\ë\Ö\ÉD u+Y<\0\0x E€’mppP¢”\Ü~û\íÇWÓ³e\'-‰D\"j1MSª—‚-Qô©O¦$•Lœr½\'Uğ|LWI4I&ı\rÁ´k±\âK|mCºı\è\Ä\èDr\â²\ß)¸>wICuCM¼&\ÏOME’	gI¬\à\ÖHe•u\äm>ö\Ù\Şc©”në½¸_7u»›\ËUBI\Ä\ï\ï›KŒ]ö»R\×\Çt—\Ô\Äj\êª\ê\æ¾}|l™¹ô±R&¢\ì$µP\Ş|ó\Í-[¶È„:e¢\ng<e\â\Ê.¶I\àò\á‡\Õ\Ô\Ô,Z´hjj\êÜ¹s\ê¥`KT\ÇÒ§>ªdòÃ³É¯oD\Ò¸”uzGM‹ˆ¤¬S>1µ\ÖcÒ±…\È\ä”4ò6Sşš©\æk’ÿ\í«Wd¯•\è\ÖbJ\Z}m\Ñ\Ñ\Ñ\çöü\Ë\Ù‰X«\êÒ£b=S¦õ\ÇÈ¬¿t?”?²¶,F\ÊØ¶r\Ûm¿-÷R\Ô?÷¿¢oı¿ˆ5Wù]\ÑtÇ5\ìcºÃ˜\Ö\Õ\â\ÌRB«“¤Ò¿\Ğ\Èü5\Ì\êŸˆ$\Õşµ\Éùóç«¢Ãº­÷\âJ’\ãWú\× z\îmŠ-©«ó¯M$²ÿññ?ÿŸ\Ï_¶\áıc\ë±\ZQ=d¶ôÿJ*³\á\Ü\Õr×¦k6\Íqû\ÈRøø‰gdd$14\ìZonn\Î[U-”Ãº\édÏ=;v\ìP…ƒƒƒö–,Y¢&\\\ÙÅ¶#G,]º´¾¾^=­®®v\Å7A•H\Ø$\İZŸú\Ø%c†o\âñø¢%Küı\Ôxû”^’ú\ÏY–Í“\Æ\Æ\Æ:\ÃÇƒ»¡6–\ÜK1QU•œ\áG=ªªªŠTUù7?\ã\Ê+¯Œ4.24[\ïE•˜‰17õ‘T‡\å*¥Äœlğ·Md\Ûño~FCCƒk¹mŸX,\æ\ã)©lzşA­åºº‚¢P-n’µ9OŸ¨\åÀ­­­\ÖË»Å‹r\"\0\08¥¿¿¿««KM÷ôôHP\Ò\Ñ\Ñ!Û·o—	)T!599)acĞµ\0\0 d‚PZ[[\Õ%¥©©I=u•‡Y<\0\0xÀ\Õ\0\0 \0\0 \à/ñxó\ÆoŒŒŒ\ØOkkk¬Lj,.ñ\0\0P”°(Ÿşô§OµÍ˜±x\0\0ğ€K<\å\ÅX<\0\0x@€R^CCCş~K1\0\0ó\n\0\0\Ğ\n\0\0\ĞNXo’-Š™˜šó.£ğ¤\Z3k*¯2\n\Î\Ê!‹\',\ÌT\ÒH]6òLQ\ë\Ì\İO¢1?*Ç”½]*\á\×Ü¢‘h,BO.š™L\Ê?gI)G#6WAX”¢ÒŒ\'ş\çS§û#\é\í\Í\Z\Ï\Ò\È™ñ,§e5¦»„=(«a\r\Øk^\Z”5=\Ñø\Í£W\Ì0\ÒòŒ\È\â	‹Ä¿¾4õşF\êqŒğ<=\è\ée£«şafş·\Æ¶º‡5\Üiz¢ú®¿ªneˆÌµ3\Ïüu\ß_g\Æ\ëuŒß›–c\ä^{:•¹W\â»r\Ñ\Êo|ös¾¡—øç§§şyoî‰óˆ“µ3¹4¸ü©\Ùúµ*\Çw•°(aI3f,\0\0<\à”ò\"‹\0\0P\0\0€vP\0\0€vP\ÊKeñ]\0\0B&¬7É†Y<\0\0x\Ö\0%,£“\Å\0€a\rPÂ’f<44\Ô\Ü\Ü\Ìµ\0P\îA\0\0\Ú!@\0\0\Ú!@)/²x\0\0ğ ¬÷ „Y<\0\0xÀ”òšœœº\n\0\0„OXÏ „%Í˜,o\Şùı;\ß~óÛ—\È:=\îô¥±X­W¦\ÇµØ£³¦Ó¼4.\ëú–õ\íŸlŸó…@\Ù}ıõ¯Œ\r8‡\íuwcö~buôX33Š\ï\ß\ßú÷uñ<\ãÆı»ñQ{pZ{¬Z\Ã9V\í¥\ßsi\\tó²qÑ­\Z¯iüÖ‹>´P¤\'\Şz\âÍ³o^6\Şu\äò‘®œy\Ù>ö‘\Ï>²b\áŠr\Ô3¬JXÒŒ\átû©\ÔTfûqŒŸ–c\ÔøÌ¡\à²\èDşOšÉ¹_Ì)s*‘J\\¶“u\ãò`\Å\î\"\0ÅN\n¼W\ÌLLE\ÖiQ\Ç/2­ÿM+^1§\át\\ryŒb?—ÿ#œÀF0d«Q;²7û\Ö\Ù7gtb=¤\ÊTO¶\0\0 ”ò\"‹\0\0´¸\Ä\Ó\Ù\ÙùÁ\ÈDOO]\Ø\ÛÛ»o\ß>™X³fÍ;«\\i\È\â\0Àƒ\à”ÁÁÁ\íÛ··¶¶öõõIP²yófû¥®®.)°n¥c,\0\0<şOSSÓŒQÈ™3g–,Y2÷õñ\×\Ğ\Ğ—x\0\0(VğgP”ÁÁÁƒº.\åttt\Èc[[›}ZÅ•]\ì400PWWW[[»xñ\âñññ?üP•KI\ÄÁ\â«j%Õœ³—«D\Éı¹/™˜˜ğ±A‰„D“Wú8G¿¿?fll\ìw¿û]\îö‰Ÿ;\×\à\ßo”yV~9»p\á\Âh\ÌÏ<::j\ä\ë™&\'cşıFY§‘\É\É\Zÿf(½.•ò3}@–ı\Üğ95=[oññ\×\ÉG\Ùk5ù7\Ãd\Ò\çlµ©©)\ÙpŒ¹\İG-©÷o¤MÎŸ?\ï\ßü9\0IUs/E\Íğ°¿\ß{!K\á\ã¹©¤TÕ¿ù¥»–\ë\ë\ë?ò‘ä­J__\ß÷¿ÿı\î\înga»Å°\îPY·n]SSzCveÛ9\Ò\Ò\Ò\"Ë¬Js\\u\ÕUö«ş®\n©IôŠK{\×\ïr•\È(\ï{æ¾¤¦¦f,\×\"\'/Z¶\Ì\ß<3¯‹I\äj·Àl\í3uòŠ)ÿ~£\ï_Ì³p\á\Â‰x†|›aCC:\Ë\İ[&ª«}<\â\É:øºZ—-[ğópz7º`±«\Ä\Õ>>ŠD\"²\×òqÃ‰\Å|Œ\'Óªªªœ‹?7û(3¹À\ß6Y´h‘1\à\Û,X \â\ÔK1\Õ\Ø\è\ã\ÎÄ°–\Â\ÇóğRÿ\ÚD­1ó\'}Š]Ë²O.d¶Á_\â\é\ï\ï?zô¨+:qQ\ÑI‘\Å\0€ÁŸA\Z\Z\Úo‘\é\æ\æ\æ;wvttôôôØ©=\êBOH‘\Å\0€Á(\ë-\Î•lœûœJX\Å\0€Á_\â©ldñ\0\0\à\n\0\0\ĞNğ—x¼ù÷ÿ÷‹/\ÚO.\0\æ^ò\ä¯&ÿ÷·GuVƒ<\Û\Ã\Í]\Z\ßSª8}6\Ù\çY\íœpÎš}rC\İ\íÿ%€Å€–\Â\Z ¬\\¹\Òù¯X™Tñ€ŠI&\Í\á§G\Zw\"\æ\Z\á9Sh\èœù/]ù\Ü\×\äW„\\X”°\ÜyJ\0\0pJyùû¨\0\0\Ì(\åE\0\0 \0\0\0\í \0\0\0\í„õ&Ù°¤“\Å\0€a\rPÂ’fL\0\0„5@	Kš1cñ\0\0\à÷ ”Y<\0\0x@€\0\0´C€\0\0´C€R^*‹\'\èZ\0\02a½I6,i\Ædñ\0\0\àAX”°¤“\Å\0€a\rP\Âr\Ô\Z\Zjnn\Öö\0\0z\â\0\0 \0\0 ”ò\"‹\0\0\ÂzJX\Å\0€a\rPÂ’fL\0\0„5@	Kš1Y<\0\0x\Ö\0…\Ó\0\0T0n’\0\0\Ú\Ñ\"@Ù³g\Ï\Ë\à\à ]\Ø\ßß¯\n{{{¬[‰\È\â\0Àƒ\à	D$.\é\é\é\é\ê\ê’H\Å.ò\É\'w\ï\Ş-\åp.\á²xñ\âh4øF\0 \\‚?v¾÷\Ş{7n”‰\Ö\ÖÖU(IKKKSS“L\ß~û\íÇ²Š\0\0`nE¿\0\Ñ\Û\Û{ıõ\×Kt\"Ó\İ\İİ† ü\èG?jooWoXºt\éúõ\ëeZ\"•±±±ì™œ={vjj*™L\Îø+bfÒ‡,š\é†JD‹¸³¸¾¾Ş™­¸9s[\Çj3IE¢±Hf†¹›:\ZE«ª\î•h^6-óŠ¥\")\Ã\'©¢™§È–3üøV›Hƒ\é?™\æşİ±ª\Zù©db\Ò=\'\ÇtÊˆ&\Ó\Ë\á\Û\ÖZH›\Ä\ÌT\Äğ\ã7Zm’”U\É\Ì0\ïöXUSŸ˜\Z7S\î5\â¬MÒŒ%£~î¾¢©ü\Ø|\Üp¤\ÑX<’Peù:h¤º¦ar|d†™9¦f<õmÃ‘YG\Í<m\"+Tú‰¿K\íL¢\Ò1£\îLbqù›˜w\Ï\É1È†5¥\'ù¶\á¤\"±H´\Ğ\r\'^]—J&R\É)÷œÓ²\á¤g\ç_›²á¸ŒşÉŸüIŞ·i‘\Å344¤&ZZZ\ìB\çe%K–\Øo˜1\n‰\Åbñx\\\Ãl^YŠë®»«<6‰#‡‡‡—|lE\Ğ\Ñ\È\ÈÈˆtİ††† +¢\Ù\'46^UUUtEt‘J¥~ÿû\ß_õÑ]ŒOLL,Z´(\èŠh\äüùó555µµµAW$\×Zğ\Êõ\×_ÿüóÏ¯_¿¾¿¿_]\Ó2100 Gw™8p\àÀ\æÍ›Uù‚fœ‰¶}ô\ìÙ³ÿø\Ç%x\nº\"º¸p\á\ÂÉ“\'W­ZtE4r\ê\Ô)	a¯¾ú\ê +¢‘7\Şxcùò\å³m\ïóDö\çÎc\Ãq’m\âò\î»\ï\Ê\çùeË–]\àlmm•(dË–-2\İ\Ó\Ó#}®££C&¶o\ß.R¨\0Àü|€\"\Ú-jZ‚‰N+pQ\0\0`¾\á\Ş\0\0 \0\0 \0\0 \0\0 \0\0 \0\0 \à¿ê¾²]¸p¡±±QÃ¯¸\rJ2™\ã¸œ\Æ\ÇÇ¥‡\Ô\Ô\Ô]ŒŒŒ\Ô\Õ\Õ\Åb± +¢\ÙQ/\\¸0\èŠhdjj*‘HH?	º\"\Z¹xñb•%\èŠøƒ\0\0\0h‡K<\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;±®®® \ëPFƒƒƒ¯¿şú;\ï¼cš\æÒ¥K]¯:u\ê¿ø\Åùó\ç,XÀ\×d\Í[¹;‰1/û	… Ÿø«’¿¨­··wß¾}\×]wLÿ\æ7¿‘‰;v466\Ê\Ó\á\á\á¿û»¿{ó\Í7×¬Y#\r\r\r\ßø\Æ7V¬Xaÿ¬ô³·\Şz\ë\âÅ‹\×_}kkk`Ë€2\Ë\ÑIŒ|ı¤R;	K)Ä¨\Ğ6A6v&¾«\ä\0¥§§\çÓŸş´Z\Ù·ş\Í\ßü\Íúõ\ë\Û\Û\Û\ÕKû÷\ïWıCúÍ®]»¤ğoÿöo\Õ\æ\îg¨$9:‰‘³ŸTp\'a\Ãqñ\ÜIŒ\Êmdcg\â?s\Şø\Ş÷¾wÿı÷\ËÄ…\î»\ï¾şğ‡öKG‘’“\'O\Ú\ï<zô¨š–Bù©§Ÿ~z\Æy\ÊL\ä:”÷·\Ë|¤w\Ê/’\ß^ê’ l\ìNb\æ\ë\'\å\è$¦–ı$\Ø\r\ÇÔ¯M\n\ï$fÁm\êÁŒØ™”.t€4w\ê\ë\ë\Õ\Ä\Ûo¿-\ëÖ­³_Z¹r¥<;vLpÛ²e‹ı’”\Üq\Ç²š\í@\Ø&1²t™X²dIß›÷pÿ{\ï½\'\Õ[µj•³s\Ï\î$F¾~\âo\'14\î\'Am8F¾6	¼AŸÚ¤:	²i»3	Q\'™GÊV¯^-§OŸnnnnjj²_R\'\Ó.^¼8\ã:û™MzÀSO=%LV\î\ßûOÿôOÇÿ\æ7¿iŸÙ“t–>\'ó‘—$jş\Ü\ç>\×\ÙÙ™wYB\Ô\Ã\Â\Å\î$F‘ı¤\ÄNb”¡ŸøuU;¨\r\Ç\È\Ù&\Ş6_\Ú\Äs\'1fj“`;‰Á\İe£\ç\Î$\\d¾({ö\ìù\àƒz\è!™>q\âDKKK\á?\ë\ìg¶ü\àòø…/|AºKñ¾¥¼ğ\Âmmm*ŒNù§ú§\Òo$V%\ánŞ¼Y½ù—¿ü¥¼t\ÓM7ı\áşaú\Ò\Ãdş›_y\å•ò6®e\È\ÙIŒ\"ûI)\Ä(C?q^\ÕV\ÙWµU˜›{Ô†c\äko\Îlm\"ûßŸı\ìgK—.UŸM¥b³\Åı¥tc¦6	°“…õ\ÃjŸ¡¡!™ø\èG?\Êş¤\Ú\îL\Ê\×IÊ¡òY[{÷\î•\è\ï\á‡\Î}²aÆ¸\Õ\ÕÏ”Ÿü\ä\'}}}–şö·¿5¬“r³\Í3\ï`\ç\Ï~\æ3Ÿ‘uÿ«_ı*ww\É\İ\ÃòF¶\Â;‰1S?)±“e\è\'òY§««\Ëy¿\ì\à\ì“\Æ\êC\Õo~ó\é$\ì.X°@*\ïªa°‘¯M<l89\ÚdllLi×HûÈ¾XM—\ØIŒ™\Ú$\ØNb\ä\ë\'†cb—ü\ã?ş#1Jš\ïL\Ê\ÑIl*”\é\è\èX¿~}®\ÂY©²¾eõÈªu^s‘’òø±}\ÌY8[?“y>ıôÓ²\Âd†²S\Ë]b\Ï\0K\×tv¬\å\îa¹O#[Ä˜©Ÿø\ÒIŒ2ô“\ÜWµ¥\Î2‡$ğ\r\Ç(²M\n\Ùpr´‰<u.¾,\×û\ï¿/µİ¸q£]yÏÄ˜¥M\ï$F¾~b\'›\ìŞ½[ı^ùDDt’C\èv&¥w[\á·\È®’\é(²j¿ò•¯\ÈQ<÷;\í³—v\ÉlıLz•l«!x®¨3{~\Z—G¾…pö°¼\'\áRx\'1²ú‰_\Ä(?q}P{ıõ\×\ï¹\ç»“lÚ´I*<88¨–B‡\r\Ç(¦M¼m83\äP¤’\Ï?ÿüš5k\ÔÁR:‰1K›h\ØIŒ¬6y\å•Wd÷ò\ío\ÛJ\niy+t;7œ¢n‘)\\\Å(\Òô²\Êg¼ºq\íµ\×\Ê\ÚYrô\è\Ñë®»\Î\Şg\ëgr˜ÿÖ·¾\å:%{15¡.c_ıõ\êi\îşd¯c\é2Oµ^%2•\nç»³\Ópô°¼\'á”£“ùú‰·Nb\ÔO\\Wµ\åVö{\Ô5\ßpŒ\é6)±AŒY®ô+?ù\ÉOd¶r`0J\ë$\Æ,m¢g\'1²\ÚD>şóŸ\ç”I!Â²3)Ó†Sø-2E©\Ø\0E\ZQV\êŒ}\åsŸû\Ü/¼ û uYD:Áşıû~øaõj~&=lttt·EJ÷’2\Ñ\İ\İı\çş\çÿ\Ê200 \åmmmö\"N®3{AK¿üÿø\é=ò{o¼ñÆ¼‘DV\ìI\ày.G\'1röÏDVÍœõ[öU\ímÛ¶}ó›\ßT_*%\Ë%o¸\ç{ò¶I°«MJi\Û\Ä&Ÿ¿÷½\ïI5\Ôvä¹“\äh\r;Iv›\ÈÌ¥’r8ü\å/)ó\\ºt\é§>õ)‚•Ù„egR\r§¨[dŠR±Š4\â~‹a­Wµ]\İt\ÓM²ş¤ùdwüô\ÓOÿü\ç?—•ô\Ö[o\Éa\Ş]sô³-[¶H?3¬tgÏ•7ŒŒ\È\á_\ne_o\ßO—ƒ\ëÌT\ì3\Ã:\Ë\'Q\ÂOõt¶\è8G+6§`\Ë\ÑIk3›­Ÿx\î$†µ}\ì\'¹?B\ÍvU[¢[Y^{.Y.û2³¶«M<l8¹\Û\Äö\Ê+¯\Èã­·Şš·AŒœ$G›\Ìq\'ñ\Ö&\ï½÷a{¤\\ö*Ç—§\Üq?›°\ìL|\ßpŠ½E¦( H»«µ\"+R\åª\Ğ>‘ +^>>ª-ğ®»\îr®‰¼ı\Ìù[$tµ_šQ\Ş3ÀN\ÒM¿ÿı\ïKmUw™-:\Î\Ñ\Ãf“\ãrû|–»“³÷;‰QB?\Éñj¶«Ú²’š”ËFz\Îo¼!²a\Ãµtšl8EµIN6q6Î¾}û\äG\ì\ßâ¹“\än“¹\ì$\Ş\Ú\Ä^Fù¼nL\ß0ûøã«§p	\ãÎ¤ôN\â\á™¢Tl€\ÒdQ\Ó3¼[-\Ù\åyû™“|ªî’£\Z¹\Ï\0g“\è\ØYÃ¼Ñ±«‡e›1§\0J\ŞNb\Ì\ÒOü\í$F	ıd¶N’\ã~½ı\èG,—\ÔK\æ0::ú\ä“Oª&Qd›²\ár£|lhh°OŸ%t£˜6)k\'1<µÉ‰\'\äñ\Ïş\ì\Ï\ÔSû{Ù¥ğ}n\ÙBº3)¥“\ä½E¦t xVH?³}şóŸ·Oˆ\Í(÷`\éC«W¯¶]oo¯*r\Ï0›³‡e\Ë\Î)@\éü\í$†\ßı$÷ız²·’™;?NmØ°A\nKLõš\Ë6ñ°\á\änexxø‡?ü¡«qJQx›\Ì}\'1òµ‰::›\ÂÇ›aï†“÷™\Üõ,JIrŸjSrœşÕ¯~%\İH…œ\ê¬Z\Şse¹{XQ\'17\n\é$†¯ı$÷ız\r\r\rƒƒƒÎ’÷\ß¿\Zú¨\Ä6ñ°\á\än\å\í·ß–­\é¶\Ûn+t1ü3÷\Ä\È\×&K—.}ó\Í7%h³÷\êÓ\ßrbiµ\á\ä½E¦t\Ó4}™¼Q_7nX·‰8#\ÙHp*!ˆ«‡\Ùg\Ø\äCğ\Ã?ü\Å/~\Ñ>¹÷°…o/»¢ú‰ó‹•²¯j÷ôô¼ğ\Â\ê[\Ú\ë´Á×¿şucúVƒ°(v\Ã\É\İ&\Ê×¾ö5y\ï·\Zú\Û&\Ò1¾úÕ¯\ÊGvû\é®]»\ä=\Ş\Ñ=•cÃ±I\É{‹LQP\Â\'wS‡‰`\Ô\É=‰]füNbT0{\ä\×Umµ¯q†`XWµy‘»M«Y¾ô¥/\åHr®<y\ÛDİƒ/‡%\ÙÏ¨S³;w\îô\å\Ô=\Â\"o\'q’c\Ñú‚\0¥\ÙÃ“b6\Î0÷³Ÿı,%@ù\ío\Ë`x.*õÔ Ÿ \0½½½ştP\0\0€v¢AW\0\0\0À\0\0\0h‡\0\0\0h‡\0\0\0h‡\0€&N=\Ó\ÖöÌ© k@(\0\0@;(\0\0@;(\0uğ‘µ\Ê#¯Lz¦m\ít\áÁ\Ìsk\Âp?P±P\0G¢‡=ø\Ü\á´\ÛN<ñ®*]±mŸUrø¹=õ\Ì)yş\åM/şT%§^y\Ùxğş\ÖÀœ @œ“\'ŒwmS£\0m¼ÿÁ¦\Ë3§U\î}\â\İwOœL¿v[&BI\Ç\'w\ŞZ\É\ÃPP\0\æÔ‰c3”JtòÔµ\ê¬\Ê\ã›2e½“E\â“U_\ŞF|\Ì(\0³\â\Ö;\'öfn(9¸W]\âIG-«®µ‚ƒ?}\Ññ\Öc?}F\â“Û¸¼\Ìñ +\0`[±m×ƒm÷®]›\Şô\àƒ7³Ê¾¼i\íCkÓ¡É¦M›.½U\"”{_¾ó¹}UÀ\Üb4c\0\ápê™¶Æ®}\\\à\æ.ñ\0…ƒ{Ÿ\àöX`!@ ;\ë{Q2\ç\ì	0p‰\0\0h‡3(\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;(\0\0@;|“,t‘H$’\ÉdĞµ\0\æµX,3\Ê=´@€-ŒŒŒÈ£„)A\×˜§$4©­­]°`<]€\0\Z˜œœ<{öl4\Z­®®X‚®0\ï˜\ÙS©\ÔÒ¥KecºF˜\ïP<‰NdŸXSS#1J\Ğu\æ5\Ù\'&&dK”%\èº`¾\ãZ#‚755U[[Ë•o pšHŒ2>>tE\0h@vˆ²[\ä\Ê £]€4c\0\0 s\'€V\Ø$¡.ñ@GS¯>?µo—\ì#£‘H4b¨¥J\ïI\ß\Ôm¦ÿ\ÈDÒ”¿f2e\Äo¹§ş/¾la½“ü\É=\ÒDé†ŠZ8\ÒÍ•n©K\re\Z)\ÓH¤Œd\Ê4¯ød\Í]/]\ë`ü\Õş¿Jw¡¨Õ\"™†\Êô-súVJ¦¿{÷w®10¿q\0\0h‡\0Á\ã»O\0}°=B(¨P¯=vK\Æc¯\å|W®—\ç‰\Ó\Ï\Ş‹\íşgO]m\ÚPÒ­²_•.¨m}P\é¸•Höñ;Œ]¯¾z³5ı\ìk\Æ\Í7]%m\ÉÁ\Ôn+\ë\écrp\\l´TDCİ¼óU:P*T¢\Ó\'İ½1sˆX¾u+‡\ÛY½öXú »ó\Ò\áô\æ;¬¾h(`®q‰Áóÿ‚÷\Í\ï~iG\Öyr\Ç	ú®\ë\ä~µB½vğ%;’s½ò\Ø-=›n«³\Çy!\âÒ´u\Élú\êZ%]¨˜µ¡flÇ•CûR\ãc¯^öC\î®6\ãû¿\ß~ùY\ç/¹´†\n]E…­!\îAPP‘n\Şùê³·\Ø\ê8\Èz\ëÛŸ}Õ²\Ëp…/¹_­X§Oız\Õ5™óKö\Î>f¾tÀ\èzõÕ½[¢\Z\ç¥7ªw~ü;]•ÒŒ¹jö{öş¿~@µ\ÜÆ“\ß96]Ø•n\ØLk¦\ç2\ãûò\Ì&mz\r-/ªÿV\ä\ZB%\"@A¥Z¾u¯\ÚY«\É\é“\Æ]\Ó{nş\ËŒ\ÎOª¹_­X\ËW|ü\Ø\É\ÌJµ×³¬²_½û~\Õ\"\Å5\Îİ»2\×A*©s7Ô¬œ-\'Í±*Sx\ì\Øw¶ªg\ÇKÆ¯O\Íò>\çlM¯‹\Ë_¶KgXE³®5D„]  x\å<Ÿ|ó\Î]w¿tp\Ş\\²)\ÖòkV\Ñ:…ğ\ÔP§Oız\æ\î\Şõªm\ï\Ö\Ùß—\á8}3G¸\Ä  ½ö˜}ú}z\ç¿ü\Z\ã\Òù\ì×¾û\ãö[?û\ÕÊµ|k\×¿\ÎwAk\Æ\ÆIŸS\È|ú>ı\ê\ÇE	û8^Q\Í8kC\Í\Ú\é\×n¹\İø\Îw/5‡z5\ë\ì½lF3¿\ï’</3®¢\å³VÍµ†¸…º\"‹•\èæ¿¼\æş[n\ÙaM¯z\àÙ½\é3\Ú[÷\î:y\Ë\Ö[¾c\ÊG\Ø\Ër{–\ç|µ’É’?k\Ü?½äª¹²\ß2C\ã\Üü—\ì\İj®ºûn\ÇE‰»ƒ™¦¯¬fœ­¡fkõ3]ÈXQ\Ú\İ<°\ê×™9š3=›­3¾/\ÏlÜ•+|U\ê\ZBÅ‰¤\ê\0õÁ\Ô\Õ\Õ\Å\ã—\Âe\Æ\â)ncñ¼ö\Ø-7:“q5R!cñ”\Ø\Äüx\"‘knnöø+\0Ÿp‰Á\ã‚7P˜\Ó\Ï\î-1\ÜOl’\Ğ—x\0@o\é/±}IMN_±*\n‚\Ç\àdƒ\ïx/i\ÖW}ú\Ş\Ú\Ö\Û#4Á=(Ş™3gjkkc±X\Ğ`$“\ÉñññeË–]\ÌwÜƒ\0\0´C€\0\0´C€‚\àE£Q.5šQ6É k @UUU\Éd’œl†²1\Ê&tE\0h ¾¾^v‹SSS©T*\èº\0ó—l€²\Ê\Æ(›d\Ğu\È\âd·xñ\â\ÅD\"AŒ%\Z\Æ\ãq‰N8ƒ @\éoO¥LK\Ğu\æ)õ\r(¦ğ=(\Ğ\n\0\0\Ğ÷ \0\0\0\í \0\0\0\í \0\0\0\íü>\âkG\r\nendstream\nendobj\n8 0 obj\n<<\n/Type /Font\n/Subtype /Type1\n/BaseFont /Times-Roman\n/Encoding /WinAnsiEncoding\n>>\nendobj\n9 0 obj\n<<\n/Type /Font\n/Subtype /Type1\n/BaseFont /Times-Bold\n/Encoding /WinAnsiEncoding\n>>\nendobj\n10 0 obj\n<<\n/BitsPerComponent 8\n/Predictor 15\n/Columns 736\n/Colors 3\n>>\nendobj\n11 0 obj\n<<\n/Length 282\n/Type /XObject\n/Subtype /Image\n/Filter /FlateDecode\n/BitsPerComponent 8\n/Width 736\n/Height 364\n/ColorSpace /DeviceGray\n>>\nstream\r\nxœ\íÁ1\0\0\0Â ş©g\n? \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\ào!¦\r\nendstream\nendobj\nxref\n0 12\n0000000000 65535 f\r\n0000000015 00000 n\r\n0000000078 00000 n\r\n0000000135 00000 n\r\n0000000254 00000 n\r\n0000000474 00000 n\r\n0000000533 00000 n\r\n0000000574 00000 n\r\n0000015377 00000 n\r\n0000015476 00000 n\r\n0000015574 00000 n\r\n0000015653 00000 n\r\ntrailer\n<<\n/Root 1 0 R\n/ID [<2165C2CDA476927A5EF340C94D72BE5E> <2165C2CDA476927A5EF340C94D72BE5E>]\n/Size 12\n>>\nstartxref\n16108\n%%EOF\n');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitor`
--

DROP TABLE IF EXISTS `visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitor` (
  `ID` int NOT NULL,
  `GroupGuide` tinyint DEFAULT NULL,
  `IsLogged` tinyint DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor`
--

LOCK TABLES `visitor` WRITE;
/*!40000 ALTER TABLE `visitor` DISABLE KEYS */;
INSERT INTO `visitor` VALUES (111111111,1,0),(123123120,0,0),(123123121,0,0),(123123122,0,1),(123123123,0,0),(123123125,0,0),(123123126,0,1),(123123127,0,0),(123654987,0,0),(201234567,1,0),(201234569,1,0),(202345671,1,0),(203456782,1,0),(204567894,0,0),(205678906,0,0),(206789017,1,0),(207890120,1,0),(208901232,1,0),(209012344,1,0),(210123456,1,0),(211234567,1,0),(211234579,1,0),(212345691,1,0),(213456703,1,0),(214567814,1,0),(214567926,0,0),(222222222,1,0),(222222223,0,0),(301234568,1,0),(302345670,1,0),(303456783,1,0),(304567895,1,0),(305678918,1,0),(306789019,1,0),(307890121,1,0),(308901233,1,0),(309012345,1,0),(310123468,1,0),(311234580,1,0),(312345692,0,0),(313456815,1,0),(333333333,1,0),(555555555,0,0),(666666666,0,0),(777777777,0,0),(888888888,0,0);
/*!40000 ALTER TABLE `visitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waitinglist`
--

DROP TABLE IF EXISTS `waitinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `waitinglist` (
  `OrderNumber` int NOT NULL,
  `ParkName` varchar(45) DEFAULT NULL,
  `VisitDate` date DEFAULT NULL,
  `VisitStartTime` time DEFAULT NULL,
  `VisitEndTime` time DEFAULT NULL,
  `NumberOfVisitors` int DEFAULT NULL,
  `VisitType` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `VisitorID` varchar(45) DEFAULT NULL,
  `VisitDuration` int DEFAULT NULL,
  `Visit_Time_And_Date` varchar(45) DEFAULT NULL,
  `Status` varchar(45) DEFAULT NULL,
  `Payment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`OrderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waitinglist`
--

LOCK TABLES `waitinglist` WRITE;
/*!40000 ALTER TABLE `waitinglist` DISABLE KEYS */;
/*!40000 ALTER TABLE `waitinglist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-03 12:27:10
