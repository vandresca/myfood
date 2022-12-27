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


//Obtenemos los parametros GET de la url correspondientes al id de usuario
//y el tipo de caducidad a mostrar
$user = $_GET['u'];
$expiration = $_GET['e'];

//Creamos la sql para
$sql = "SELECT idUser, name, days, price, expiration FROM (SELECT idUser, name, DATEDIFF(str_to_date(expired_date, '%e/%c/%Y'), CURRENT_TIMESTAMP) as days, price, case ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< UNIX_TIMESTAMP(CURRENT_TIMESTAMP) then 'expired' ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> UNIX_TIMESTAMP(CURRENT_TIMESTAMP) AND UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY))then '0to10days' ";
$sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY)) then 'more10days' ";
$sql .="end as expiration FROM `Pantry`) as t1 WHERE t1.idUser = $user";

if($expiration!="All") $sql .= " and t1.expiration='$expiration'";
$con -> query('SET NAMES utf8');


//Creamos un objeto para almacenar el estado de la respuesta de la sql
$data= new stdClass();

//Creamos un array para almacenar los productos resultantes
$products = array();
if ($result = mysqli_query($con, $sql)) {

  //Recorremos cada una de las filas resultantes
  while ($row = mysqli_fetch_row($result)) {

    //Creamos un producto por cada fila
    $product = new stdClass();

    //Asignamos cada uno de los atributos del producto
    $product->name = $row[1];
    $product->days = $row[2];
    $product->price = $row[3];
    $product->expiration = $row[4];

    //Añadimos el producto al array de productos
    array_push($products, $product);

    //Devolvemos en el objeto data respuesta correcta
    $data->response="OK";
  }

  //Liberamos recursos
  mysqli_free_result($result);
}

//Asignamos el array de productos al objeto data
$data->products =$products;

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto serializado como json
echo json_encode($data);
?>