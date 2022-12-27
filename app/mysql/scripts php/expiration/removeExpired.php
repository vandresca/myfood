<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

// Creamos la conexión
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para eliminar todos los productos caducados que tiene el usuario
$sql ="DELETE FROM Pantry WHERE id_pantry in (SELECT id_pantry FROM (SELECT id_pantry, idUser, case ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< UNIX_TIMESTAMP(CURRENT_TIMESTAMP) then 'expired' ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> UNIX_TIMESTAMP(CURRENT_TIMESTAMP) AND UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY))then '0to10days' ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY)) then 'more10days' ";
$sql .="end as expiration FROM `Pantry`) as t1 WHERE t1.expiration = 'expired' and t1.idUser=".$_GET['u'].")";
$con -> query('SET NAMES utf8');

//Creamos un objeto data para devolver la respuesta
$data = new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {

  //Obtenemos el número de filas afectadas por la sql
  $affected = mysqli_affected_rows($con);

  //Si el número de filas afectadas es menor que 1 devolvemos una respuesta incorrecta
  //en caso contrario correcta
  if($affected < 1){
    $data-> response = "KO";
  }else{
    $data-> response = "OK";
  }
}

//Cerramos la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json
echo json_encode($data);
?>