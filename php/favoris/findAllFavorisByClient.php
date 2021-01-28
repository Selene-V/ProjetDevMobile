<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "rechercheFavorisProduitClient";

$id_client=intval($_GET['id_client']);

$res = $db_connexion->prepare('SELECT * FROM Favori INNER JOIN Produit ON Produit.id_produit = Favori.id_produit WHERE Favori.id_client = ?');
$res->bindParam(1, $id_client, PDO::PARAM_INT);
$res->execute();

$res = $res->fetchAll(PDO::FETCH_OBJ);

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res,
);

echo json_encode($resComplet);