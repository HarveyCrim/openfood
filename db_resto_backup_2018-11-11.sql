# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Hôte: 127.0.0.1 (MySQL 5.7.20)
# Base de données: db_resto_backup
# Temps de génération: 2018-11-11 21:30:42 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Affichage de la table t_commands
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_commands`;

CREATE TABLE `t_commands` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idfood` int(11) DEFAULT NULL,
  `qte` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `datecommand` date DEFAULT NULL,
  `idInvoice` int(11) DEFAULT NULL,
  `iduser` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idfood` (`idfood`),
  KEY `idInvoice` (`idInvoice`),
  KEY `iduser` (`iduser`),
  CONSTRAINT `t_commands_ibfk_1` FOREIGN KEY (`idfood`) REFERENCES `t_foods` (`id`),
  CONSTRAINT `t_commands_ibfk_2` FOREIGN KEY (`idInvoice`) REFERENCES `t_invoices` (`id`),
  CONSTRAINT `t_commands_ibfk_3` FOREIGN KEY (`iduser`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_commands` WRITE;
/*!40000 ALTER TABLE `t_commands` DISABLE KEYS */;

INSERT INTO `t_commands` (`id`, `idfood`, `qte`, `amount`, `datecommand`, `idInvoice`, `iduser`)
VALUES
	(472,14,1,24000,NULL,86,1),
	(473,15,1,27500,NULL,86,1),
	(474,16,1,20800,NULL,86,1),
	(475,17,1,16000,NULL,86,1),
	(476,23,1,24000,NULL,87,1),
	(477,22,1,27200,NULL,87,1),
	(478,24,1,24000,NULL,87,1),
	(479,21,1,24000,NULL,87,1),
	(480,47,1,4000,NULL,87,1),
	(481,58,1,96000,NULL,87,1),
	(482,57,1,96000,NULL,87,1),
	(483,55,1,96000,NULL,88,1),
	(484,54,1,96000,NULL,88,1),
	(485,53,1,96000,NULL,88,1),
	(486,19,1,20800,NULL,88,1),
	(487,21,1,24000,NULL,88,1);

/*!40000 ALTER TABLE `t_commands` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_commands_booking
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_commands_booking`;

CREATE TABLE `t_commands_booking` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idfood` int(11) NOT NULL,
  `qte` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `idInvoice` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idfood` (`idfood`),
  KEY `idInvoice` (`idInvoice`),
  KEY `iduser` (`iduser`),
  CONSTRAINT `booking_food_cmd` FOREIGN KEY (`idfood`) REFERENCES `t_foods` (`id`),
  CONSTRAINT `booking_invoice_cmd` FOREIGN KEY (`idInvoice`) REFERENCES `t_invoices_booking` (`id`),
  CONSTRAINT `booking_user_cmd` FOREIGN KEY (`iduser`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_commands_booking` WRITE;
/*!40000 ALTER TABLE `t_commands_booking` DISABLE KEYS */;

INSERT INTO `t_commands_booking` (`id`, `idfood`, `qte`, `amount`, `idInvoice`, `iduser`)
VALUES
	(104,14,1,24000,25,1),
	(105,15,1,27500,25,1),
	(106,16,1,20800,25,1),
	(107,24,1,24000,26,1),
	(108,23,1,24000,26,1),
	(109,22,1,27200,26,1),
	(110,14,1,24000,27,1),
	(111,15,1,27500,27,1),
	(112,16,1,20800,27,1),
	(113,17,1,16000,27,1),
	(114,19,1,20800,27,1);

/*!40000 ALTER TABLE `t_commands_booking` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_foods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_foods`;

CREATE TABLE `t_foods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `namefood` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `typeFood` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `typeFood` (`typeFood`),
  CONSTRAINT `t_foods_ibfk_1` FOREIGN KEY (`typeFood`) REFERENCES `t_type_food` (`codeType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_foods` WRITE;
/*!40000 ALTER TABLE `t_foods` DISABLE KEYS */;

