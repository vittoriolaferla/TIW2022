CREATE DATABASE  IF NOT EXISTS `db_gestione_immagini` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_gestione_immagini`;
-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: db_gestione_spese
-- ------------------------------------------------------
-- Server version	8.0.28

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

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `comment`(
	`id` int NOT NULL AUTO_INCREMENT,
	`idImage`   INT NOT NULL,
    `nameUser`   varchar(45) NOT NULL,
`text` varchar(180) NOT NULL,
PRIMARY KEY(`id`),
 FOREIGN KEY (`idImage`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mission`
--
LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,'vittorio','Mi piace proprio questa immagine!'), (2,1,'Francesco Vaniglia','Complimenti per la foto!'),(3,1,'Vittorio La Ferla','Ti ringrazio!'),(4,2,'vittorio','Non mi aggrada molto questa foto'),(5,3,'Alessandro Barbero','Si vede che hai gusto!'),(6,4,'Elena Nuttini','Volevo commentare questa foto rempiondoti di complimenti, ma non so cosa dire!');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `user`
--
--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `album`(
	`id` int NOT NULL AUTO_INCREMENT,
	`idUser`   INT NOT NULL,
`title` varchar(45) NOT NULL,
`nameCreator` varchar(45) NOT NULL,
`creationDate` datetime NOT NULL,
PRIMARY KEY(`id`),
 FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mission`
--
LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,1,'Album','Vittorio' ,'2020-07-19'), (2,2,'Album','Luca' ,'2020-07-19'),(3,1,'Il Mio Album','Vittorio La Ferla' ,'2020-05-19');
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `image`(
	`id` int NOT NULL AUTO_INCREMENT,
    `idAlbum`  INT ,
    `idUser` INT NOT NULL, 
`title` varchar(45) NOT NULL,
`textDescription` varchar(100) NOT NULL,
`filePath` VARCHAR(150) NOT NULL,
PRIMARY KEY(`id`),

FOREIGN KEY (`idAlbum`) REFERENCES `album` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(`idUser`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (1,1,1,'ImmagineVittorio' ,'Immagine dii album di vittorio','image2.jpeg'), (2,2,2,'ImmagineLuca' ,'Immagine di alum di luca','image2.jpeg'),(3,null,1,'ImmagineVittorio1' ,'Immagine dell album di vittorio','image2.jpeg') ,(4,1,1,'ImmagineVittorio2' ,'Imm','image2.jpeg'),(5,1,1,'ImmagineVittorio1' ,'Immagine dell album di vittorio','image1.jpeg'),(6,1,1,'ImmagineVittorio2' ,'Imm','image2.jpeg'),(7,1,1,'ImmagineVi1' ,'Immagine dell  di vittorio','image1.jpeg'),(8,1,1,'Immaginetorio2' ,'Imm','image2.jpeg'),(9,1,1,'ImmagineVittorio1' ,'Immagine dell album di vittorio','image1.jpeg'),(10,1,1,'ImmagineVittorio2' ,'Imm','image2.jpeg'),(11,1,1,'ImmagineVittorio1' ,'Immagine dell album di vittorio','image3.jpeg'),(12,1,1,'ImmagineVittorio2' ,'Imm','image2.jpeg'),(13,1,1,'ImmagineVi1' ,'Immagine dell  di vittorio','image3.jpeg'),(14,1,1,'Immaginetorio2' ,'Imm','image3.jpeg'),(15,1,1,'ImmagineVittorio1' ,'Immagine dell album di vittorio','image1.jpeg'),(16,null,1,'Imm4netorio2' ,'Imm','image3.jpeg'),(17,null,1,'ImineVittorio1' ,'Questa e una bella immagine','image3.jpeg'),(18,null,1,'ImmagineVittorio1' ,'Immagine dell album di vittorio','image4.jpeg') ,(19,null,1,'Immagina' ,'Sono dei fiori','image4.jpeg'),(20,1,1,'IMaggine con dei fiori' ,'Immagine dell album di vittorio','image4.jpeg'),(21,null,1,'Io Con Fiori' ,'Immagine molto interessante','image4.jpeg'),(22,null,1,'Baci e abbracci' ,'Immagine perfetta di una coppia','image10.jpeg'),(23,3,1,'Obbiettivo' ,'Questo e il mio nuovo obbiettivo','image8.jpeg'),(24,3,1,'Farfalla' ,'Col mio nuovo obbiettivo ho fortografato questa farfalla','image9.jpeg'),(25,3,1,'Palla di fuoco' ,'Sembra una palla di fuoco','image7.jpeg'),(26,3,1,'Paesaggio','Questo e quello che si vede da casa mia','image6.jpeg'),(27,3,1,'Cuori','Foto di cuori','image5.jpeg'),(28,3,1,'Fiori','Ho trovato questa immagine su internet, mi piaceva proprio','image4.jpeg');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Dumping data for table `mission`
--
DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  UNIQUE(`username`),
  PRIMARY KEY (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user1','user1pass','Piero','Fraternali'),(2,'user2','user2pass','Federico','Milani'),(3,'user3','user3pass','Rocio','Torres');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db_gestione_spese'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-22 14:19:19
