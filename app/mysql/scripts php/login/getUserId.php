<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 


// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

$n='';
$p='';
if(isset($_GET['n'])) $n = $_GET['n'];
if(isset($_GET['p'])) $p = $_GET['p'];

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$sql = "SELECT * FROM User where name='$n' and password='$p'";
$con -> query('SET NAMES utf8');
$wrap = array();
$data= new stdClass();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  while ($row = mysqli_fetch_row($result)) {
    $data -> idUser= $row[0];
  }
  $rowcount=mysqli_num_rows($result);
  if($rowcount == 0){
    $data->response='KO';
    $data-> idUser ='';
  }else{
    $data->response='OK';
  }
  mysqli_free_result($result);
}

mysqli_close($con);
echo json_encode($data);
?>