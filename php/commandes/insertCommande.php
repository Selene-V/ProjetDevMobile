<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "insertCommande";

$id_client = strval($_GET['id_client']);
$id_commande = strval($_GET['id_commande']);
$date = $_GET['date'];

$res = $db_connexion->prepare("INSERT INTO Commande VALUES(?, ?, ?)");
$res->bindParam(1, $id_client, PDO::PARAM_STR);
$res->bindParam(2, $id_commande, PDO::PARAM_STR);
$res->bindParam(3, $date, PDO::PARAM_STR);
$res->execute();

echo json_encode($requete);
