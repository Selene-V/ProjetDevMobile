<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "insert";

$nom=strval($_GET['nom']);
$prenom=strval($_GET['prenom']);
$identifiant_client=strval($_GET['identifiant_client']);
$mdp=strval($_GET['mdp']);
$adrNum=strval($_GET['adrNum']);
$adrVoie=strval($_GET['adrVoie']);
$adrCP=strval($_GET['adrCP']);
$adrVille=strval($_GET['adrVille']);
$adrP=strval($_GET['adrP']);


$res = $db_connexion->prepare('INSERT INTO Client (nom, prenom, identifiant, mot_de_passe, adr_numero, adr_voie, adr_code_postal, adr_ville, adr_pays) VALUES (?,?,?,?,?,?,?,?,?)');
$res->bindParam(1, $nom, PDO::PARAM_STR);
$res->bindParam(2, $prenom, PDO::PARAM_STR);
$res->bindParam(3, $identifiant_client, PDO::PARAM_STR);
$res->bindParam(4, $mdp, PDO::PARAM_STR);
$res->bindParam(5, $adrNum, PDO::PARAM_STR);
$res->bindParam(6, $adrVoie, PDO::PARAM_STR);
$res->bindParam(7, $adrCP, PDO::PARAM_STR);
$res->bindParam(8, $adrVille, PDO::PARAM_STR);
$res->bindParam(9, $adrP, PDO::PARAM_STR);

$valid = $res->execute();

$resComplet = array(
	'requete'=>$requete,
	'data'=>$valid
);

echo json_encode($resComplet);
