<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 


// Creamos una conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos una sql  para obtener la lista de productos de compra del usuario
$sql = "SELECT id_shop, name, quantity, id_quantity_unit FROM Shop WHERE idUser=".$_GET['u'];
$con -> query('SET NAMES utf8');

//Creamos un objeto data para almacenar la respuesta
$data= new stdClass();

//Creamos un array de productos
$products = array();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas del resultado
  while ($row = mysqli_fetch_row($result)) {
    //Creamos un objeto producto y le asignamos el id, nombre, cantidad y unidad de cantidad
    $product = new stdClass();
    $product->id = $row[0];
    $product->name = utf8_decode($row[1]);
    $product->quantity = $row[2];
    $product->quantityUnit = $row[3];
    //Asignamos el prodducto al array de productos
    array_push($products, $product);
    //Devolvemos una respuesta OK
    $data->response ="OK";
  }
  //Asignamos el array de productos al objeto de respuesta data
  $data->products= $products;

  //Obtenemos el número de filas devueltas
  $rowcount=mysqli_num_rows($result);

  //Si el núemro de filas devueltas es menor de 1 devolvemos un KO
  if($rowcount < 1){
    $data->response='KO';
  }
  //Liberamos recursos
  mysqli_free_result($result);
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>