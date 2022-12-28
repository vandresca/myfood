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

//Creamos la select para devolver la lista de productos de despensa de un usuario
$sql = "SELECT id_pantry, name, quantity, id_quantity_unit, price FROM Pantry where idUser=".$_GET['u'];
$con -> query('SET NAMES utf8');

//Creamos un objeto data para devolver la respuesta
$data= new stdClass();

//Creamos un array de productos
$products = array();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos cada una de las filas del resultado
  while ($row = mysqli_fetch_row($result)) {
    //Creamos un objeto producto
    $product = new stdClass();
    //Asignamos cada uno de los atributos del producto
    $product->id = $row[0];
    $product->name = $row[1];
    $product->quantity = $row[2];
    $product->quantityUnit = $row[3];
    $product->price = $row[4];
    //Añadimos el producto al array de productos
    array_push($products, $product);
    //Indicamos que la respuesta es correcta
    $data->response="OK";
  }
  //Asignamos al objeto data de respuesta el array de productos
  $data->products= $products;

  //Obtenemos el numero de filas del resultado
  $rowcount=mysqli_num_rows($result);

  //Si el numero de filas es < 1 devolvemos un KO
  if($rowcount < 1){
    $data->response='KO';
  }
  //Liberamos rescursos
  mysqli_free_result($result);
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json
echo json_encode($data);
?>