<?php

//Incluimos el script translate para traducir los tipos de grupos de nutrientes
include('../translate.php');

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

//Obtenemos el idioma
$lang = 5;
if(isset($_GET['l'])) $lang = $_GET['l'];


// Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para obtener los tipos de grupo de nutrientes
$sql = "SELECT name FROM NutrientGroup";
$con -> query('SET NAMES utf8');

//Creamos un objeto data para devolver la respuesta
$data= new stdClass();

//Creamos un array auxiliar para guardar los grupos de nutrientes
$nutrients_aux = array();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas de los resultados
  while ($row = mysqli_fetch_row($result)) {
    //Añadimos cada uno de los tipos al array de nutrientes
    array_push($nutrients_aux, $row[0]);
    //Indicamos que la respuesta ha sido correcta
    $data->response='OK';
  }

  //Obtenemos el número de filas resultantes
  $rowcount=mysqli_num_rows($result);

  //Si el número de filas es inferior a 1 devolvemos KO
  if($rowcount < 1){
    $data->response='KO';
  }
  //Liberamos recursos
  mysqli_free_result($result);
}

//Traducimos los resultados al idioma indicado
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

//Asignamos los nutrientes al objeto resultados
$data->nutrients= $nutrients;

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>