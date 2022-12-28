<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json');

// Creamos una conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión con la base de datos y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}
//Creamos un objeto data para almacenar la respuesta
$data = new stdClass();

//Creamos una sql para eliminar el producto de compra a partir de su id
$sql ="DELETE FROM Shop WHERE id_shop =".$_GET['id'];
$con -> query('SET NAMES utf8');

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Obtenemos el número de filas afectadas
$affected = mysqli_affected_rows($con);

//Si el núemro de filas afectadas es inferior a 1 devolvemos un KO
if($affected < 1){
    $data-> response = "KO";
//En caso contrario devolvemos un OK
}else{
    $data-> response = "OK";
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializaddo como json.
echo json_encode($data);
?>