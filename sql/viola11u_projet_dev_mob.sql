-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Client :  devbdd.iutmetz.univ-lorraine.fr
-- Généré le :  Mer 09 Décembre 2020 à 11:32
-- Version du serveur :  10.3.25-MariaDB
-- Version de PHP :  7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `viola11u_projet_dev_mob`
--

-- --------------------------------------------------------

--
-- Structure de la table `Categorie`
--

CREATE TABLE IF NOT EXISTS `Categorie` (
  `id_categorie` int(3) NOT NULL,
  `titre` varchar(30) NOT NULL,
  `visuel` varchar(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Categorie`
--

INSERT INTO `Categorie` (`id_categorie`, `titre`, `visuel`) VALUES
(1, 'Pulls', 'renne.jpg'),
(2, 'Bonnets', 'bonnet_renne.jpg'),
(3, 'Casquettes', 'casquette_dab.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `Produit`
--

CREATE TABLE IF NOT EXISTS `Produit` (
  `id_produit` int(5) NOT NULL,
  `titre` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL,
  `tarif` float NOT NULL,
  `visuel` varchar(30) NOT NULL,
  `id_categorie` int(3) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Produit`
--

INSERT INTO `Produit` (`id_produit`, `titre`, `description`, `tarif`, `visuel`, `id_categorie`) VALUES
(1, 'A Noël c''est moi qui tient les rennes', 'Un pull qui ravira les petits et les grands. Tricoté par d''authentiques grand-mères Australiennes, en laine 100% coton naturel issue d''une filière agriculture durable certifiée ISO-2560.', 52, 'pull0.jpg', 1),
(2, 'Sonic te kiffe', 'Inspiré par la saga Séga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.', 41.5, 'pull1.jpg', 1),
(3, 'Mon renne a les boules', 'Un grand classique revisité, à la fois renne et sapin de Noël, c''est toi le plus beau quand tu es décoré par ce pull !', 44, 'pull2.jpg', 1),
(4, 'Le père Noël a une gastro', 'Ah, le chic à la Française. Parce que les stars aussi vont sur le pot de temps en temps...', 35.2, 'pull3.jpg', 1),
(5, 'Une guirlande de pères Noël', 'Ils sont tous en rang, ils te regardent, à toi d''être bien sage pour mériter ce pull de fête !', 38, 'pull4.jpg', 1),
(6, 'La chaleur des rennes', 'Classique mais efficace, un bonnet dont l''élégance n''est pas à souligner, il vous grattera comme il faut !', 15, 'bonnet0.jpg', 2),
(7, 'Noël Lorientais', 'Idéal pour tous les fans du FC Lorient, mais pas que !', 22, 'bonnet1.jpg', 2),
(8, 'Beau comme un Elfe', 'HE bonnet à oreilles, couleurs indémodables pour cette création inspirée de l''univers de Tolkien.', 17, 'bonnet2.jpg', 2),
(9, 'Traineau de rennes', 'Un grand classique, ce magnifique bonnet vous sierra en toutes circonstances, et s''adaptera à toutes vos tenues hivernales.', 15, 'bonnet3.jpg', 2),
(10, 'Real Santa', 'En direct de NYC, avec bois de rennes télescopiques !!!', 20, 'bonnet4.jpg', 2),
(11, 'Les trois amis', 'Un trio féérique prendra soin de votre calvitie naissante, vous en serez ravi !', 30, 'casquette0.jpg', 3),
(12, 'Dall', 'Joyeux Noël avec nos petits lutins dansants !', 35, 'casquette1.jpg', 3),
(13, 'Magie de Noël', 'Quoi de plus beau qu''un bonhomme de neige avec les enfants quand les premiers flocons tombent du ciel ?', 26, 'casquette2.jpg', 3),
(14, 'Santa Hangover', 'Le Père Noël est bien fatigué sur cette magnifique casquette, mais n''est-ce pas notre lot à tous une fois les fêtes passées ?', 30, 'casquette3.jpg', 3);

-- --------------------------------------------------------

--
-- Structure de la table `Produit_taille`
--

CREATE TABLE IF NOT EXISTS `Produit_taille` (
  `id_produit` int(5) NOT NULL,
  `id_taille` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Produit_taille`
--

INSERT INTO `Produit_taille` (`id_produit`, `id_taille`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),
(5, 6),
(6, 7),
(7, 7),
(8, 7),
(9, 7),
(10, 7),
(11, 8),
(11, 9),
(11, 10),
(11, 11),
(11, 12),
(11, 13),
(12, 9),
(12, 11),
(12, 13),
(13, 8),
(13, 9),
(13, 10),
(13, 11),
(13, 12),
(13, 13),
(14, 8),
(14, 10),
(14, 11),
(14, 13);

-- --------------------------------------------------------

--
-- Structure de la table `Taille`
--

CREATE TABLE IF NOT EXISTS `Taille` (
  `id_taille` int(2) NOT NULL,
  `libelle` varchar(5) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Taille`
--

INSERT INTO `Taille` (`id_taille`, `libelle`) VALUES
(1, 'XS'),
(2, 'S'),
(3, 'M'),
(4, 'L'),
(5, 'XL'),
(6, 'XXL'),
(7, 'TU'),
(8, '52'),
(9, '54'),
(10, '56'),
(11, '58'),
(12, '60'),
(13, '62');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `Categorie`
--
ALTER TABLE `Categorie`
  ADD PRIMARY KEY (`id_categorie`);

--
-- Index pour la table `Produit`
--
ALTER TABLE `Produit`
  ADD PRIMARY KEY (`id_produit`);

--
-- Index pour la table `Produit_taille`
--
ALTER TABLE `Produit_taille`
  ADD PRIMARY KEY (`id_produit`,`id_taille`);

--
-- Index pour la table `Taille`
--
ALTER TABLE `Taille`
  ADD PRIMARY KEY (`id_taille`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `Categorie`
--
ALTER TABLE `Categorie`
  MODIFY `id_categorie` int(3) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `Produit`
--
ALTER TABLE `Produit`
  MODIFY `id_produit` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT pour la table `Taille`
--
ALTER TABLE `Taille`
  MODIFY `id_taille` int(2) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
