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

$sql ="SELECT id_recipe, portions, GROUP_CONCAT(title) as title, group_concat(ingredients) as ingredients, group_concat(directions) as directions FROM ";
$sql .="(SELECT r.id_recipe, r.portions, case when rl.itemType='title' then rl.translation end title, ";
$sql .="case when rl.itemType='ingredients' then rl.translation end ingredients, ";
$sql .="case when rl.itemType='directions' then rl.translation end directions ";
$sql .="FROM RecipeLanguage rl, Recipe r WHERE rl.id_recipe = r.id_recipe  and rl.id_recipe=".$_GET['r']." and rl.language=".$_GET['l'].") t1";

if($_GET['l']==1){
    $con -> query('SET NAMES utf8');
}

$data = new stdClass();
$arr = array();
$arr_res = "";
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  while ($row = mysqli_fetch_row($result)) {
    $data->title=$row[0];
    $data->portions=$row[1];
    $data->title = $row[2];
    $data->ingredients=$row[3];
    $data->directions=$row[4];
  }
  $affected = mysqli_affected_rows($con);
    if($affected == 0){
        $data->response = "KO";
    }else{
        $data->response = "OK";
    }
}

mysqli_close($con);
echo json_encode($data);
?>

