<?php

//Cargamos el script email para manejar el envio de correo electronicos
include('../email.php');


//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si algo falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos un array users
$users = array();

//Creamos una sql para recuperar todos los usuarios del sistema
$sql = "SELECT idUser, name, surnames, email FROM User";

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Recorremos cada usuario y obtemos su id, nombre, apellidos y correo
while ($row = mysqli_fetch_row($result)) {
    $user = new stdClass();
    $user->id = $row[0];
    $user->name = $row[1];
    $user->surnames = $row[2];
    $user->email = $row[3];

    //Añadimos el usuario al array de usuarios
    array_push($users, $user);
}

//Recorremos el array de usuarios
foreach($users as $user){

    //Por cada usuario miramos los productos que tiene caducados y a punto de caducar (0 a 10 días)
    $sql = "SELECT name, days, expiration FROM (SELECT idUser, name, DATEDIFF(str_to_date(expired_date, '%e/%c/%Y'), CURRENT_TIMESTAMP) as days, price, case ";
    $sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< UNIX_TIMESTAMP(CURRENT_TIMESTAMP) then 'expired' ";
    $sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> UNIX_TIMESTAMP(CURRENT_TIMESTAMP) AND UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY))then '0to10days' ";
    $sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY)) then 'more10days' ";
    $sql .="end as expiration FROM `Pantry`) as t1 WHERE t1.idUser = ".$user->id." and (t1.expiration='expired' or t1.expiration='0to10days')";
    $con -> query('SET NAMES utf8');

    //Creamos un array products para almacenar los productos resultantes
    $products = array();

    //Ejecutamos la sql
    if ($result = mysqli_query($con, $sql)) {
      //Empezamos a montar el cuerpo del mensaje con un saludo al usuario por su nombre y apellidos
      $body = "Hi ".$user->name." ".$user->surnames."<br><br>";
      $body .= "These are the products expired and almost expired you have: <br><br>";

      //Recreamos el array products
      $products = array();

      //Recorremos cada una de las filas y obtenemos las caracteristicas del producto
      //(nombre, dias y caducidad)
      while ($row = mysqli_fetch_row($result)) {
        $product = new stdClass();
        $product->name = $row[0];
        $product->days = $row[1];
        $product->expiration = $row[2];
        //Añadimos el producto al array de productos
        array_push($products, $product);
      }

      // Seguimos montando el cuerpo del mensaje indicando los productos caducados
      $body .= "<div style='margin-left:20px;'><b>Expired:</b></div><br>";

      //Anotamos en el cuerpo de mensaje todos los productos caducados
      foreach($products as $product){
          if($product->expiration == 'expired'){
              $body .= "<div style='margin-left:40px;color:red;'>".$product->name.":  ".$product->days." days</div><br>";
          }
      }

      //Realizamos la misma operación pero para los casi caducados
      $body .= "<br><br>";
      $body .= "<div style='margin-left:20px;'><b>Almost Expired:</b></div><br>";
      foreach($products as $product){
          if($product->expiration == '0to10days'){
              $body .= "<div style='margin-left:40px;'>".$product->name.":  ".$product->days." days</div><br>";
          }
      }
      $body .= "<br><br>";

      //Enviamos el correo al usuario
      sendEmail("Expired and Almos Expired Products", $body, $user->email, $user->name);
      
    }
}

//Cerramos la conexión
mysqli_close($con);
?>