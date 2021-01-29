-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Client :  devbdd.iutmetz.univ-lorraine.fr
-- Généré le :  Ven 29 Janvier 2021 à 18:02
-- Version du serveur :  10.3.27-MariaDB
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Contenu de la table `Client`
--

INSERT INTO `Client` (`id_client`, `nom`, `prenom`, `identifiant`, `mot_de_passe`, `adr_numero`, `adr_voie`, `adr_code_postal`, `adr_ville`, `adr_pays`) VALUES
(1, 'Dupont', 'Jean', 'jeandupont@gmail.com', 'Azerty1?', '56', 'Rue de la foret', '57000', 'METZ', 'FRANCE'),
(2, 'Jean', 'Pierre', 'jeanpierre@gmail.com', 'Azerty1?', '5', 'Chemin des corses', '51000', 'REIMS', 'FRANCE');

-- --------------------------------------------------------

--
-- Structure de la table `Commande`
--

CREATE TABLE IF NOT EXISTS `Commande` (
  `id_commande` int(5) NOT NULL,
  `date_commande` varchar(255) NOT NULL,
  `id_client` int(5) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Contenu de la table `Commande`
--

INSERT INTO `Commande` (`id_commande`, `date_commande`, `id_client`) VALUES
(1, '2021-01-22', 1),
(2, '2020-12-09', 1),
(3, '2021-01-23', 2),
(4, '2021-29-01', 1);

-- --------------------------------------------------------

--
-- Structure de la table `Favori`
--

CREATE TABLE IF NOT EXISTS `Favori` (
  `id_client` int(5) NOT NULL,
  `id_produit` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Favori`
--

INSERT INTO `Favori` (`id_client`, `id_produit`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 6),
(1, 8),
(2, 1),
(2, 8);

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

--
-- Contenu de la table `Ligne_commande`
--

INSERT INTO `Ligne_commande` (`id_commande`, `id_produit`, `id_taille`, `quantite`, `tarif`) VALUES
(1, 1, 1, 1, 52),
(1, 2, 1, 2, 83),
(2, 4, 2, 1, 35.2),
(3, 9, 7, 1, 15);

-- --------------------------------------------------------

--
-- Structure de la table `Magasin`
--

CREATE TABLE IF NOT EXISTS `Magasin` (
  `id_magasin` int(5) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Magasin`
--

INSERT INTO `Magasin` (`id_magasin`, `nom`, `latitude`, `longitude`) VALUES
(1, 'Magasin de Metz', 49.119309, 6.175716),
(2, 'Magasin de Nancy', 48.6833, 6.2);

-- --------------------------------------------------------

--
-- Structure de la table `Mentions_legales`
--

CREATE TABLE IF NOT EXISTS `Mentions_legales` (
  `mentions` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Mentions_legales`
--

INSERT INTO `Mentions_legales` (`mentions`) VALUES
('La boutique monPullMoche, portant le SIRET 840.489.398.00012, est une société de droit français enregistrée auprès du RCS. Cette société est propriétaire du nom de domaine www.monpullmoche.fr (ci-après, le « site »).'),
('<h1>Coordonnées</h1>Adresse : 9, rue du vivier 57000 METZ<br>FRANCE<br>Téléphone : +33 3 83 85 96 75<br>Email : info@monpullmoche.fr'),
('<h1>Conditions générales d''utilisation</h1>Nous mettons tout en œuvre pour assurer une mise à jour fiable et constante de l''application. Toutefois, des erreurs ou omissions peuvent survenir. L''utilisateur devra donc signaler toutes modifications du site qu''il jugerait utile.'),
('<h1>Services fournis</h1>L''ensemble des activités de la société ainsi que ses informations sont présentés sur notre site www.monpullmoche.fr.<br><br>Nous nous efforçons de fournir des informations aussi précises que possibles. Les renseignements figurant sur le site et l''application mobile ne sont pas exhaustifs et les photos non contractuelles. Ils sont donnés sous réserve de modifications ayant été apportées depuis leur mise en ligne. Par ailleurs, toutes les informations indiquées sur le site ou l''application mobile sont données à titre indicatif, et sont susceptibles de changer ou d''évoluer sans préavis.<br><br>Les liens hypertextes mis en place dans le cadre du présent site en direction d''autres ressources présentes sur le réseau Internet ne sauraient engager la responsabilité de La boutique monpullmoche.'),
('<h1>Propriété intellectuelle</h1>Tout le contenu du présent site www.monpullmoche.fr, incluant, de façon non limitative, les graphismes, images, textes, vidéos, animations, sons, logos, gifs et icônes ainsi que leur mise en forme sont la propriété exclusive de la société à l’exception des marques, logos ou contenus appartenant à d’autres sociétés partenaires ou auteurs. Toute reproduction, distribution, modification, adaptation, retransmission ou publication, même partielle, de ces différents éléments est strictement interdite sans l’accord exprès par écrit de La boutique monpullmoche. Cette représentation ou reproduction, par quelque procédé que ce soit, constitue une contrefaçon sanctionnée par les articles L.335–2 et suivants du Code de la propriété intellectuelle. Le non-respect de cette interdiction constitue une contrefaçon pouvant engager la responsabilité civile et pénale du contrefacteur. En outre, les propriétaires des Contenus copiés pourraient intenter une action en justice à votre encontre.'),
('<h1>Déclaration à la CNIL</h1>Conformément à la loi 78–17 du 6 janvier 1978 (modifiée par la loi 2004–801 du 6 août 2004 relative à la protection des personnes physiques à l’égard des traitements de données à caractère personnel) relative à l’informatique, aux fichiers et aux libertés, ce site a fait l’objet d’une déclaration 1657682 auprès de la Commission nationale de l’informatique et des libertés (www.cnil.fr).'),
('<h1>Litiges</h1>Les présentes conditions du site www.monpullmoche.fr sont régies par les lois françaises et toute contestation ou litiges qui pourraient naître de l’interprétation ou de l’exécution de celles-ci seront de la compétence exclusive des tribunaux dont dépend le siège social de la société. La langue de référence, pour le règlement de contentieux éventuels, est le français.'),
('<h1>Données personnelles</h1>De manière générale, vous n’êtes pas tenu de nous communiquer vos données personnelles lorsque vous visitez notre site.<br><br>Cependant, ce principe comporte certaines exceptions. En effet, pour certains services proposés par notre site, vous pouvez être amenés à nous communiquer certaines données telles que : votre nom, votre adresse, votre adresse électronique...<br><br>Dans tous les cas, vous pouvez refuser de fournir vos données personnelles. Dans ce cas, vous ne pourrez pas utiliser les services du site.<br><br>Enfin, nous pouvons collecter de manière automatique certaines informations vous concernant lors d’une simple navigation sur notre site Internet, notamment : des informations concernant l’utilisation de notre site, comme les zones que vous visitez et les services auxquels vous accédez, votre adresse IP, le type de votre navigateur, vos temps d’accès.<br><br>De telles informations sont utilisées exclusivement à des fins de statistiques internes, de manière à améliorer la qualité des services qui vous sont proposés. Les bases de données sont protégées par les dispositions de la loi du 1er juillet 1998 transposant la directive 96/9 du 11 mars 1996 relative à la protection juridique des bases de données.');

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
(1, 'A Noël c''est moi qui tient les rennes', 'Un pull qui ravira les petits et les grands. Tricoté par d''authentiques grand-mères Australiennes, en laine 100% coton naturel issue d''une filière agriculture durable certifiée ISO-2560.', 52, 'renne.jpg', 1),
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
  MODIFY `id_client` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `Commande`
--
ALTER TABLE `Commande`
  MODIFY `id_commande` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `Magasin`
--
ALTER TABLE `Magasin`
  MODIFY `id_magasin` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
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
