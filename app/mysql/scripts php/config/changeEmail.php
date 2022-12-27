<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

// Creamos la conexión a la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Obtenemos el correo y el id de usuario
$email = $_GET['e'];
$user =$_GET['u'];

//Creamos la sql para actualizar el email del usuario
$sql = "UPDATE `User` SET `email`='$email' WHERE idUser = $user";

//Creamos un objeto para almacenar el estado de la respuesta
$data = new stdClass();

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Obtenemos el número de filas afectadas
$affected = mysqli_affected_rows($con);

//Si el número de filas afectadas es negativo mostramos una respuesta
//erronea
if($affected == -1){
    $data-> response = "KO";

//En caso contrario una respuesta correcta
}else{
    $data-> response = "OK";
}

//Cerramos la conexión de la base de datos
mysqli_close($con);

//Serializamos el objeto $data con la respuesta a json
echo json_encode($data);
?>