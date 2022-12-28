<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 


//Incializamos las variables
$n="";
$q=0;
$qu="";

if(isset($_GET['n']) && $_GET['n']!="")  $n=$_GET['n'];
if(isset($_GET['q'])  && $_GET['q']!="")  $q=$_GET['q'];
if(isset($_GET['qu'])  && $_GET['qu']!="")  $qu=$_GET['qu'];
$u = $_GET['u'];


// Cremos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para insertar un producto de compra
$sql = "INSERT INTO `Shop`(`name`, `quantity`, `id_quantity_unit`, `idUser`) ";
$sql .=" values('$n',$q,'$qu',$u)";

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Creamos un objeto data para almacenar la respuesta
$data = new stdClass();

//Obtenemos el id de inserción tras insertar el producto
$row_inserted_id = mysqli_insert_id($con);

//Obtenemos el número de filas afectadas tras la ejecución de la sql
$affected = mysqli_affected_rows($con);

//Si el número de filas afectadas es inferior a 1 devolvemos un KO y un valor
//con el id de inserción 0
if($affected < 1){
    $data-> response = "KO";
    $data-> value = 0;
//En caso contrario devolvemos un OK y en el valor el id de inserción
}else{
    $data-> response = "OK";
    $data-> value = $row_inserted_id;
}

//Cerramos la conexión con la base de datos
mysqli_close($con);

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>