INSERT INTO `t_foods` (`id`, `namefood`, `price`, `typeFood`, `status`)
VALUES
	(14,'MAKAYABU+CHIKWANGE',24000,'PL',1),
	(15,'MAKAYABU+MAKEMBA',27500,'PL',1),
	(16,'NDAKALA+SEMOULE',20800,'PL',1),
	(17,'SAUCISSES FRAICHES+MAKEMBA+PETITS POIS',16000,'PL',1),
	(19,'LIBOKE SOSO/MBIKA+CHIKWANGE',20800,'PL',1),
	(20,'POULET BRAISE+MAKEMBA',24000,'PL',1),
	(21,'NZOMBO FUME A LA SAUCE TOMATE+CHIKWANGE',24000,'PL',1),
	(22,'POISSONS FRAIS (SAUCE TOMATE)',27200,'PL',1),
	(23,'COTES DE PORC + FRITE',24000,'PL',1),
	(24,'COTES DE PORC + MAKEMBA',24000,'PL',1),
	(25,'COTES DE PORC + CHIKWANGE',24000,'PL',1),
	(26,'CHAMPAGNE LAURENT PERRIER',241000,'BL',1),
	(27,'KOBA (ENTIER)',40000,'PL',1),
	(28,'NTABA A LA SAUCE TOMATE',24000,'PL',1),
	(29,'LIBOKE NGULU + CHIKWANGE',24000,'PL',1),
	(30,'LIBOKO MABONGO',32000,'PL',1),
	(31,'MITSHOPO YA NTABA+SEMOULE',20800,'PL',1),
	(32,'MUTU YA NTABA (ENTIER)',20800,'PL',1),
	(33,'ANTILOPE+MAKEMBA',27200,'PL',1),
	(34,'SOMBO+MAKEMBA',27200,'PL',1),
	(35,'MALANGWA+SEMOULE',27200,'PL',1),
	(36,'FUMBWA+SEMOULE',16000,'PL',1),
	(37,'NGAI NGAI+CHIKWANGUE',19200,'PL',1),
	(38,'NGAI NGAI YA MOSAKA+SEMOULE',20800,'PL',1),
	(39,'BROCHETTES (COUPLE)',14500,'PL',1),
	(40,'MAKOSO',8000,'PL',1),
	(41,'LEGUMES VERTES',8000,'PL',1),
	(42,'HARICOTS',8000,'PL',1),
	(43,'MBINZO+MAYEBO',8000,'PL',1),
	(44,'MATEMBELE',7200,'PL',1),
	(45,'PONDU',7200,'PL',1),
	(46,'LIMBONDO',7200,'PL',1),
	(47,'MAKEMBA',4000,'AC',1),
	(48,'RIZ',4000,'AC',1),
	(49,'SEMOULE',4000,'AC',1),
	(50,'CHIKWANGE',1000,'AC',1),
	(51,'GRAND MARNIER LIQUEUR',96000,'BL',1),
	(52,'CAMUS COGNAC',96000,'BL',1),
	(53,'COINTREAU',96000,'BL',1),
	(54,'COURVOISIER COGNAC',96000,'BL',1),
	(55,'COURVOISIER COGNAC',96000,'BL',1),
	(56,'JACK DANIELS',96000,'BL',1),
	(57,'JOHNNY WALKER BLACK LABEL',96000,'BL',1),
	(58,'JOHNNY WALKER RED LABEL',96000,'BL',1),
	(59,'J/B',56000,'BL',1),
	(60,'SIR EDOUARD',40000,'BL',1),
	(61,'AMAROULA',56000,'BL',1),
	(62,'BAILEYS',56000,'BL',1),
	(63,'NEEDERBURG VIN',64000,'BL',1),
	(64,'FLEUR DE CAP VIN',64000,'BL',1),
	(65,'PORTO',64000,'BL',1),
	(66,'BORDEAUX',40000,'BL',1),
	(67,'VIN  DES PAYS  D OCCIDENT ET AUTRES',24000,'BL',1),
	(68,'MATEUS ',32000,'BL',1),
	(69,'MUSCADOR  ROSE',18400,'BL',1),
	(70,'MESURE  BLACK  LABEL',10000,'BL',1),
	(71,'MESURE  CHIVAS LEGAL',10000,'BL',1),
	(72,'MESURE  RED  Ã‚Â§ AUTRES',10000,'BL',1),
	(73,'MESURE  COGNAC',11000,'BL',1),
	(74,'MESURE  COITREAU',11500,'BL',1),
	(75,'JUS  CERES',8000,'BL',1),
	(76,'JUS  FRESH',8000,'BL',1),
	(77,'JUS  TWIST',8000,'BL',1),
	(78,'SANGRIA',8000,'BL',1),
	(79,'GANDIA  VIN  ROUGE  PKT',8000,'BL',1),
	(80,'BAVARIA',6000,'BL',1),
	(81,'SAVANNA',5000,'BL',1),
	(82,'RED  BULL',5000,'BL',1),
	(83,'TEMBO',4000,'BL',1),
	(84,'GR  PRIMUS',4000,'BL',1),
	(85,'GD  SKOL',4000,'BL',1),
	(86,'GD DOPPEL',4000,'BL',1),
	(87,'GD TURBO KING',4000,'BL',1),
	(88,'GD VITALO',4000,'BL',1),
	(89,'GD 33 EXPORT',4000,'BL',1),
	(90,'GD CASTEL',4000,'BL',1),
	(91,'GD NKOY',4000,'BL',1),
	(92,'BEAUFORT',3500,'BL',1),
	(93,'HEINEKEN',4000,'BL',1),
	(94,'HEINEKEN',4000,'BL',1),
	(95,'FAYROUZ',3000,'BL',1),
	(96,'PEPSI',3000,'BL',1),
	(97,'COCA  ZERO',3500,'BL',1),
	(98,'MUTZIG',3500,'BL',1),
	(99,'PT  TURBO',4000,'BL',1),
	(100,'PT CASTEL',3500,'BL',1),
	(101,'LEGEND',3500,'BL',1),
	(102,'SUCREES',3000,'BL',1),
	(103,'SODA ',3000,'BL',1),
	(104,'GD EAU VIVE',4000,'BL',1),
	(105,'PT EAU VIVE',3000,'BL',1),
	(106,'JUS  TANGAWISI',2500,'BL',1),
	(107,'DROIT DE BOUCH',24000,'BL',1),
	(108,'DROIT DE BOUCH',16000,'BL',1),
	(109,'DROIT DE BOUCH',8000,'BL',1),
	(110,'BOL',1000,'AC',1),
	(111,'MAKAYABU+RIZ',27500,'PL',1),
	(112,'MAKAYABU+SEMOULE',27500,'PL',1),
	(113,'NDAKALA+CHIKWANGE',20800,'PL',1),
	(114,'NDAKALA+MAKEMBA',20800,'PL',1),
	(115,'NDAKALA+RIZ',20800,'PL',1),
	(116,'SAUCISSES FRAICHES + FRITE + PETITS POIS ',16000,'PL',1),
	(117,'LIBOKE SOSO / MBIKA + MAKEMBA',20800,'PL',1),
	(118,'LIBOKE SOSO / MBIKA + SEMOULE',20800,'PL',1),
	(119,'LIBOKE SOSO / MBIKA + RIZ',20800,'PL',1),
	(120,'POULET BRAISE + FRITE',24000,'PL',1),
	(121,'POULET BRAISE + SEMOULE',24000,'PL',1),
	(122,'POULET BRAISE + RIZ',24000,'PL',1),
	(123,'POULET BRAISE + CHIKWANGE',24000,'PL',1),
	(124,'NZOMBO FUME A LA SAUCE TOMATE + SEMOULE',24000,'PL',1),
	(125,'NZOMBO FUME A LA SAUCE TOMATE + MAKEMBA',24000,'PL',1),
	(126,'NZOMBO FUME A LA SAUCE TOMATE + RIZ',24000,'PL',1),
	(127,'POISSON FRAIS (SAUCE TOTAME) + SEMOULE',27200,'PL',1),
	(128,'POISSON FRAIS (SAUCE TOTAME) + CHIKWANGE',27200,'PL',1),
	(129,'POISSON FRAIS (SAUCE TOTAME) + MAKEMBA',27200,'PL',1),
	(130,'POISSON FRAIS (SAUCE TOTAME) + RIZ',27200,'PL',1),
	(131,'NGANDO + CHIKWANGE',24000,'PL',1),
	(132,'NGANDO + SEMOULE',24000,'PL',1),
	(133,'NGANDO + RIZ',24000,'PL',1),
	(134,'NGANDO + MAKEMBA',24000,'PL',1),
	(135,'KOBA (ENTIER) + CHIKWANGE',40000,'PL',1),
	(136,'KOBA (ENTIER) + RIZ',40000,'PL',1),
	(137,'KOBA (ENTIER) + SEMOULE',40000,'PL',1),
	(138,'KOBA (ENTIER) + MAKEMBA',40000,'PL',1),
	(139,'NTABA A LA SAUCE TOTAME + CHIKWANGE',24000,'PL',1),
	(140,'NTABA A LA SAUCE TOTAME + SEMOULE',24000,'PL',1),
	(141,'NTABA A LA SAUCE TOTAME + MAKEMBA',24000,'PL',1),
	(142,'NTABA A LA SAUCE TOTAME + RIZ',24000,'PL',1),
	(143,'LIBOKE NGULU + SEMOULE',24000,'PL',1),
	(144,'LIBOKE NGULU + RIZ',24000,'PL',1),
	(145,'LIBOKE NGULU + MAKEMBA',24000,'PL',1),
	(146,'LIBOKE MABONGO + RIZ',32000,'PL',1),
	(147,'LIBOKE MABONGO + SEMOULE',32000,'PL',1),
	(148,'LIBOKE MABONGO + CHIKWANGE',32000,'PL',1),
	(149,'LIBOKE MABONGO + MAKEMBA',32000,'PL',1),
	(150,'MITSHOPO YA NTABA + MAKEMBA',20800,'PL',1),
	(151,'MITSHOPO YA NTABA + CHIKWANGE',20800,'PL',1),
	(152,'MITSHOPO YA NTABA + RIZ',20800,'PL',1),
	(153,'MUTU YA NTABA (ENTIER) + SEMOULE',20800,'PL',1),
	(154,'MUTU YA NTABA (ENTIER) + MAKEMBA',20800,'PL',1),
	(155,'MUTU YA NTABA (ENTIER) + CHIKWANGE',20800,'PL',1),
	(156,'MUTU YA NTABA (ENTIER) + RIZ',20800,'PL',1),
	(157,'ANTILOPE + SEMOULE',27200,'PL',1),
	(158,'ANTILOPE + CHIKWANGE',27200,'PL',1),
	(159,'ANTILOPE + RIZ',27200,'PL',1),
	(160,'ANTILOPE + FRITE',27200,'PL',1),
	(161,'SOMBO + SEMOULE ',27200,'PL',1),
	(162,'SOMBO + CHIKWANGE',27200,'PL',1),
	(163,'SOMBO + RIZ',27200,'PL',1),
	(164,'MALANGWA + CHIKWANGE',20800,'PL',1),
	(165,'MALANGWA + MAKEMBA',20800,'PL',1),
	(166,'MALANGWA + RIZ',20800,'PL',1),
	(167,'MALANGWA + FRITE',20800,'PL',1),
	(168,'FUMBWA + CHIKWANGE',16000,'PL',1),
	(169,'FUMBWA + RIZ',16000,'PL',1),
	(170,'FUMBWA + MAKEMBA',16000,'PL',1),
	(171,'FUMBWA + FRITE',16000,'PL',1),
	(172,'NGAI NGAI YA MOSAKA + CHIKWANGE ',20800,'PL',1),
	(173,'NGAI NGAI YA MOSAKA + MAKEMBA',20800,'PL',1),
	(174,'NGAI NGAI YA MOSAKA + RIZ',20800,'PL',1),
	(175,'NGAI NGAI YA MOSAKA + FRITE',20800,'PL',1),
	(176,'NGAI NGAI + SEMOULE',20800,'PL',1),
	(177,'NGAI NGAI + MAKEMBA',20800,'PL',1),
	(178,'NGAI NGAI + RIZ',20800,'PL',1),
	(179,'NGAI NGAI + FRITE',20800,'PL',1),
	(180,'FRITE',4000,'AC',1),
	(181,'XXL',3000,'BL',1),
	(182,'SAUCISSES + CHIKWANGE',16000,'PL',1),
	(183,'SIMBILIKI+RIZ',27200,'PL',1),
	(184,'SIMBILIKI+SEMOULE',27200,'PL',1),
	(185,'SIMBILIKI+CHIKWANGUE',27200,'PL',1),
	(186,'SIMBILIKI+CHIKWANGUE',27200,'PL',1),
	(187,'SUCREES GD',3500,'BL',1),
	(188,'TONIC',3000,'BL',1),
	(190,'MESURE AMARULA',10000,'BL',1);

