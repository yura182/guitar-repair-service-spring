-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: repair_service
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

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
-- Dumping data for table `instruments`
--
USE `repair_service`;

LOCK TABLES `instruments` WRITE;
/*!40000 ALTER TABLE `instruments` DISABLE KEYS */;
INSERT INTO `instruments` VALUES (29,'Gibson','Les Paul',1990),(30,'Trembita','150br',1995),(31,'Yamaha','234',2018),(32,'Cort','Frank Gambale',2008),(33,'Epiphone','SG',1980),(34,'Ibanez','JS2480',2019),(35,'Tagima','T126',1955),(36,'Fender','Stratocaster',1982),(37,'Gibson','Les Paul2',1982),(38,'Yamaha','Y768',2015),(39,'Cort','P256',1999),(40,'Trembita','150br',1998),(44,'Gibson','G786',1990),(45,'Trembita','150br',1995),(46,'Trembita','150br',1995),(47,'Epiphone','SG SPECIAL',2017);
/*!40000 ALTER TABLE `instruments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (11,NULL,2,29,'2020-02-03 14:56:13','Restring, cleaning',78,'ACCEPTED',NULL),(12,4,3,30,'2020-02-03 14:56:28','Cleaning',38,'COMPLETED',NULL),(13,20,5,31,'2020-02-03 14:57:21','Finishing',99,'COMPLETED',NULL),(14,4,2,32,'2020-02-03 14:58:36','Restring',43,'PROCESSING',NULL),(15,NULL,2,33,'2020-02-03 14:59:19','Rebuild',NULL,'NEW',NULL),(16,NULL,2,34,'2020-02-03 15:00:14','Setup',NULL,'NEW',NULL),(17,NULL,2,35,'2020-02-03 15:01:39','Restring, cleaning',NULL,'REJECTED','Too old guitar'),(18,NULL,19,36,'2020-02-03 15:02:42','Cleaning',NULL,'NEW',NULL),(19,NULL,16,37,'2020-02-03 15:08:40','Repair',NULL,'NEW',NULL),(20,20,17,38,'2020-02-03 15:08:55','Restring',52,'COMPLETED',NULL),(21,4,13,39,'2020-02-03 15:09:25','Rebuild',137,'COMPLETED',NULL),(22,20,2,40,'2020-02-03 15:10:00','Repair',150,'PROCESSING',NULL),(26,4,23,44,'2020-02-04 23:13:28','Restring, cleaning',65,'COMPLETED',NULL),(27,4,2,45,'2020-02-06 23:17:46','Cleaning',1222,'COMPLETED',NULL),(28,20,24,47,'2020-02-14 15:05:06','Cleaning',53,'COMPLETED',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (17,3,12,'I am so satisfied on how my guitar plays','2020-02-03 15:34:05'),(18,5,13,'What a great experience I have had with this service on two of my electric guitars','2020-02-03 15:35:07'),(19,17,20,'I found my new go-to repair shop! This place is super excellent!','2020-02-03 15:36:23'),(20,13,21,'Absolutely recommend this place for all your guitar needs!','2020-02-03 15:40:16'),(21,23,26,'Excellent service! Thank you','2020-02-04 23:16:55'),(25,24,28,'There\'s nobody who treats my precious guitars better than You!','2020-02-14 15:21:22');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Yura','Petrashenko','0664003626','yura@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(3,'Bob','Tomson','0501836598','bob@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(4,'Tom','Kay','0501212326','tom@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','MASTER'),(5,'Alfred','Dan','0503233672','dan@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(12,'Don','Brown','0664003626','brown@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(13,'Scott','Terry','0664003626','sterry@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(14,'Anton','Lapenko','0997778090','otec@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','ADMIN'),(15,'Lindsay','Brown','0986326323','lindsay@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(16,'Дмитро','Власенко','0501918275','dvlas@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(17,'Neil','Johnston','0996224040','neil@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(18,'Maya','Moore','0767653232','maya@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(19,'Lisa','Leslie','0987639872','lisa@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(20,'Philip','Pullman','0995007259','master@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','MASTER'),(23,'Sony','Tompson','0987775050','sony@gmail.com','$2a$10$TgzAoiC2qJbEdSoH93DN/.Zwr1HMzX2TWVQbnkRLdnz5Yu6QebNKy','CLIENT'),(24,'Tommy','Emmanuel','0975003678','tommy@gmail.com','$2a$10$JBDoGE/I3hsAPnFjNLyJMORomiUBau.tNr7YkEqKjSiaufmhBQuZu','CLIENT');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-17 19:21:34
