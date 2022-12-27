<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 
 


$n="";
$q=0;
$qu="";

if(isset($_GET['n'])  && $_GET['n']!="")  $n=$_GET['n'];
if(isset($_GET['q'])  && $_GET['q']!="")  $q=$_GET['q'];
if(isset($_GET['qu']) && $_GET['qu']!="")  $qu=$_GET['qu'];
$id =$_GET['id'];


// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$data = new stdClass();

$sql = "UPDATE `Shop` SET `name`='$n', `quantity`=$q, `id_quantity_unit`='$qu' WHERE id_shop = $id";

$result = mysqli_query($con, $sql);
$affected = mysqli_affected_rows($con);
if($affected < 0){
    $data-> response = "KO";
}else{
    $data-> response = "OK";
}
mysqli_close($con);
echo json_encode($data);
?>