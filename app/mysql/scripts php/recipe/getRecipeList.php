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


$sql = "SELECT id_recipe, translation FROM RecipeLanguage WHERE itemType='title' and language=".$_GET['l'];

if($_GET['l']==1){
    $con -> query('SET NAMES utf8');
}

$data= new stdClass();
$recipes = array();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  
  while ($row = mysqli_fetch_row($result)) {
    $recipe = new stdClass();
    $recipe->id = $row[0];
    $recipe->title = $row[1];
    array_push($recipes, $recipe);
  }
  $affected = mysqli_affected_rows($con);
  if($affected == 0){
    $data-> response = "KO";
  }else{
    $data-> response = "OK";
  }
  mysqli_free_result($result);
}

$data->recipes =$recipes;

mysqli_close($con);
echo json_encode($data);
?>