<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

//Creamos una conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para recuperar la lista de todas las recetas del sistema
$sql = "SELECT id_recipe, translation FROM RecipeLanguage WHERE itemType='title' and language=".$_GET['l'];
if($_GET['l']==1){
    $con -> query('SET NAMES utf8');
}

//Creamos el objeto data para la respuesta
$data= new stdClass();

//Creamos un array recetas
$recipes = array();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas del resultado
  while ($row = mysqli_fetch_row($result)) {
    //Creamos un objeto receta y asignamos el id y su titulo
    $recipe = new stdClass();
    $recipe->id = $row[0];
    $recipe->title = $row[1];
    //Añadimos la receta a la lista de recetas
    array_push($recipes, $recipe);
  }
  //Obtenemos las filas afectadas por la sql
  $affected = mysqli_affected_rows($con);

  //Si el número de filas afectadas es inferior a 1 devolvemos un KO
  if($affected < 1){
    $data-> response = "KO";
  //En caso contrario devolvemos un OK
  }else{
    $data-> response = "OK";
  }
  //Liberamos recursos
  mysqli_free_result($result);
}
//Asignamos las recetas al objeto de respuesta data
$data->recipes =$recipes;

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>