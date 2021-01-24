<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "rechercheID";

$id_client=strval($_GET['id_client']);

$res = $db_connexion->prepare('SELECT * FROM Client WHERE id_client = ?');
$res->bindParam(1, $id_client, PDO::PARAM_STR);
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