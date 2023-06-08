-- MariaDB dump 10.19  Distrib 10.6.13-MariaDB, for debian-linux-gnu (aarch64)
--
-- Host: localhost    Database: trinted
-- ------------------------------------------------------
-- Server version	10.6.13-MariaDB-1:10.6.13+maria~ubu2004

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articolo`
--

DROP TABLE IF EXISTS `articolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articolo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) DEFAULT NULL,
  `condizioni` varchar(255) DEFAULT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  `immagini` varbinary(255) DEFAULT NULL,
  `prezzo` double DEFAULT NULL,
  `titolo` varchar(255) DEFAULT NULL,
  `utente` bigint(20) DEFAULT NULL,
  `acquistabile` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtdv80up34qmvqrmju8se9xkak` (`utente`),
  CONSTRAINT `FKtdv80up34qmvqrmju8se9xkak` FOREIGN KEY (`utente`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articolo`
--

LOCK TABLES `articolo` WRITE;
/*!40000 ALTER TABLE `articolo` DISABLE KEYS */;
/*!40000 ALTER TABLE `articolo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_acquisto` date DEFAULT NULL,
  `prezzo_finale` bigint(20) DEFAULT NULL,
  `acquirente` bigint(20) DEFAULT NULL,
  `articolo` bigint(20) DEFAULT NULL,
  `recensione_ordine` bigint(20) DEFAULT NULL,
  `venditore` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgf61gwevo6shdukma69pqvbuw` (`acquirente`),
  KEY `FKc8f6aq6i9pae5d2ly2x3ddxjx` (`articolo`),
  KEY `FKpoiem73dak3smj0sohxr7d50b` (`recensione_ordine`),
  KEY `FK6ryppeilec0he5cpprbpe4m4p` (`venditore`),
  CONSTRAINT `FK6ryppeilec0he5cpprbpe4m4p` FOREIGN KEY (`venditore`) REFERENCES `utente` (`id`),
  CONSTRAINT `FKc8f6aq6i9pae5d2ly2x3ddxjx` FOREIGN KEY (`articolo`) REFERENCES `articolo` (`id`),
  CONSTRAINT `FKgf61gwevo6shdukma69pqvbuw` FOREIGN KEY (`acquirente`) REFERENCES `utente` (`id`),
  CONSTRAINT `FKpoiem73dak3smj0sohxr7d50b` FOREIGN KEY (`recensione_ordine`) REFERENCES `recensione` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preferiti_utente`
--

DROP TABLE IF EXISTS `preferiti_utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preferiti_utente` (
  `utente_id` bigint(20) NOT NULL,
  `articolo_id` bigint(20) NOT NULL,
  PRIMARY KEY (`utente_id`,`articolo_id`),
  KEY `FKbdt0knq3h517ag3qngd6bgwi0` (`articolo_id`),
  CONSTRAINT `FKbdt0knq3h517ag3qngd6bgwi0` FOREIGN KEY (`articolo_id`) REFERENCES `articolo` (`id`),
  CONSTRAINT `FKfgm65uhmel23hny42gv5821aw` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preferiti_utente`
--

LOCK TABLES `preferiti_utente` WRITE;
/*!40000 ALTER TABLE `preferiti_utente` DISABLE KEYS */;
/*!40000 ALTER TABLE `preferiti_utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recensione`
--

DROP TABLE IF EXISTS `recensione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recensione` (
  `id` bigint(20) NOT NULL,
  `commento` varchar(255) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `autore` bigint(20) DEFAULT NULL,
  `destinatario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKca461k82kbwirr4utlgm9tjou` (`autore`),
  KEY `FK1eywo529xwkfowuhyeikj4lhj` (`destinatario`),
  CONSTRAINT `FK1eywo529xwkfowuhyeikj4lhj` FOREIGN KEY (`destinatario`) REFERENCES `utente` (`id`),
  CONSTRAINT `FKca461k82kbwirr4utlgm9tjou` FOREIGN KEY (`autore`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recensione`
--

LOCK TABLES `recensione` WRITE;
/*!40000 ALTER TABLE `recensione` DISABLE KEYS */;
/*!40000 ALTER TABLE `recensione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cognome` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `immagine_profilo` tinytext DEFAULT NULL,
  `citta_utente` varchar(255) DEFAULT NULL,
  `numero_civico` int(11) DEFAULT NULL,
  `via_utente` varchar(255) DEFAULT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `rating_genrale` float DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2vq82crxh3p7upassu0k1kmte` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (8,NULL,'mascigaetano@gmail.com','$2a$12$4reyfaKWcumIF07cDv4Xh.n5YQWXpq3ceD/b6hOYn/ijXFW6CnmbW','thanos',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,NULL,'pierluigitrc@gmail.com','$2a$12$hEarm1eOsoorzeUtG6joBOX21eayVkOYmV9PhgfqYZcX.YhVbbLAC','pierluigitrc',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,NULL,'giovix92@giovix92.com','$2a$12$CVzi8o0GzXg0RUTzhWclZeNgq7cO4m5kYEZng8WTuQBM3KJnbj6iC','giovix92',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,NULL,'maario.rossi@gmail.com','$2a$12$odJRO/752qp9XmUoLpXKoehniX64Fi8VQb9yh/fbf3G0PRTYjJMUG','rosss',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,NULL,'miaomiao@gmail.com','$2a$12$bdT0H9l8G0U/43zZoSmN4ukTsKih.JIVhQRoVLuH.e03bt5jmYIQm','a',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,NULL,'giovaani@giovanni.com','$2a$12$AAIEhoK7ZGMI46RN080Lku8pi30DehFa7IT4gf2b1D55EDeyDYTqG','giovaani',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,NULL,'gaetano@gaetano.com','$2a$12$Op6M/oInuUfYameeNFpx0.xJrG8QEdD3Irga2xM29.x8L/RKj4Dea','gaetano',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,NULL,'ida@ida.com','$2a$12$8JANvWAlHYMKTgD3myN19uZlpKJukwf5Li6usS.H1htG2U5hcQRFS','ida',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,NULL,'carolina@carolina.com','$2a$12$6/KsIkmHyyFZxqCJnWychuJHrvtA0dszfC3wMFF/p1LjzpxZ2m716','carolina',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,NULL,'aureliobnv@gmail.com','$2a$12$afohvJsykAiQx1jmi7t8XunCsI5EiLezSANJ.cHy0PkhOBCwsWrWa','aureliobnv',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,NULL,'pietro.macri2000@gmail.com','$2a$12$1GtDElue7xDC0EvkP7opo.FV08JpGjdXgLOJOF5RiljU6HxZQgqY2','pietro.macri',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(38,NULL,'pietro@pietro.com','$2a$12$vINITH87n91JTHwG0Bd2QOu8CzbUnBUqzUy/PSzWFGJMp4Oggx0QO','pietro',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(39,NULL,'miaomiao@gatto.com','$2a$12$FiZJrrdhuQV7.u9VTHVnruANxYu9GV7I9qwthrhxDsSq9qNDPvNqW','miao',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-08 18:55:12
