<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 
 

//Incializamos las variables
$n="";
$q=0;
$qu="";

if(isset($_GET['n'])  && $_GET['n']!="")  $n=$_GET['n'];
if(isset($_GET['q'])  && $_GET['q']!="")  $q=$_GET['q'];
if(isset($_GET['qu']) && $_GET['qu']!="")  $qu=$_GET['qu'];
$id =$_GET['id'];


// Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos un objeto data para almacenar la respuesta
$data = new stdClass();

//Creamos una sql para actualizar el producto de compra a partir de su id
$sql = "UPDATE `Shop` SET `name`='$n', `quantity`=$q, `id_quantity_unit`='$qu' WHERE id_shop = $id";

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Obtenemos el número de filas afectadas
$affected = mysqli_affected_rows($con);

//Si el número de filas afectadas es inferior a 0 devolvemos un KO
if($affected < 0){
    $data-> response = "KO";
//En caso contrario devolvemos  un OK
}else{
    $data-> response = "OK";
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json,
echo json_encode($data);
?>