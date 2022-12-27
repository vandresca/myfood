<?php


include('../email.php');

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 



$emailTo = "";
if(isset($_GET['e'])) $emailTo = $_GET['e'];

$u = "-1";
$url = 'http://vandresc.site/forgottenpassword/resetPassword.php';

$reset_link="Reset Password";
$body="";
$nameTo = "";

$servername = "localhost:3306";
$username = "vandresc_myfood";
$password = "7*Ui[=pKU{b{";
$database = "vandresc_myfood";

// Create connection
$con = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

$sql = "SELECT idUser, name FROM User WHERE email='$emailTo'";
$con -> query('SET NAMES utf8');


$data = new stdClass();
if ($result = mysqli_query($con, $sql)) {
    while ($row = mysqli_fetch_row($result)){
        $u = $row[0];
        $nameTo= $row[1];
    }
    if($_GET['l']=="1"){
        $reset_link="Restablecer Contraseña";
        $body = "Accede al siguiente enlace para restablecer tu contraseña: <br><br>Clica <a href='$url?u=$u&l=1'>aquí</a> para restablecer la contraseña<br><br>";
    }  
    if($_GET['l']=="2"){
        $reset_link="Reset Password";
        $body = "Access the following link to reset your password: <br><br>Click <a href='$url&l=2'>here</a> to reset your password<br><br>";
    } 
    if($_GET['l']=="3"){
        $reset_link="Réinitialiser le mot de passe";
        $body = "Accédez au lien suivant pour réinitialiser votre mot de passe: <br><br> Cliquez <a href='$url?u=$u&l=3'>ici</a> pour réinitialiser votre mot de passe<br><br>";
    }
    if($_GET['l']=="4"){
        $reset_link="Passwort zurücksetzen";
        $body = "Greifen Sie auf den folgenden Link zu, um Ihr Passwort zurückzusetzen:<br><br> Klicken Sie <a href='$url?u=$u&l=4'>hier</a>, um Ihr Passwort zurückzusetzen<br><br>";
    }
    if($_GET['l']=="5"){
        $reset_link="Restablir Contrasenya";
        $body =  "Accedeix al següent enllaç per restablir la teva contrasenya:<br><br> Clica <a href='$url?u=$u&l=5'>aquí</a> per restablir la contrasenya<br><br>";
    } 
    $rowcount=mysqli_num_rows($result);
    if($rowcount == -1 || $rowcount == 0){
        $data->response = 'KO';
    }else{
        $data->response = 'OK';
        sendEmail($reset_link, $body, $emailTo, $nameTo);
    }
}


mysqli_close($con);

echo json_encode($data);

?>