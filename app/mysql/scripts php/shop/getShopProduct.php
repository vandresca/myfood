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

$servername = "localhost:3306";
$username = "vandresc_myfood";
$password = "7*Ui[=pKU{b{";
$database = "vandresc_myfood";

// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$sql ="SELECT name, quantity, id_quantity_unit FROM `Shop` WHERE id_shop =".$_GET['id'];

$data = new stdClass();
$data->response = "KO";
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  while ($row = mysqli_fetch_row($result)) {
    $data->name= $row[0];
    $data->quantity=$row[1];
    $data->quantityUnit=$row[2];
    $data->response = "OK";
  }
  mysqli_free_result($result);
}else{
    $data->response = "KO";
}
mysqli_close($con);

echo json_encode($data);
?>

