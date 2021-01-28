<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "insertFavori";

$id_client=intval($_GET['id_client']);
$id_produit=intval($_GET['id_produit']);

$res = $db_connexion->prepare('INSERT INTO Favori VALUES (?, ?)');
$res->bindParam(1, $id_client, PDO::PARAM_INT);
$res->bindParam(2, $id_produit, PDO::PARAM_INT);
$valid = $res->execute();

$resComplet = array(
	'requete'=>$requete,
	'data'=>$valid,
	'id_produit'=>$id_produit
);

echo json_encode($resComplet);