<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 


// Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Obtenemos el id del alimento
$id = $_GET['id'];
if($id == 0) $id= 5001;

//Obtenemos el tipo de grupo de nutriente y dependiendo de cual sea
//obteenemos esto de la tabla correspondiente
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

//Creamos un objeto data para devolver la respuesta
$data= new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos cada una de las filas de los resultados
  while ($row = mysqli_fetch_assoc($result)){

     //Creamos un array para guardar los nutrientes
     $nutrients = array();

     //Por cada fila obtenemos el nombre de la columna y su valor
     foreach($row as $column => $value){
        //Si el nombre de la columna es indice, id_food o name no lo almacenamos
        if($column != "indice" && $column != "id_food" && $column !="name"){
            //Creamos un objeto nutriente
            $nutrient = new StdClass();
            //Asignamos al nutriente el nombre de columna y su valor
            $nutrient->column=$column;
            $nutrient->value=$value;
            //Añadimos el nutriente al array de nutrientes
            array_push($nutrients, $nutrient);
            //Indicamos que la respuesta es OK
            $data->response='OK';
        }
     } 
  }
  //Asignamos los nutrientes al objeto data de respuesta
  $data -> food_nutrients = $nutrients;
  //Obtenemos el numero de filas del resultado
  $rowcount=mysqli_num_rows($result);
  //Si el numero de filas es 0 inferiro devolvemos un KO
  if($rowcount < 1){
    $data->response='KO';
  }
  //Liberamos recursos
  mysqli_free_result($result);
}
//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json
echo json_encode($data);
?>