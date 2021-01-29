<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "rechercheCommandes";

$id_client=strval($_GET['id_client']);

$res = $db_connexion->prepare('SELECT * FROM Commande WHERE id_client = ?');
$res->bindParam(1, $id_client, PDO::PARAM_STR);
$res->execute();

$res = $res->fetchAll(PDO::FETCH_ASSOC);

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res,
);

echo json_encode($resComplet);