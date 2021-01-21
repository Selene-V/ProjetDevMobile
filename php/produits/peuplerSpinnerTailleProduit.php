<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "taillesProduits";

$res = $db_connexion->query('SELECT Produit_taille.id_produit as id_produit, Taille.libelle as libelle FROM `Produit_taille` INNER JOIN Taille ON Taille.id_taille = Produit_taille.id_taille', PDO::FETCH_OBJ);

$res = $res->fetchAll();

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res
);

echo json_encode($resComplet);