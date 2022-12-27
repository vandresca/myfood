<?php


//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 
 

if (!function_exists('str_starts_with')) {
  function str_starts_with($str, $start) {
    return (@substr_compare($str, $start, 0, strlen($start))==0);
  }
}

// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$sql ="SELECT ip.src, pp.code_bar, pp.name, pp.quantity, pp.id_quantity_unit, pp.id_place, pp.weight, pp.price, pp.expired_date, pp.preference_date, pp.is_notified_expired, pp.brand, pp.id_food, pp.id_pantry FROM `Pantry` pp left join ImageProduct ip on pp.id_pantry=ip.id_product WHERE pp.id_pantry =".$_GET['id'];
$con -> query('SET NAMES utf8');

$data = new stdClass();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  while ($row = mysqli_fetch_row($result)) {
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
    $data->response = "OK";
  }
  $rowcount=mysqli_num_rows($result);
  if($rowcount < 1){
    $data->response='KO';
  }
  mysqli_free_result($result);
}else{
    $data->response = "KO";
}
mysqli_close($con);
echo json_encode($data);
?>

