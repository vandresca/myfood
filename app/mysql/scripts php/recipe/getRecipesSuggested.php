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

$sql ="SELECT f.name FROM Pantry p, Food f where f.id_food=p.id_food and p.idUser=".$_GET['u'];
if($_GET['l']==1){
    $con -> query('SET NAMES utf8');
}


$i = 0;
$words = "";
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields 
  while ($row = mysqli_fetch_row($result)) {
      $value=trim($row[0]);
      if($i == 0){
        $words .= " rl.translation like '%$value%' ";
      }else{
        $words .= "or rl.translation like '%$value%' ";
      }
      $i++;
  }
}

$sql = "SELECT rl.id_recipe FROM RecipeLanguage rl WHERE rl.language=1 and rl.itemType='ingredients' and (".$words.")";

$ids="";
$cont=0;
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  while ($row = mysqli_fetch_row($result)) {
      if($cont==0){
        $ids .= "(".$row[0];
      }else{
        $ids .= ",".$row[0];
      }
      $cont++;
  }
}

$ids .=")";

$sql = "SELECT id_recipe, translation FROM RecipeLanguage WHERE itemType='title' and language=".$_GET['l']." and id_recipe in ".$ids;

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

