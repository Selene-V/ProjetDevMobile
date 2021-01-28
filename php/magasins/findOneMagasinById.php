<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "rechercheIDMagasin";

$id_magasin=intval($_GET['id_magasin']);

$res = $db_connexion->prepare('SELECT * FROM Magasin WHERE id_magasin = ?');
$res->bindParam(1, $id_magasin, PDO::PARAM_STR);
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