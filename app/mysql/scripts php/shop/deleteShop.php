<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 


if (!function_exists('str_starts_with')) {
  function str_starts_with($str, $start) {
    return (@substr_compare($str, $start, 0, strlen($start))==0);
  }
}

// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$data = new stdClass();
$sql ="DELETE FROM Shop WHERE id_shop =".$_GET['id'];
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