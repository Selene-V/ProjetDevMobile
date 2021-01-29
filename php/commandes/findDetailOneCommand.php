<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "rechercheDetail";

$id_commande=intval($_GET['id_commande']);

$res = $db_connexion->prepare('SELECT * FROM Produit inner join Ligne_commande on Produit.id_produit = Ligne_commande.id_produit inner join Taille on Ligne_commande.id_taille = Taille.id_taille where Ligne_commande.id_commande = ?');
$res->bindParam(1, $id_commande, PDO::PARAM_INT);
$res->execute();

$res = $res->fetchAll(PDO::FETCH_OBJ);

$resComplet = array(
	'requete'=>$requete,
	'data'=>$res,
);

echo json_encode($resComplet);