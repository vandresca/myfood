<?php

include('../utilities.php');

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json');


if (!function_exists('str_starts_with')) {
  function str_starts_with($str, $start) {
    return (@substr_compare($str, $start, 0, strlen($start))==0);
  }
}

//Inicializamos variables
$cb="";
$n="";
$q=0;
$qu="";
$p="";
$w=0;
$pr=0;
$ed="";
$pd="";
$b="";
$im="";
$u = 0;

if(isset($_POST['cb']) && $_POST['cb']!="")  $cb=$_POST['cb'];
if(isset($_POST['n'])  && $_POST['n']!="")  $n=$_POST['n'];
if(isset($_POST['q'])  && $_POST['q']!="")  $q=$_POST['q'];
if(isset($_POST['qu']) && $_POST['qu']!="")  $qu=$_POST['qu'];
if(isset($_POST['p'])  && $_POST['p']!="")  $p=$_POST['p'];
if(isset($_POST['w'])  && $_POST['w']!="")  $w=$_POST['w'];
if(isset($_POST['pr']) && $_POST['pr']!="")  $pr=$_POST['pr'];
if(isset($_POST['ed']) && $_POST['ed']!="")  $ed=$_POST['ed'];
if(isset($_POST['pd']) && $_POST['pd']!="")  $pd=$_POST['pd'];
if(isset($_POST['b'])  && $_POST['b']!="")  $b=$_POST['b'];
if(isset($_POST['i'])  && $_POST['i']!="")  $im=$_POST['i'];
if(isset($_POST['u'])  && $_POST['u']!="")  $u=$_POST['u'];


// Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$pal = toSingular(trim($n));
$pal = str_replace("fideo", "fideos", $pal);
$pal = str_replace("edulcorante", "azucar blanco", $pal);
$pal = str_replace("Edulcorante", "Azucar blanco", $pal);


// Consulta para obtener registros de la tabla "Food" que tengan las mismas palabras que el nombre "$pal" y que se encuentren al principio de la frase
$sql = "SELECT * FROM Food WHERE MATCH(name) AGAINST ('$pal' IN BOOLEAN MODE) AND name LIKE '$pal %' ORDER BY MATCH (name) AGAINST ('$pal') DESC";

//Ejecutamos la sql
$result = mysqli_query($con, $sql);

//Inicializamos idFood a 0
$idFood = 0;

// Mostramos los registros obtenidos
if (mysqli_num_rows($result) > 0) {
  $row = mysqli_fetch_assoc($result);
  $idFood = $row["id_food"];
}

if($idFood == 0){
    $pal = toPlural($pal);
    // Consulta para obtener registros de la tabla "Food" que tengan las mismas palabras que el nombre "$pal" y que se encuentren al principio de la frase
    $sql = "SELECT * FROM Food WHERE MATCH(name) AGAINST ('$pal' IN BOOLEAN MODE) AND name LIKE '$pal %' ORDER BY MATCH (name) AGAINST ('$pal') DESC";

    //Ejecutamos la sql
    $result = mysqli_query($con, $sql);

    // Mostramos los registros obtenidos
    if (mysqli_num_rows($result) > 0) {
      $row = mysqli_fetch_assoc($result);
      $idFood = $row["id_food"];
    }
}

//Si el nombre y la fecha de vencimiento existen y no son vacias insertamos los datos del producto
//de despensa.
if(trim($n) != "" && trim($ed) !=""){

    $sql = "INSERT INTO `Pantry`(`code_bar`, `name`, `quantity`, `id_quantity_unit`, `id_place`, `weight`, `price`, `expired_date`, `preference_date`, `is_notified_expired`, `brand`, `idUser`, `id_food`) ";
    $sql .=" values('$cb','$n',$q,'$qu','$p',$w,$pr,'$ed','$pd',false,'$b',$u, $idFood)";
    
    //Ejecutamos la sql
    $result = mysqli_query($con, $sql);

    //Obtenemos el número de filas afectadas
    $affected = mysqli_affected_rows($con);

    //Si el número de filas afectadas es inferior a 1 devolvemos un KO
    if($affected < 1){
        $data-> response = "KO";

   //En caso contrario devolvemos un OK y obtenemos el id generado en la inserción
   //para insertar la imagen en la tabla ImageProduct
    }else{
        $data-> response = "OK";
        $row_inserted_id = mysqli_insert_id($con);
        $sql = "INSERT INTO ImageProduct(id_product, src) values ($row_inserted_id,'$im')";
        
        //Ejecutamos la sql de inserción en la tabla ImageProduct
        $result = mysqli_query($con, $sql);

        //Obtenemos el número de filas afectadas
        $affected = mysqli_affected_rows($con);

        //Si el número de filas afectadas es inferior a 1 devolvemos un KO y un valor con el
        //número de fila insertada 0
        if($affected < 1){
            $data-> response = "KO";
            $data-> value = 0;
        //en caso contrario devolvemos un OK y el número de fila insertada
        }else{
            $data-> response = "OK";
            $data-> value = $row_inserted_id;
            
        }
    }

    //Cerramos la conexión con la base de datos
    mysqli_close($con);
}else{
    //Si el nombre o la fecha de vencimiento no existen o son vacios devolvemos un KO
    $data -> response = "KO";
}

//Devolvemos el objeto data serializado como json.
echo json_encode($data);
?>