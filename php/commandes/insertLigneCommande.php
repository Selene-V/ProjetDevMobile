<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "insertLigneCommande";

$id_produit = strval($_GET['id_produit']);
$id_commande = strval($_GET['id_commande']);
$id_taille = strval($_GET['id_taille']);
$quantite = strval($_GET['quantite']);
$tarif = strval($_GET['tarif']);

$res = $db_connexion->prepare("INSERT INTO Commande VALUES(?, ?, ?, ?, ?)");
$res->bindParam(1, $id_commande, PDO::PARAM_STR);
$res->bindParam(2, $id_produit, PDO::PARAM_STR);
$res->bindParam(3, $id_taille, PDO::PARAM_STR);
$res->bindParam(4, $quantite, PDO::PARAM_STR);
$res->bindParam(5, $tarif, PDO::PARAM_STR);
$res = $res->execute();

$resComplet = array(
	'requete'=>$requete,
	'res'=>$res
);

echo json_encode($resComplet);
