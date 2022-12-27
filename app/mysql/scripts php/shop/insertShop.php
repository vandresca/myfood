<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 



$n="";
$q=0;
$qu="";

if(isset($_GET['n']) && $_GET['n']!="")  $n=$_GET['n'];
if(isset($_GET['q'])  && $_GET['q']!="")  $q=$_GET['q'];
if(isset($_GET['qu'])  && $_GET['qu']!="")  $qu=$_GET['qu'];
$u = $_GET['u'];


// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}


$sql = "INSERT INTO `Shop`(`name`, `quantity`, `id_quantity_unit`, `idUser`) ";
$sql .=" values('$n',$q,'$qu',$u)";

$result = mysqli_query($con, $sql);
$data = new stdClass();

$row_inserted_id = mysqli_insert_id($con);
$affected = mysqli_affected_rows($con);

if($affected < 1){
    $data-> response = "KO";
    $data-> value = 0;
}else{
    $data-> response = "OK";
    $data-> value = $row_inserted_id;
}

mysqli_close($con);
echo json_encode($data);
?>