/*!40000 ALTER TABLE `t_foods` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_invoices
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_invoices`;

CREATE TABLE `t_invoices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idRate` int(11) DEFAULT NULL,
  `totalPaie` double DEFAULT NULL,
  `dateInvoice` datetime DEFAULT NULL,
  `serverName` varchar(100) DEFAULT NULL,
  `idclient` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idRate` (`idRate`),
  KEY `idclient` (`idclient`),
  CONSTRAINT `t_invoices_ibfk_1` FOREIGN KEY (`idRate`) REFERENCES `t_rates` (`idRate`),
  CONSTRAINT `t_invoices_ibfk_2` FOREIGN KEY (`idclient`) REFERENCES `t_table_client` (`nametable`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_invoices` WRITE;
/*!40000 ALTER TABLE `t_invoices` DISABLE KEYS */;

INSERT INTO `t_invoices` (`id`, `idRate`, `totalPaie`, `dateInvoice`, `serverName`, `idclient`)
VALUES
	(86,1,88300,'2018-11-11 00:00:00','test1','Table1'),
	(87,1,295200,'2018-11-11 00:00:00','test2','Table2'),
	(88,1,332800,'2018-11-11 00:00:00','test3','Table3');

/*!40000 ALTER TABLE `t_invoices` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_invoices_booking
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_invoices_booking`;

CREATE TABLE `t_invoices_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idRate` int(11) DEFAULT NULL,
  `dateInvoice` datetime DEFAULT NULL,
  `totalPaie` double DEFAULT NULL,
  `amountPaie` double DEFAULT NULL,
  `totaltopaid` int(11) DEFAULT NULL,
  `serverName` varchar(50) DEFAULT NULL,
  `idClient` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idRate` (`idRate`),
  CONSTRAINT `booking_invoice_rate` FOREIGN KEY (`idRate`) REFERENCES `t_rates` (`idRate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_invoices_booking` WRITE;
/*!40000 ALTER TABLE `t_invoices_booking` DISABLE KEYS */;

