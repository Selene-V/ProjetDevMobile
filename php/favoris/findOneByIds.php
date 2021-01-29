<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "favorisProduit";

$id_client=intval($_GET['id_client']);
$id_produit=intval($_GET['id_produit']);

$res = $db_connexion->prepare('SELECT * FROM Favori WHERE id_client = ? AND id_produit = ?');
$res->bindParam(1, $id_client, PDO::PARAM_INT);
$res->bindParam(2, $id_produit, PDO::PARAM_INT);
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