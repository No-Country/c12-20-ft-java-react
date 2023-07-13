CREATE DATABASE  IF NOT EXISTS `gym` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gym`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: gym
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(45) NOT NULL,
                            `create_day` date NOT NULL DEFAULT (now()),
                            `description` varchar(255) NOT NULL DEFAULT 'default',
                            `img` varchar(255) NOT NULL DEFAULT 'default',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forgot_password`
--

DROP TABLE IF EXISTS `forgot_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forgot_password` (
                                   `id_user` int NOT NULL,
                                   `code` varchar(200) NOT NULL,
                                   `expiration_date` datetime NOT NULL,
                                   `enable` tinyint(1) NOT NULL,
                                   PRIMARY KEY (`id_user`),
                                   UNIQUE KEY `code_UNIQUE` (`code`),
                                   CONSTRAINT `user_forgot_password_fk` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forgot_password`
--

LOCK TABLES `forgot_password` WRITE;
/*!40000 ALTER TABLE `forgot_password` DISABLE KEYS */;
/*!40000 ALTER TABLE `forgot_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `subscription_id` int NOT NULL,
                           `method` varchar(45) NOT NULL,
                           `payment_at` date NOT NULL,
                           `expired_at` date NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `subscription_payment_fk_idx` (`subscription_id`),
                           CONSTRAINT `subscription_payment_fk` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(45) NOT NULL,
                        `max_capacity` int NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `training_session_id` int NOT NULL,
                                `customer_id` int NOT NULL,
                                `state` enum('RESERVED','ACTIVE','INACTIVE','CANCELED') NOT NULL,
                                `create_date` date NOT NULL DEFAULT (now()),
                                PRIMARY KEY (`id`),
                                KEY `user_subscription_fk_idx` (`customer_id`),
                                KEY `training_session_subscription_fk_idx` (`training_session_id`),
                                CONSTRAINT `training_session_subscription_fk` FOREIGN KEY (`training_session_id`) REFERENCES `training_session` (`id`),
                                CONSTRAINT `user_subscription_fk` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_session`
--

DROP TABLE IF EXISTS `training_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `training_session` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `activity_id` int NOT NULL,
                                    `room_id` int NOT NULL,
                                    `capacity` int NOT NULL,
                                    `time_start` time NOT NULL,
                                    `time_end` time NOT NULL,
                                    `monday` tinyint(1) NOT NULL DEFAULT '0',
                                    `tuesday` tinyint(1) NOT NULL DEFAULT '0',
                                    `wednesday` tinyint(1) NOT NULL DEFAULT '0',
                                    `thursday` tinyint(1) NOT NULL DEFAULT '0',
                                    `friday` tinyint(1) NOT NULL DEFAULT '0',
                                    `saturday` tinyint(1) NOT NULL DEFAULT '0',
                                    `sunday` tinyint(1) NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`),
                                    KEY `activity_training_session_fk_idx` (`activity_id`),
                                    KEY `room_training_session_fk_idx` (`room_id`),
                                    CONSTRAINT `activity_training_session_fk` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
                                    CONSTRAINT `room_training_session_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
                                    CONSTRAINT `check_time_range` CHECK ((`time_start` <= `time_end`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_session`
--

LOCK TABLES `training_session` WRITE;
/*!40000 ALTER TABLE `training_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_session` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `before_insert_check_capacity` BEFORE INSERT ON `training_session` FOR EACH ROW BEGIN
  DECLARE room_max_capacity INT;
  SELECT max_capacity INTO room_max_capacity FROM room WHERE id = NEW.room_id;
  IF NEW.capacity > room_max_capacity THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Capacity exceeds max capacity for the room.';
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `check_capacity_overflow` BEFORE INSERT ON `training_session` FOR EACH ROW BEGIN
  DECLARE total_capacity INT;

  SELECT SUM(capacity) INTO total_capacity
  FROM training_session
  WHERE room_id = NEW.room_id
    AND time_start = NEW.time_start
    AND time_end = NEW.time_end
    AND (
    (monday = 1 AND  monday = NEW.monday) OR
    (tuesday = 1 AND  tuesday = NEW.tuesday) OR
    (wednesday = 1 AND  wednesday = NEW.wednesday) OR
    (thursday = 1 AND  thursday = NEW.thursday) OR
    (friday = 1 AND  friday = NEW.friday) OR
    (saturday = 1 AND  monday = NEW.saturday) OR
    (sunday = 1 AND  sunday = NEW.sunday)
    );

  IF total_capacity + NEW.capacity > (
    SELECT max_capacity FROM room WHERE id = NEW.room_id
  ) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Total capacity exceeds max capacity for the room.';
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `check_consistency` BEFORE INSERT ON `training_session` FOR EACH ROW BEGIN
  DECLARE conflicting_rows INT;

  SELECT COUNT(*) INTO conflicting_rows
  FROM training_session
  WHERE room_id = NEW.room_id
    AND (
        (time_start >= NEW.time_end AND time_end >= NEW.time_end) OR
        (time_start <= NEW.time_start AND time_end <= NEW.time_start)
    )
    AND (
    (monday = 1 AND  monday = NEW.monday) OR
    (tuesday = 1 AND  tuesday = NEW.tuesday) OR
    (wednesday = 1 AND  wednesday = NEW.wednesday) OR
    (thursday = 1 AND  thursday = NEW.thursday) OR
    (friday = 1 AND  friday = NEW.friday) OR
    (saturday = 1 AND  monday = NEW.saturday) OR
    (sunday = 1 AND  sunday = NEW.sunday)
    );

  IF conflicting_rows > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The record you are trying to add is not consistent.';
   END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `before_update_training_session` BEFORE UPDATE ON `training_session` FOR EACH ROW BEGIN
  DECLARE room_max_capacity INT;
  SELECT max_capacity INTO room_max_capacity FROM room WHERE id = NEW.room_id;
  IF NEW.capacity > room_max_capacity THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Capacity exceeds max capacity for the room.';
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(45) NOT NULL,
                        `lastname` varchar(45) NOT NULL,
                        `email` varchar(128) NOT NULL,
                        `password` varchar(128) NOT NULL,
                        `role` enum('CUSTOMER','EMPLOYEE','ADMIN') NOT NULL,
                        `create_at` date NOT NULL DEFAULT (now()),
                        `deleted` tinyint(1) NOT NULL DEFAULT '0',
                        `picture` varchar(200) NOT NULL DEFAULT 'default',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'gym'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-07 19:15:43
