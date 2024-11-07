-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: salon
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Masters`
--

DROP TABLE IF EXISTS `Masters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Masters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Masters`
--

LOCK TABLES `Masters` WRITE;
/*!40000 ALTER TABLE `Masters` DISABLE KEYS */;
INSERT INTO `Masters` VALUES (1,'Алина Пономарёва'),(2,'Антонина Капустина'),(3,'Анастасия Золотова');
/*!40000 ALTER TABLE `Masters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` int NOT NULL DEFAULT '0',
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `allergie` varchar(200) DEFAULT NULL,
  `service` int DEFAULT '0',
  `resource` int DEFAULT '0',
  `schedule` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_пользователя` (`user`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (12,0,'2023-11-22 23:17:26','нет',1,0,8),(13,0,'2023-11-22 23:40:42','нет',1,0,9),(14,0,'2023-11-23 00:51:52','нет',1,0,10),(16,0,'2023-11-23 11:58:50','нет',1,0,12),(19,11,'2023-11-23 19:01:07','нет',1,0,17),(20,11,'2023-11-23 21:04:06','нет',1,0,20),(22,6,'2023-11-24 13:48:48','нет',3,0,19),(23,5,'2023-11-24 22:50:00','нет',3,0,15),(24,1,'2023-11-24 23:03:46','нет',2,0,21),(25,1,'2023-11-24 23:04:42','нет',2,0,22),(26,11,'2023-11-29 15:19:03','нет',1,0,24),(27,5,'2023-11-29 15:20:11','нет',3,0,26),(28,5,'2023-12-04 13:25:43','нет',3,0,27),(29,14,'2024-10-27 15:11:23','нет',3,0,25);
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_order_created` AFTER INSERT ON `orders` FOR EACH ROW BEGIN
  SET @scheduleId := NEW.schedule;
  UPDATE `Schedule` SET `booked` = true WHERE `Schedule`.`id` = @scheduleId;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Resources`
--

DROP TABLE IF EXISTS `Resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Resources` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Resources`
--

LOCK TABLES `Resources` WRITE;
/*!40000 ALTER TABLE `Resources` DISABLE KEYS */;
INSERT INTO `Resources` VALUES (1,'ножницы','простерилизованы'),(2,'пилка','одноразовая'),(3,'аппарат для снятия (фрезы)','простерилизованы');
/*!40000 ALTER TABLE `Resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resources_services`
--

DROP TABLE IF EXISTS `resources_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resources_services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `resources_id` int DEFAULT NULL,
  `services_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`resources_id`),
  KEY `id_idx1` (`services_id`),
  CONSTRAINT `id_r` FOREIGN KEY (`resources_id`) REFERENCES `Resources` (`id`),
  CONSTRAINT `id_s` FOREIGN KEY (`services_id`) REFERENCES `Services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resources_services`
--

LOCK TABLES `resources_services` WRITE;
/*!40000 ALTER TABLE `resources_services` DISABLE KEYS */;
INSERT INTO `resources_services` VALUES (1,1,1),(2,2,1),(3,1,2),(4,2,2),(5,3,2),(6,1,3),(7,2,3),(8,3,3);
/*!40000 ALTER TABLE `resources_services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Schedule`
--

DROP TABLE IF EXISTS `Schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `master` int NOT NULL,
  `time` datetime NOT NULL,
  `booked` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_мастера` (`master`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Schedule`
--

LOCK TABLES `Schedule` WRITE;
/*!40000 ALTER TABLE `Schedule` DISABLE KEYS */;
INSERT INTO `Schedule` VALUES (1,2,'2023-12-12 10:00:00',1),(2,1,'2023-12-12 12:00:00',1),(3,2,'2023-12-11 18:00:00',1),(4,3,'2023-12-12 19:00:00',1),(5,3,'2023-12-12 20:00:00',1),(6,1,'2023-12-12 22:00:00',1),(7,2,'2023-12-13 12:00:00',1),(8,1,'2023-12-13 14:00:00',1),(9,1,'2023-12-14 12:00:00',1),(10,1,'2023-12-14 13:00:00',1),(11,1,'2023-12-14 14:00:00',1),(12,1,'2023-12-14 15:00:00',1),(13,1,'2023-12-14 16:00:00',1),(14,1,'2023-12-15 12:00:00',1),(15,1,'2023-12-15 13:00:00',1),(16,1,'2023-12-15 14:00:00',1),(17,1,'2023-12-15 16:00:00',1),(18,1,'2023-12-15 18:00:00',1),(19,1,'2023-12-16 12:00:00',1),(20,1,'2023-12-16 15:00:00',1),(21,2,'2023-12-16 18:00:00',1),(22,2,'2023-12-17 20:00:00',1),(23,2,'2023-12-17 23:00:00',1),(24,3,'2023-12-18 12:00:00',1),(25,3,'2023-12-18 16:00:00',1),(26,3,'2023-12-18 18:00:00',1),(27,2,'2023-12-19 10:00:00',1);
/*!40000 ALTER TABLE `Schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Services`
--

DROP TABLE IF EXISTS `Services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Services`
--

LOCK TABLES `Services` WRITE;
/*!40000 ALTER TABLE `Services` DISABLE KEYS */;
INSERT INTO `Services` VALUES (1,'маникюр','обработка кутикулы и пластины',1000.00),(2,'маникюр с покрытием','обработка кутикулы и пластины и покрытие гель-лаком',1500.00),(3,'педикюр с покрытием','обработка кутикулы и пластины и покрытие гель-лаком',1500.00);
/*!40000 ALTER TABLE `Services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `role` enum('Клиент','Сотрудник') NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (1,'Алина','Сотрудник','123','111'),(2,'Антонина','Сотрудник','234','222'),(3,'Анастасия','Сотрудник','345','333'),(4,'Катя','Клиент','456','444'),(5,'Карина','Клиент','567','555'),(6,'Ксюша','Клиент','678','666'),(7,'Арина','Клиент','000','000'),(10,'Ира','Клиент','888','888'),(11,'Елена Вячеславовна','Клиент','elka','qwert'),(12,'опов','Клиент','11','1'),(13,'дима','Клиент','дима','111'),(14,'пп','Клиент','пп','пп');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-06 10:20:35
