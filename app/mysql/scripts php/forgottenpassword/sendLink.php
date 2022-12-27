<?php

//Incluimos el script email para enviar correos electronicos
include('../email.php');

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');

//Indicamos que el contenido que vamos a devolver es un json
header('Content-Type: application/json'); 

//Inicializamos una variable para almacenar el correo a enviar el link
$emailTo = "";
if(isset($_GET['e'])) $emailTo = $_GET['e'];

//Inicializamos el id de usuario y la url a enviar en el correo para modificar la contraseña
$u = "-1";
$url = 'http://vandresc.site/forgottenpassword/resetPassword.php';

$reset_link="Reset Password";
$body="";
$nameTo = "";

// Creamos la conexión a la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión y si falla salimos
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Creamos la sql para verificar si el correo existe en el sistema
$sql = "SELECT idUser, name FROM User WHERE email='$emailTo'";
$con -> query('SET NAMES utf8');

//Creamos un objeto data para almacenar el resultado
$data = new stdClass();

//Ejecutamos la sql
if ($result = mysqli_query($con, $sql)) {

    //Obtenemos las filas del resultado
    while ($row = mysqli_fetch_row($result)){
        //Por cada fila obtenemos el id y el nombre de usuario
        $u = $row[0];
        $nameTo= $row[1];
    }
    //Dependiendo del idioma enviamos un texto u otro
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

    //Verificamos que la sql que hemos ejecutado antes ha devuelto resultados
    $rowcount=mysqli_num_rows($result);
    //Si no tiene un número de resultado cero o negativo devolvemos respuesta incorrecta
    if($rowcount == -1 || $rowcount == 0){
        $data->response = 'KO';

    //En caso contrario devolvemos una respuesta correcta y enviamos el correo con el enlace
    //para cambiar la contraseña
    }else{
        $data->response = 'OK';
        sendEmail($reset_link, $body, $emailTo, $nameTo);
    }
}


mysqli_close($con);

echo json_encode($data);

?>