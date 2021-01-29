<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "produits";

$id_categorie=intval($_GET['id_categorie']);

$res = $db_connexion->prepare('SELECT * FROM Produit WHERE id_categorie = ?');
$res->bindParam(1, $id_categorie, PDO::PARAM_INT);
$res->execute();

$res = $res->fetchAll(PDO::FETCH_OBJ);

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res
);

echo json_encode($resComplet);