<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "produits";

$res = $db_connexion->query('SELECT * FROM Produit', PDO::FETCH_OBJ);

$res = $res->fetchAll();

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res
);

echo json_encode($resComplet);