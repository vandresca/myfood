<?php


i//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 



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


// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$data = new stdClass();
$sql = "UPDATE `Pantry` SET `code_bar`='$cb', `name`='$n', `quantity`=$q, `id_quantity_unit`='$qu', `id_place`='$p', `weight`=$w, `price`=$pr, `expired_date`='$ed', `preference_date`='$pd', `brand`='$b' WHERE id_pantry= $id";

$result = mysqli_query($con, $sql);
if ($result === TRUE) {
    $data->response="OK";
} else {
    $data->response="KO";
}

$sql = "UPDATE ImageProduct SET src='$i' WHERE id_product=$id";
$result = mysqli_query($con, $sql);
if ($result === TRUE) {
    $data->response="OK";
} else {
    $data->response="KO";
}

mysqli_close($con);
echo json_encode($data);

?>