<?php


//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 



// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$sql = "SELECT id_pantry, name, quantity, id_quantity_unit, price FROM Pantry where idUser=".$_GET['u'];
$con -> query('SET NAMES utf8');

$data= new stdClass();
$products = array();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  
  while ($row = mysqli_fetch_row($result)) {
    $product = new stdClass();
    $product->id = $row[0];
    $product->name = $row[1];
    $product->quantity = $row[2];
    $product->quantityUnit = $row[3];
    $product->price = $row[4];
    array_push($products, $product);
    
    $data->response="OK";
  }
  $data->products= $products;
  $rowcount=mysqli_num_rows($result);
  if($rowcount == 0){
    $data->response='KO';
  }
  mysqli_free_result($result);
}

mysqli_close($con);
echo json_encode($data);

?>