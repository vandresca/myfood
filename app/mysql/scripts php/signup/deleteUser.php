<?php


//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 



// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

$id = $_GET['id'];

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$sql = "DELETE FROM User where idUser=$id";
$con -> query('SET NAMES utf8');

$data= new stdClass();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  $affectedrows=mysqli_affected_rows($con);
  if($affectedrows > 0){
        $data -> response = 'OK';
  }else{
        $data ->  response = 'KO';
  }
}


mysqli_close($con);
echo json_encode($data);
?>