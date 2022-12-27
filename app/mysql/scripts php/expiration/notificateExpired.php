<?php


include('../email.php');


//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');




// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$users = array();
$sql = "SELECT idUser, name, surnames, email FROM User";
$result = mysqli_query($con, $sql);
while ($row = mysqli_fetch_row($result)) {
    $user = new stdClass();
    $user->id = $row[0];
    $user->name = $row[1];
    $user->surnames = $row[2];
    $user->email = $row[3];
    array_push($users, $user);
}

foreach($users as $user){
    $sql = "SELECT name, days, expiration FROM (SELECT idUser, name, DATEDIFF(str_to_date(expired_date, '%e/%c/%Y'), CURRENT_TIMESTAMP) as days, price, case ";
    $sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< UNIX_TIMESTAMP(CURRENT_TIMESTAMP) then 'expired' ";
    $sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> UNIX_TIMESTAMP(CURRENT_TIMESTAMP) AND UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))< (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY))then '0to10days' ";
    $sql .="when UNIX_TIMESTAMP(str_to_date(expired_date, '%e/%c/%Y'))> (UNIX_TIMESTAMP(CURRENT_TIMESTAMP + INTERVAL 10 DAY)) then 'more10days' ";
    $sql .="end as expiration FROM `Pantry`) as t1 WHERE t1.idUser = ".$user->id." and (t1.expiration='expired' or t1.expiration='0to10days')";
    $con -> query('SET NAMES utf8');
    $data= new stdClass();
    $products = array();
    if ($result = mysqli_query($con, $sql)) {
      // Get field information for all fields
      
      $body = "Hi ".$user->name." ".$user->surnames."<br><br>";
      $body .= "These are the products expired and almost expired you have: <br><br>";
      $products = array();
      while ($row = mysqli_fetch_row($result)) {
        $product = new stdClass();
        $product->name = $row[0];
        $product->days = $row[1];
        $product->expiration = $row[2];
        array_push($products, $product);
      }
      $body .= "<div style='margin-left:20px;'><b>Expired:</b></div><br>";
      foreach($products as $product){
          if($product->expiration == 'expired'){
              $body .= "<div style='margin-left:40px;color:red;'>".$product->name.":  ".$product->days." days</div><br>";
          }
      }
      $body .= "<br><br>";
      $body .= "<div style='margin-left:20px;'><b>Almost Expired:</b></div><br>";
      foreach($products as $product){
          if($product->expiration == '0to10days'){
              $body .= "<div style='margin-left:40px;'>".$product->name.":  ".$product->days." days</div><br>";
          }
      }
      $body .= "<br><br>";
      
      sendEmail("Expired and Almos Expired Products", $body, $user->email, $user->name);
      
    }
}
mysqli_close($con);
?>