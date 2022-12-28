<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

//Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

//Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para recuperar los atributos de la receta
$sql ="SELECT id_recipe, portions, GROUP_CONCAT(title) as title, group_concat(ingredients) as ingredients, group_concat(directions) as directions FROM ";
$sql .="(SELECT r.id_recipe, r.portions, case when rl.itemType='title' then rl.translation end title, ";
$sql .="case when rl.itemType='ingredients' then rl.translation end ingredients, ";
$sql .="case when rl.itemType='directions' then rl.translation end directions ";
$sql .="FROM RecipeLanguage rl, Recipe r WHERE rl.id_recipe = r.id_recipe  and rl.id_recipe=".$_GET['r']." and rl.language=".$_GET['l'].") t1";
if($_GET['l']==1){
    $con -> query('SET NAMES utf8');
}

//Creamos el objeto deata que almacenara la respuesta
$data = new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas de los resultados
  while ($row = mysqli_fetch_row($result)) {
    //Asignamos los atributos de la receta al objeto data
    $data->title=$row[0];
    $data->portions=$row[1];
    $data->title = $row[2];
    $data->ingredients=$row[3];
    $data->directions=$row[4];
  }
  //Obtenemos el número de filas afectadas
  $affected = mysqli_affected_rows($con);
    //Si el número de filas afectadas es inferior a 1 devolvemos KO
    if($affected < 1){
        $data->response = "KO";
    //En caso contrario un OK
    }else{
        $data->response = "OK";
    }
}
//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>