INSERT INTO `t_invoices_booking` (`id`, `idRate`, `dateInvoice`, `totalPaie`, `amountPaie`, `totaltopaid`, `serverName`, `idClient`)
VALUES
	(25,1,'2018-11-11 00:00:00',72300,10000,62300,'Test1','Table1'),
	(26,1,'2018-11-11 00:00:00',75200,60000,15200,'test2','Table2'),
	(27,1,'2018-11-11 00:00:00',109100,60000,49100,'test3','Table3');

/*!40000 ALTER TABLE `t_invoices_booking` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_parameter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_parameter`;

CREATE TABLE `t_parameter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nameEntreprise` varchar(100) DEFAULT NULL,
  `contact` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Affichage de la table t_rates
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_rates`;

CREATE TABLE `t_rates` (
  `idRate` int(11) NOT NULL AUTO_INCREMENT,
  `USD` double DEFAULT NULL,
  `EUR` double DEFAULT NULL,
  `FC` double DEFAULT NULL,
  `dateRate` datetime DEFAULT NULL,
  PRIMARY KEY (`idRate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_rates` WRITE;
/*!40000 ALTER TABLE `t_rates` DISABLE KEYS */;

INSERT INTO `t_rates` (`idRate`, `USD`, `EUR`, `FC`, `dateRate`)
VALUES
	(1,1,0.75,1600,NULL);

