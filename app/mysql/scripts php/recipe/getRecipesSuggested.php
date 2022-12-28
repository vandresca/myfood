<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

//Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión con la base de datos y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para obtener la lista de productos despensa del usuario
$sql ="SELECT f.name FROM Pantry p, Food f where f.id_food=p.id_food and p.idUser=".$_GET['u'];
if($_GET['l']==1){
    $con -> query('SET NAMES utf8');
}


$i = 0;
$words = "";

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas del resultado
  while ($row = mysqli_fetch_row($result)) {
      //Quitamos los espacios por delante y por detras del nombre del producto despensa
      $value=trim($row[0]);
      //Por cada producto añadimos una sentencia like para incorporar el producto y restringir
      //más la posterior busqueda de recetas.
      if($i == 0){
        $words .= " rl.translation like '%$value%' ";
      }else{
        $words .= "or rl.translation like '%$value%' ";
      }
      $i++;
  }
}

//Creamos la sql para seleccionar las recetas sugeridas con los productos de que disponemos
$sql = "SELECT rl.id_recipe FROM RecipeLanguage rl WHERE rl.language=1 and rl.itemType='ingredients' and (".$words.")";

$ids="";
$cont=0;

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas del resultado con los ids de las recetas
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

//Creamos las sql para obtener los ids y titulos de las recetas a partir de sus ids
//en el idioma adecuado
$sql = "SELECT id_recipe, translation FROM RecipeLanguage WHERE itemType='title' and language=".$_GET['l']." and id_recipe in ".$ids;
if($_GET['l']==1){
    $con -> query('SET NAMES utf8');
}

//Creamos un objeto data para almacenar la respuesta
$data= new stdClass();

//Creamos un array de recetas
$recipes = array();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas del resultado
  while ($row = mysqli_fetch_row($result)) {
    //Creamos un objeto receta y almacenamos el id y el titulo de la receta
    $recipe = new stdClass();
    $recipe->id = $row[0];
    $recipe->title = $row[1];
    //Añadimos la receta al array de recetas
    array_push($recipes, $recipe);
  }
  //Obtenemos el número de filas afectadas
  $affected = mysqli_affected_rows($con);
  //Si el número de filas afectadas es inferior a   1 devolvemos un KO
  if($affected < 1){
    $data-> response = "KO";
  //En caso contrario un OK
  }else{
    $data-> response = "OK";
  }
  //Liberamos recursos
  mysqli_free_result($result);
}

//Asignamos el array de recetas al objeto de respuesta data
$data->recipes =$recipes;

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);

?>

