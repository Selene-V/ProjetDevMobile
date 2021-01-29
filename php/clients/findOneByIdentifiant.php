<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "recherche";

$identifiant_client=strval($_GET['identifiant_client']);

$res = $db_connexion->prepare('SELECT * FROM Client WHERE identifiant = ?');
$res->bindParam(1, $identifiant_client, PDO::PARAM_STR);
$res->execute();

$res = $res->fetch(PDO::FETCH_ASSOC);

if ($res === false){
	$resComplet = array(
		'requete'=>$requete,
		'data'=>$res,
		'res'=>false
	);
} else {
	$resComplet = array(
		'requete'=>$requete,
		'data'=>$res,
		'res'=>true
	);
}


echo json_encode($resComplet);