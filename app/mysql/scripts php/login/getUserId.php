<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

//Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

//Obtenemos el nombre y la contraseña por parametro get
$n='';
$p='';
if(isset($_GET['n'])) $n = $_GET['n'];
if(isset($_GET['p'])) $p = $_GET['p'];

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para verificar que el nombre y contraseña es correcto y poder hacer login
$sql = "SELECT * FROM User where name='$n' and password='$p'";
$con -> query('SET NAMES utf8');
$wrap = array();
$data= new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas resultantes
  while ($row = mysqli_fetch_row($result)) {
    //Por cada fila obtenemos el id de usuario
    $data -> idUser= $row[0];
  }
  //Obtenemos el numero de filas resultantes
  $rowcount=mysqli_num_rows($result);

  //Si la fila es menor a 1 devolvemos un KO y id de usuario vacio
  if($rowcount < 1){
    $data->response='KO';
    $data-> idUser ='';
  }else{
    //En caso contrario devolvemos un OK
    $data->response='OK';
  }

  //Liberamos recursos
  mysqli_free_result($result);
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json
echo json_encode($data);
?>