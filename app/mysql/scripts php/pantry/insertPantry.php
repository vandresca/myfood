<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
//header('Content-Type: application/json'); 


if (!function_exists('str_starts_with')) {
  function str_starts_with($str, $start) {
    return (@substr_compare($str, $start, 0, strlen($start))==0);
  }
}


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


// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$p = strtolower(trim($_GET['p']));
$p = str_replace("fideo", "fideos", $p);
$p = str_replace("edulcorante", "azucar blanco", $p);
$p = str_replace(" mesa ", " ", $p);
$arr = explode(" ",$p);
$select = "(";
$subselect = "";
$i=0;

if(count($arr) == 1){
    $select .= "p".$i;
    $subselect .= "case when instr(lower(name), lower('$p'))>0 then 1 else 0 end as p$i";
}else{
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

$sql ="SELECT lower(name), $select as p3, id_food FROM";
$sql .= " (SELECT id_food, name, $subselect FROM `Food`) as t1 having p3 > 0";
$con -> query('SET NAMES utf8');

$arr_a = array();
$arr_b = array();
$arr_c = array();

$select = array();

$data= new stdClass();
if ($result = mysqli_query($con, $sql)) {
  // Get field information for all fields
  while ($row = mysqli_fetch_row($result)) {
    array_push($arr_a,$row[0]);
    array_push($arr_b,$row[1]);
    array_push($arr_c,$row[2]);
    $data->response = "OK";
  }
  $rowcount=mysqli_num_rows($result);
  if($rowcount == 0){
    $data->response='KO';
  }
  mysqli_free_result($result);
}

$max=0;
$return=0;
$cont=0;

if(count($arr)==1){
    foreach($a as $key=>$value){
        $explode = explode(' ', $value);
        if(count($explode)==1) $return = $key;
    }
}else{    
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

$idFood = "0";
if($i>0){
    foreach($select as $value){
        if(str_starts_with($arr_a[$value], $arr[0]." ")){
            $return=$value;
        }
    }
    if($return != 0) $idFood = $arr_c[$return];
}else{
    $idFood = $arr_c[$return];
}

if(trim($n) != "" && trim($ed) !=""){

    $sql = "INSERT INTO `Pantry`(`code_bar`, `name`, `quantity`, `id_quantity_unit`, `id_place`, `weight`, `price`, `expired_date`, `preference_date`, `is_notified_expired`, `brand`, `idUser`, `id_food`) ";
    $sql .=" values('$cb','$n',$q,'$qu','$p',$w,$pr,'$ed','$pd',false,'$b',$u, $idFood)";
    

    $result = mysqli_query($con, $sql);
    $affected = mysqli_affected_rows($con);
    if($affected < 1){
        $data-> response = "KO";
    }else{
        $data-> response = "OK";
        $row_inserted_id = mysqli_insert_id($con);
        $sql = "INSERT INTO ImageProduct(id_product, src) values ($row_inserted_id,'$im')";
        
        echo $sql;
        $result = mysqli_query($con, $sql);
        
        $affected = mysqli_affected_rows($con);
        if($affected < 1){
            $data-> response = "KO2";
            $data-> value = 0;
        }else{
            $data-> response = "OK";
            $data-> value = $row_inserted_id;
            
        }
    }
    
    mysqli_close($con);
}else{
    $data -> response = "KO";
}
echo json_encode($data);
?>