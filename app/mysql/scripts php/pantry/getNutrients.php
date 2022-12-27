<?php

include('../translate.php');

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

$lang = 5;
if(isset($_GET['l'])) $lang = $_GET['l'];


// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}


$sql = "SELECT name FROM NutrientGroup";
$con -> query('SET NAMES utf8');

$data= new stdClass();
$nutrients_aux = array();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  
  while ($row = mysqli_fetch_row($result)) {
    array_push($nutrients_aux, $row[0]);
    $data->response='OK';
  }
  $rowcount=mysqli_num_rows($result);
  if($rowcount == 0){
    $data->response='KO';
  }
  mysqli_free_result($result);
}

if($lang==1) $toLang = "es";
if($lang==2) $toLang = "en";
if($lang==3) $toLang = "fr";
if($lang==4) $toLang = "de";
if($lang==5) $toLang = "ca";

$nutrients = array();
foreach($nutrients_aux as $nutrient){
    $translation = ucfirst(translate($nutrient,"es", $toLang));
    array_push($nutrients, $translation);   
}

$data->nutrients= $nutrients;
mysqli_close($con);
echo json_encode($data);
?>