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

//Creamos la sql para recuperar los atributos de un producto despensa dado su id
$sql ="SELECT ip.src, pp.code_bar, pp.name, pp.quantity, pp.id_quantity_unit, pp.id_place, pp.weight, pp.price, pp.expired_date, pp.preference_date, pp.is_notified_expired, pp.brand, pp.id_food, pp.id_pantry FROM `Pantry` pp left join ImageProduct ip on pp.id_pantry=ip.id_product WHERE pp.id_pantry =".$_GET['id'];
$con -> query('SET NAMES utf8');

//Creamos un objeto data para almacenar la respuesta
$data = new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos cada una de las filas del resultado
  while ($row = mysqli_fetch_row($result)) {
    //Asignamos al objeto data los atributos del producto
    $data->id_pantry =$row[13];
    $data->id_food =$row[12];  
    $data->image= $row[0];
    $data->barcode=$row[1];
    $data->name= $row[2];
    $data->quantity=$row[3];
    $data->quantityUnit=$row[4];
    $data->place=$row[5];
    $data->weight=$row[6];
    $data->price=$row[7];
    $data->expiredDate=$row[8];
    $data->preferenceDate=$row[9];
    $data->brand= utf8_decode($row[11]);
    //Indicamos que la respuesta es OK
    $data->response = "OK";
  }
  //Obtenemos el número de filas de la ejecución de la sql
  $rowcount=mysqli_num_rows($result);
  //Si es inferior a 1 devolvemos un KO
  if($rowcount < 1){
    $data->response='KO';
  }
  //Liberamos resultados
  mysqli_free_result($result);
}else{
    //Si se producte un error en la ejecución de la sql devolvemos KO
    $data->response = "KO";
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>

