<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "magasins";


$res = $db_connexion->query('SELECT * FROM Magasin');

$res = $res->fetchAll(PDO::FETCH_OBJ);

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res,
);

echo json_encode($resComplet);