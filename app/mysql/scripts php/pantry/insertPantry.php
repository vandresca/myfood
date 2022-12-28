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

//pasamos el nombre a minusculas y sin espacios
$pal = strtolower(trim($_GET['n']));
//Realizamos algunos ajustes  para que encuentre alimentos
$pal = str_replace("fideo", "fideos", $pal);
$pal = str_replace("edulcorante", "azucar blanco", $pal);
$pal = str_replace(" mesa ", " ", $pal);

//Separamos el nombre por espacios
$arr = explode(" ",$pal);

//Creamos la subselect para que detecte alimentos que contengan las palabras del nombre
$select = "(";
$subselect = "";
$i=0;

//Si solo es una palabra
if(count($arr) == 1){
    $select .= "p".$i;
    $subselect .= "case when instr(lower(name), lower('$p'))>0 then 1 else 0 end as p$i";

//Si es más de una palabra
}else{
    //Recorremos todas las palabras
    foreach($arr as $key=>$value){
        if($i== 0){
            $select .= "p".$i;
            $subselect .= "case when instr(lower(name), lower('$value '))>0 then 1 else 0 end as p$i";
            $i++;
        } else{
            $select .= "+p".$i;
            $subselect .= ", case when instr(lower(name), lower('$value'))>0 then 1 else 0 end as p$i";
            $i++;
            $select .= "+p".$i;
            $subselect .= ", case when instr(lower(name), lower(' $value '))>0 then 1 else 0 end as p$i";
            $i++;
            $select .= "+p".$i;
            $subselect .= ", case when instr(lower(name), lower(' $value'))>0 then 1 else 0 end as p$i";
            $i++;
        }
    }
}

$select .= ")";

//Montamos la select completa
$sql ="SELECT lower(name), $select as p3, id_food FROM";
$sql .= " (SELECT id_food, name, $subselect FROM `Food`) as t1 having p3 > 0";
$con -> query('SET NAMES utf8');

//Creamos 4 arrays
//array_a => Almacena los nombres de los alimentos
//array_b => Almacena el peso de las coincidencias de palabras
//array_c => Almacena los id_food
$arr_a = array();
$arr_b = array();
$arr_c = array();

//Creamos un objeto data para la respuesta
$data= new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {
  //Obtenemos las filas del resultado
  while ($row = mysqli_fetch_row($result)) {
    //Asignamos los arrays
    array_push($arr_a,$row[0]);
    array_push($arr_b,$row[1]);
    array_push($arr_c,$row[2]);
    //Indicamos que la respuesta es correcta
    $data->response = "OK";
  }
  //Obtenemos el nùmero de filas resultantes
  $rowcount=mysqli_num_rows($result);
  //Si el número es inferior a 1 devolvemos un KO
  if($rowcount < 1){
    $data->response='KO';
  }
  //Liberamos recursos
  mysqli_free_result($result);
}

$max=0;
$return=0;
$cont=0;

//Array que almacena la clave con el valor máximo de coincidencias
//de palabras
$select = array();

//Si solo existe una palabra en el nombre cogemos el elemento del array
//que tiene solo una palabra
if(count($arr)==1){
    foreach($arr_a as $key=>$value){
        $explode = explode(' ', $value);
        if(count($explode)==1) $return = $key;
    }
//En caso de que exista más de una palabra
}else{
    //Recorremos las coincidencias de palabras y cada vez que el valor
    //sea mayor al maximo almacenado almacenamos en el array select la clave
    //con la coincidencia máxima de palabras
    foreach($arr_b as $key=>$value){
        if($value == $max){
            array_push($select,$key);
            $cont++;
        }
        if($value>$max){
            $max = $value;
            $return=$key;
            $cont=0;
            $select = array();
            array_push($select,$key);
        } 
        
    }
}
//Inicializamos idFood a 0
$idFood = "0";

//Si existe más de una palabra
if($i>0){
    //Recorremos el array de claves con el máximo de coincidencias
    foreach($select as $value){
        //Devolvemos la calve cuyo valor empiece por la palabra inicial más espacio
        if(str_starts_with($arr_a[$value], $arr[0]." ")){
            $return=$value;
        }
    }
    //Si la clave devuelta es diferente a 0 devolvemos el idFood correspondiente
    if($return != 0) $idFood = $arr_c[$return];
}else{
    //Si existe una única palabra devolvemos el idFodd correspondiente
    $idFood = $arr_c[$return];
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