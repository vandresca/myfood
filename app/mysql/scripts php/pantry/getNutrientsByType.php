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

$id = $_GET['id'];

if($id == 0) $id= 5001;

if($_GET['t']=="1") {
    $sql = "SELECT * FROM GenericNutrient WHERE id_food=$id";
}
if($_GET['t']=="2") {
    $sql = "SELECT * FROM VitaminNutrient WHERE id_food=$id";
}
if($_GET['t']=="3") {
    $sql = "SELECT * FROM MineralNutrient WHERE id_food=$id";
}
if($_GET['t']=="4") {
    $sql = "SELECT * FROM AcidGrassNutrient WHERE id_food=$id";
}


$con -> query('SET NAMES utf8');

$data= new stdClass();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  while ($row = mysqli_fetch_assoc($result)){
     $nutrients = array();
     foreach($row as $column => $value){
        if($column != "indice" && $column != "id_food" && $column !="name"){
            $nutrient = new StdClass();
            $nutrient->column=$column;
            $nutrient->value=$value;
            array_push($nutrients, $nutrient);
            $data->response='OK';
        }
     } 
  }
  
  $data -> food_nutrients = $nutrients;
  
  $rowcount=mysqli_num_rows($result);
  if($rowcount == 0){
    $data->response='KO';
  }
  mysqli_free_result($result);
}

mysqli_close($con);
echo json_encode($data);
?>