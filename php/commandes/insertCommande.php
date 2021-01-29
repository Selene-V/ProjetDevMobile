<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "insertCommande";

$id_client = strval($_GET['id_client']);
$date = $_GET['date'];

$res = $db_connexion->prepare("INSERT INTO Commande (id_client, date_commande) VALUES(?, ?)");
$res->bindParam(1, $id_client, PDO::PARAM_STR);
$res->bindParam(2, $date, PDO::PARAM_STR);
$res = $res->execute();

$resComplet = array(
	'requete'=>$requete,
	'res'=>$res
);

echo json_encode($resComplet);
