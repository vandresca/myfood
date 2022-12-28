<?php


//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

//Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

//Obtenemos el id
$id = $_GET['id'];

// Chequeamo la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para eliminar el usuario a partir de su id
$sql = "DELETE FROM User where idUser=$id";
$con -> query('SET NAMES utf8');

//Creamos un objeto data para alamcenar la respuesta
$data= new stdClass();

//Ejectuamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos el número de filas afectadas
  $affectedrows=mysqli_affected_rows($con);

  //Si el núemro de filas afectadas es superior a 0 devolvemos un OK
  if($affectedrows > 0){
        $data -> response = 'OK';
  //En caso contrario un KO
  }else{
        $data ->  response = 'KO';
  }
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>