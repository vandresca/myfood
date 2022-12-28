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

//Inicializamos las variables
$n = "";
$s = "";
$e = "";
$p = "";

if(isset($_GET['n'])) $n = $_GET['n'];
if(isset($_GET['s'])) $s = $_GET['s'];
if(isset($_GET['e'])) $e = $_GET['e'];
if(isset($_GET['p'])) $p = $_GET['p'];

//Creamos la sql para verificar si el correo existe y por lo tanto el usuario
$sql = "SELECT idUser FROM User where email='$e'";
$con -> query('SET NAMES utf8');

//Creamos un objeto data para almacenar la respuesta
$data = new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos el número de filas del resultado
  while ($row = mysqli_fetch_row($result)) {
     //Obtenemos el id de usuario y lo asignamos al atributo value
     $data -> value= $row[0];
  }
  //Obtenemos el número de filas
  $rowcount=mysqli_num_rows($result);

  //Si el número de filas es diferente a devolvemos un KO
  if($rowcount != 0){
      $data->response='KO';

  //En caso contrario devolvemos insertamos el usuario y devolvemos un OK
  }else{

    //Creamos la sql para insertar el usuario
    $sql = "INSERT INTO User (name, surnames, email, password) values('$n','$s','$e','$p')";

    //Ejecutamos la sql
    $result = mysqli_query($con, $sql);
    
    //Obtenemos el número de filas afectadas
    $affected = mysqli_affected_rows($con);

    //Si el número de filas afectadas es superior a 0 devolvemos un OK y el id de inserción
    if($affected > 0){
        $data->value= mysqli_insert_id($con);
        $data->response='OK';
    //En caso contrario devolvemos un KO y un valor vacio de id de inserción
    } else {
        $data->response='KO';
        $data->value= '';
    }
  }
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>