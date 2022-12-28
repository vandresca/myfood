<?php


i//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 


//Incializamos las variables
$cb="";
$n="";
$q=0;
$qu="";
$p="";
$w=0;
$pr=0;
$ed="";
$pd="";
$b="";
$i="";

if(isset($_POST['cb']) && $_POST['cb']!="")  $cb=$_POST['cb'];
if(isset($_POST['n'])  && $_POST['n']!="")  $n=$_POST['n'];
if(isset($_POST['q'])  && $_POST['q']!="")  $q=$_POST['q'];
if(isset($_POST['qu']) && $_POST['qu']!="")  $qu=$_POST['qu'];
if(isset($_POST['p'])  && $_POST['p']!="")  $p=$_POST['p'];
if(isset($_POST['w'])  && $_POST['w']!="")  $w=$_POST['w'];
if(isset($_POST['pr']) && $_POST['pr']!="")  $pr=$_POST['pr'];
if(isset($_POST['ed']) && $_POST['ed']!="")  $ed=$_POST['ed'];
if(isset($_POST['pd']) && $_POST['pd']!="")  $pd=$_POST['pd'];
if(isset($_POST['b'])  && $_POST['b']!="")  $b=$_POST['b'];
if(isset($_POST['i'])  && $_POST['i']!="")  $i=$_POST['i'];
$id = $_POST['id'];


// Cremos la conexión de base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexion y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos un objeto data para devolver la respuesta
$data = new stdClass();

//Creamos la sql para actualizar el producto de despensa.
$sql = "UPDATE `Pantry` SET `code_bar`='$cb', `name`='$n', `quantity`=$q, `id_quantity_unit`='$qu', `id_place`='$p', `weight`=$w, `price`=$pr, `expired_date`='$ed', `preference_date`='$pd', `brand`='$b' WHERE id_pantry= $id";

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Si la consulta se ha ejecutado correctamente devolvemos un OK
if ($result === TRUE) {
    $data->response="OK";

//En caso contrario un KO
} else {
    $data->response="KO";
}

//Creamos la sql para actualizar la imagen en la base de datos del producto
$sql = "UPDATE ImageProduct SET src='$i' WHERE id_product=$id";

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Si la consulta se ha ejecutado correctamente devolvemos un OK
if ($result === TRUE) {
    $data->response="OK";
//En caso contrario devolvemos un KO
} else {
    $data->response="KO";
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json
echo json_encode($data);

?>