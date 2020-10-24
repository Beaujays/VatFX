/*
Database file voor VAT. Let op: Door de code uit te voeren wordt de hu
idge database gewist.
*/

DROP DATABASE IF EXISTS vat;

CREATE DATABASE vat;

USE vat;

DROP TABLE IF EXISTS  `cylinder`;

CREATE TABLE `cylinder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `shape` varchar(45) DEFAULT NULL,
  `radius` int DEFAULT NULL,
  `height` int DEFAULT NULL,
  `volume` int DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS  `cube`;

  CREATE TABLE `cube` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `shape` varchar(45) DEFAULT NULL,
  `length` int DEFAULT NULL,
  `height` int DEFAULT NULL,
  `depth` int DEFAULT NULL,
  `volume` int DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS  `globe`;

  CREATE TABLE `globe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `shape` varchar(45) DEFAULT NULL,
  `radius` int DEFAULT NULL,
  `volume` int DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS  `hemisphere`;

  CREATE TABLE `hemisphere` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `shape` varchar(45) DEFAULT NULL,
  `radius` int DEFAULT NULL,
  `volume` int DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS  `pyramid`;

  CREATE TABLE `pyramid` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `shape` varchar(45) DEFAULT NULL,
  `length` int DEFAULT NULL,
  `height` int DEFAULT NULL,
  `depth` int DEFAULT NULL,
  `volume` int DEFAULT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;