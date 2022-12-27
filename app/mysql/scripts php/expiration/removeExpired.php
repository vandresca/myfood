<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 


// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}


$sql ="DELETE FROM Pantry WHERE id_pantry in (SELECT id_pantry FROM (SELECT id_pantry, idUser, case ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< UNIX_TIMESTAMP(CURRENT_TIMESTAMP) then 'expired' ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> UNIX_TIMESTAMP(CURRENT_TIMESTAMP) AND UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY))then '0to10days' ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY)) then 'more10days' ";
$sql .="end as expiration FROM `Pantry`) as t1 WHERE t1.expiration = 'expired' and t1.idUser=".$_GET['u'].")";

$con -> query('SET NAMES utf8');

$data = new stdClass();
if ($result = mysqli_query($con, $sql)) {
  $affected = mysqli_affected_rows($con);
  if($affected == 0){
    $data-> response = "KO";
  }else{
    $data-> response = "OK";
  }
}
mysqli_close($con);
echo json_encode($data);
?>