/*!40000 ALTER TABLE `t_rates` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_table_client
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_table_client`;

CREATE TABLE `t_table_client` (
  `nametable` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`nametable`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_table_client` WRITE;
/*!40000 ALTER TABLE `t_table_client` DISABLE KEYS */;

INSERT INTO `t_table_client` (`nametable`)
VALUES
	('Table1'),
	('Table2'),
	('Table3'),
	('Table4'),
	('Table5');

/*!40000 ALTER TABLE `t_table_client` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_trash
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_trash`;

CREATE TABLE `t_trash` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `invoiceid` int(11) DEFAULT NULL,
  `datecmd` date DEFAULT NULL,
  `qte` int(11) DEFAULT NULL,
  `idfood` int(11) DEFAULT NULL,
  `foodname` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `datetrash` date DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Affichage de la table t_type_food
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_type_food`;

CREATE TABLE `t_type_food` (
  `codeType` varchar(50) NOT NULL DEFAULT '',
  `nameType` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codeType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_type_food` WRITE;
/*!40000 ALTER TABLE `t_type_food` DISABLE KEYS */;

INSERT INTO `t_type_food` (`codeType`, `nameType`)
VALUES
	('AC','ACCOMPAGNEMENT'),
	('BL','BOISSON-LIQUEUR'),
	('PL','PLAT');

/*!40000 ALTER TABLE `t_type_food` ENABLE KEYS */;
UNLOCK TABLES;


# Affichage de la table t_users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_users`;

CREATE TABLE `t_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `fullname` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `priority` varchar(50) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `taux` (`rate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `t_users` WRITE;
/*!40000 ALTER TABLE `t_users` DISABLE KEYS */;

INSERT INTO `t_users` (`id`, `name`, `fullname`, `password`, `priority`, `rate`)
VALUES
	(1,'admin','Hornel LAMA','123456','administrator',1),
	(2,'serge','Serge Tsimba','888888','user',1),
	(3,'DJESSY','DJESSY','123456','user',1),
	(4,'bethy','Bethy','123456','administrator',1),
	(5,'SERGE','CAISSIER : SERGE','123456','administrator',1);

/*!40000 ALTER TABLE `t_users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
