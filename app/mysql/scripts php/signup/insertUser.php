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

$n = "";
$s = "";
$e = "";
$p = "";

if(isset($_GET['n'])) $n = $_GET['n'];
if(isset($_GET['s'])) $s = $_GET['s'];
if(isset($_GET['e'])) $e = $_GET['e'];
if(isset($_GET['p'])) $p = $_GET['p'];


$sql = "SELECT idUser FROM User where email='$e'";
$con -> query('SET NAMES utf8');
$data = new stdClass();
if ($result = mysqli_query($con, $sql)) {
  while ($row = mysqli_fetch_row($result)) {
     $data -> value= $row[0];
  }
  $rowcount=mysqli_num_rows($result);
  if($rowcount != 0){
      $data->response='KO';
  }else{
    $sql = "INSERT INTO User (name, surnames, email, password) values('$n','$s','$e','$p')";
    $result = mysqli_query($con, $sql);
    
   
    $affected = mysqli_affected_rows($con);
    if($affected > 0){
        $data->value= mysqli_insert_id($con);
        $data->response='OK';
    } else {
        $data->response='KO';
    }
  }
}
mysqli_close($con);
echo json_encode($data);
?>