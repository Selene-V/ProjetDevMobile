-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Client :  devbdd.iutmetz.univ-lorraine.fr
-- Généré le :  Mer 09 Décembre 2020 à 19:35
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
-- Structure de la table `Client`
--

CREATE TABLE IF NOT EXISTS `Client` (
  `id_client` int(5) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `identifiant` varchar(50) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `adr_numero` varchar(10) NOT NULL,
  `adr_voie` varchar(255) NOT NULL,
  `adr_code_postal` varchar(255) NOT NULL,
  `adr_ville` varchar(255) NOT NULL,
  `adr_pays` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Commande`
--

CREATE TABLE IF NOT EXISTS `Commande` (
  `id_commande` int(5) NOT NULL,
  `date_commande` date NOT NULL,
  `id_client` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Favori`
--

CREATE TABLE IF NOT EXISTS `Favori` (
  `id_client` int(5) NOT NULL,
  `id_produit` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Ligne_commande`
--

CREATE TABLE IF NOT EXISTS `Ligne_commande` (
  `id_commande` int(5) NOT NULL,
  `id_produit` int(5) NOT NULL,
  `id_taille` int(2) NOT NULL,
  `quantite` int(5) NOT NULL,
  `tarif` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Magasin`
--

CREATE TABLE IF NOT EXISTS `Magasin` (
  `id_magasin` int(5) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Mentions_legales`
--

CREATE TABLE IF NOT EXISTS `Mentions_legales` (
  `mentions` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
(1, 'A Noël c''est moi qui tient les rennes BDD', 'Un pull qui ravira les petits et les grands. Tricoté par d''authentiques grand-mères Australiennes, en laine 100% coton naturel issue d''une filière agriculture durable certifiée ISO-2560.', 52, 'renne.jpg', 1),
(2, 'Chien de noël', 'Un pull qui te permettra de faire baver d''envie tes petits camarades.', 41.5, 'chien.jpg', 1),
(3, 'Un lama au poil !', 'Un grand classique revisité, c''est toi le plus beau quand tu es décoré par ce pull !', 44, 'lama.jpg', 1),
(4, 'Mon beau sapin !', 'Ah, le chic à la Française, notre roi des forêts !', 35.2, 'sapin.jpg', 1),
(5, 'Un pull pour deux !', 'Pour des soirées au chaud en amoureux !', 38, 'double_pull.jpg', 1),
(6, 'Bonnet en laine', 'Ceci est un magnifique bonnet en laine qui tient extrêmement chaud !', 15, 'bonnet_renne.jpg', 2),
(7, 'Bonnet en laine plus cher', 'Ce bonnet est exactement le même que celui vu précédemment mais en plus cher !  Merci d''acheter celui la !', 22, 'bonnet_renne.jpg', 2),
(8, 'Dabsquette', 'Voici une casquette très laide et qui ne tient absolument pas chaud mais il y a un père noël dessus.', 17, 'casquette_dab.jpg', 3),
(9, 'Joli renne', 'Grâce à cette casquette vous pourrez vous faire passer pour un magnifique renne et tout le monde n''y verra que du feu !', 15, 'casquette_renne.jpg', 3);

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
(9, 7);

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
-- Index pour la table `Client`
--
ALTER TABLE `Client`
  ADD PRIMARY KEY (`id_client`);

--
-- Index pour la table `Commande`
--
ALTER TABLE `Commande`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `id_client` (`id_client`);

--
-- Index pour la table `Favori`
--
ALTER TABLE `Favori`
  ADD PRIMARY KEY (`id_client`,`id_produit`),
  ADD KEY `favoris_id_produit_fkey` (`id_produit`);

--
-- Index pour la table `Ligne_commande`
--
ALTER TABLE `Ligne_commande`
  ADD KEY `id_commande` (`id_commande`,`id_produit`,`id_taille`),
  ADD KEY `ligne_commande_id_produit_fkey` (`id_produit`),
  ADD KEY `ligne_commande_id_taille_fkey` (`id_taille`);

--
-- Index pour la table `Magasin`
--
ALTER TABLE `Magasin`
  ADD PRIMARY KEY (`id_magasin`);

--
-- Index pour la table `Produit`
--
ALTER TABLE `Produit`
  ADD PRIMARY KEY (`id_produit`),
  ADD KEY `produit_id_categorie_fkey` (`id_categorie`);

--
-- Index pour la table `Produit_taille`
--
ALTER TABLE `Produit_taille`
  ADD PRIMARY KEY (`id_produit`,`id_taille`),
  ADD KEY `id_produit` (`id_produit`,`id_taille`),
  ADD KEY `produit_taille_id_taille_fkey` (`id_taille`);

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
-- AUTO_INCREMENT pour la table `Client`
--
ALTER TABLE `Client`
  MODIFY `id_client` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `Commande`
--
ALTER TABLE `Commande`
  MODIFY `id_commande` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `Magasin`
--
ALTER TABLE `Magasin`
  MODIFY `id_magasin` int(5) NOT NULL AUTO_INCREMENT;
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
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `Commande`
--
ALTER TABLE `Commande`
  ADD CONSTRAINT `commande_id_client_fkey` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id_client`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `Favori`
--
ALTER TABLE `Favori`
  ADD CONSTRAINT `favoris_id_client_fkey` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id_client`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `favoris_id_produit_fkey` FOREIGN KEY (`id_produit`) REFERENCES `Produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `Ligne_commande`
--
ALTER TABLE `Ligne_commande`
  ADD CONSTRAINT `ligne_commande_id_commande_fkey` FOREIGN KEY (`id_commande`) REFERENCES `Commande` (`id_commande`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ligne_commande_id_produit_fkey` FOREIGN KEY (`id_produit`) REFERENCES `Produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ligne_commande_id_taille_fkey` FOREIGN KEY (`id_taille`) REFERENCES `Taille` (`id_taille`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `Produit`
--
ALTER TABLE `Produit`
  ADD CONSTRAINT `produit_id_categorie_fkey` FOREIGN KEY (`id_categorie`) REFERENCES `Categorie` (`id_categorie`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `Produit_taille`
--
ALTER TABLE `Produit_taille`
  ADD CONSTRAINT `produit_taille_id_produit_fkey` FOREIGN KEY (`id_produit`) REFERENCES `Produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `produit_taille_id_taille_fkey` FOREIGN KEY (`id_taille`) REFERENCES `Taille` (`id_taille`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
