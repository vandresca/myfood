<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

// Creamos la conexión ala base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}


//Obtenemos el parametro GET de la url correspondiente al id de usuario
$user =$_GET['u'];


//Creamos la sql para obtener el correo del usuario
$sql = "SELECT `email` FROM `User` WHERE idUser = $user";


//Creamos un objeto para almacenar el estado de la respuesta tras la ejecución
//de la sql
$data = new stdClass();


//Si existen resultados recorremos los resultados y obtenemos el valor del
//correo electronico almacenandolo en el atributo value del objeto data
if ($result = mysqli_query($con, $sql)) {
  
  while ($row = mysqli_fetch_row($result)) {
      $data->value = $row[0];
  }
}


//Obtenemos el número de resultados de la consulta
$rowcount=mysqli_num_rows($result);

  //Si el número de resultados de la consulta es menor que 1 mostramos una respuesta erronea
  if($rowcount < 1){
    $data->response='KO';

  //En caso contrario mostramos una respuesta correcta  
  }else{
    $data->response ='OK';  
  }
  
//Cerramos la conexión a la base de datos  
mysqli_close($con);

//Serializamos el objeto $data con la respuesta a json
echo json_encode($data);
?>