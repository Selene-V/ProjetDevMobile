<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "recherche";

$res = $db_connexion->query('SELECT * FROM Mentions_legales', PDO::FETCH_OBJ);

$res = $res->fetchAll();

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res
);

echo json_encode($resComplet);