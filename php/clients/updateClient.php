<?php
header("Access-Control-Allow-Origin:*", true);

include "../connexion.php";

$requete = "modification";

$nom=strval($_GET['nom']);
$prenom=strval($_GET['prenom']);
$identifiant_client=strval($_GET['identifiant_client']);
$mdp=strval($_GET['mdp']);
$adrNum=strval($_GET['adrNum']);
$adrVoie=strval($_GET['adrVoie']);
$adrCP=strval($_GET['adrCP']);
$adrVille=strval($_GET['adrVille']);
$adrP=strval($_GET['adrP']);
$id=intval($_GET['id']);


$res = $db_connexion->prepare('UPDATE Client SET nom = ?, prenom = ?, identifiant = ?, mot_de_passe = ?, adr_numero = ?, adr_voie = ?, adr_code_postal = ?, adr_ville = ?, adr_pays = ? WHERE id_client = ?');
$res->bindParam(1, $nom, PDO::PARAM_STR);
$res->bindParam(2, $prenom, PDO::PARAM_STR);
$res->bindParam(3, $identifiant_client, PDO::PARAM_STR);
$res->bindParam(4, $mdp, PDO::PARAM_STR);
$res->bindParam(5, $adrNum, PDO::PARAM_STR);
$res->bindParam(6, $adrVoie, PDO::PARAM_STR);
$res->bindParam(7, $adrCP, PDO::PARAM_STR);
$res->bindParam(8, $adrVille, PDO::PARAM_STR);
$res->bindParam(9, $adrP, PDO::PARAM_STR);
$res->bindParam(10, $id, PDO::PARAM_INT);

$valid = $res->execute();
if (!$res->execute())
{
    echo "\nPDO::errorInfo():\n";
    print_r($res->errorInfo());
}
$resComplet = array(
	'requete'=>$requete,
	'data'=>$valid
);
var_dump($resComplet);
echo json_encode($resComplet);
