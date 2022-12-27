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


$sql ="DELETE p, ip FROM Pantry p JOIN ImageProduct ip ON p.id_pantry = ip.id_product WHERE p.id_pantry = ".$_GET['id'];
$con -> query('SET NAMES utf8');

$result = mysqli_query($con, $sql);

$data = new stdClass();

$affected = mysqli_affected_rows($con);
if($affected < 1){
    $data-> response = "KO";
}else{
    $data-> response = "OK";
}

mysqli_close($con);
echo json_encode($data);
?>