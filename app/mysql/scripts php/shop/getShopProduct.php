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

//Creamos la sql para obtener los datos del producto de compra
$sql ="SELECT name, quantity, id_quantity_unit FROM `Shop` WHERE id_shop =".$_GET['id'];

//Creamos un objeto data para la respuesta
$data = new stdClass();

//Por defecto devolvemos un KO en el objeto respuesta
$data->response = "KO";

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas del resultado
  while ($row = mysqli_fetch_row($result)) {

    //Asignamos los atributos del producto de compra al objeto data
    $data->name= $row[0];
    $data->quantity=$row[1];
    $data->quantityUnit=$row[2];

    //Si todo ha ido bien devolvemos un OK en la respuesta
    $data->response = "OK";
  }

  //Liberamos recursos
  mysqli_free_result($result);
}else{
    //Si la sql no se ha ejecutado bien devolvemos un KO en la respuesta
    $data->response = "KO";
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>

