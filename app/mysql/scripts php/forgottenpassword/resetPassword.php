<?php

//Cargamos las credenciales de la base de datos MySQL
include('../credencialesBaseDatos.php');


// Creamos la conexión con la base de datos
$con = mysqli_connect($servername, $username, $password, $database);

// Chequeamos la conexión
if (mysqli_connect_errno()){
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  exit();
}

//Si no venimos de una petición POST obtenemos el id de usuario y el lenguaje y
//printamos el formulario
if ($_SERVER["REQUEST_METHOD"] != "POST") {
    $user =$_GET['u'];
    $language = $_GET['l'];
    printForm($user, $language);
//En caso de venir de un POST, (hemos rellenado los datos del formualario)
}else{
    //Comprobamos que la contraseña introducida y su comprobación son iguales si no lo son
    //mostramos un aviso al usuario en su idioma
    if($_POST['pass'] != $_POST['passConf']){
        $language = $_POST['language'];
        if($language==1){
            $passNotMatch = "Las contraseñas no coinciden";
        }
        if($language==2){
            $passNotMatch = "Passwords don't match";
        }
        if($language==3){
            $passNotMatch = "Les mots de passe ne correspondent pas";
        }
        if($language==4){
            $passNotMatch = "Kennwörter stimmen nicht überein";
        }
        if($language==5){
            $passNotMatch = "Les contrasenyes no coincideixen";
        }
        echo "<div style='margin-left:50px; margin-top:50px;color:red;'>$passNotMatch</div>";
        printForm($_POST['user'], $_POST['language']);

   //En caso de coincidir cambiamos la contraseña y avisamos al usuario.
    }else{
        $language = $_POST['language'];
        if($language==1){
            $passwordreseted = "La contraseña se ha restablecido con éxito";
        }
        if($language==2){
            $passwordreseted = "The password has been successfully reset";
        }
        if($language==3){
            $passwordreseted = "Le mot de passe a été réinitialisé avec succès";
        }
        if($language==4){
            $passwordreseted = "Das Kennwort wurde erfolgreich zurückgesetzt";
        }
        if($language==5){
            $passwordreseted = "La contrasenya s'ha restablert amb èxit";
        }
        
        $sql = "UPDATE `User` SET `password`='".$_POST['pass']."' WHERE idUser = ".$_POST['user'];

        $result = mysqli_query($con, $sql);

        mysqli_close($con);    
        echo "<div style='margin-left:50px; margin-top:50px;'>$passwordreseted</div>";
    }
}

//Función que printa el formularo para cambiar la contraseña
function printForm($user, $language){
    $self = $_SERVER['PHP_SELF'];
    if($language==1){
        $title = "Restablecer contraseña";
        $insertPass = "Nueva contraseña";
        $confirmPass = "Confirmar contraseña";
        $passNotMatch = "Las contraseñas no coinciden";
        $passwordreseted = "La contraseña se ha restablecido con éxito";
    }
    if($language==2){
        $title = "Reset password";
        $insertPass = "New password";
        $confirmPass = "Confirm password";
        $passNotMatch = "Passwords don't match";
        $passwordreseted = "The password has been successfully reset";
    }
    if($language==3){
        $title = "Réinitialiser le mot de passe";
        $insertPass = "Nouveau mot de passe";
        $confirmPass = "Confirmer le mot de passe";
        $passNotMatch = "Les mots de passe ne correspondent pas";
        $passwordreseted = "Le mot de passe a été réinitialisé avec succès";
    }
    if($language==4){
        $title = "Passwort zurücksetzen";
        $insertPass = "Neues Passwort";
        $confirmPass = "Passwort bestätigen";
        $passNotMatch = "Kennwörter stimmen nicht überein";
        $passwordreseted = "Das Kennwort wurde erfolgreich zurückgesetzt";
    }
    if($language==5){
        $title = "Restablir contrasenya";
        $insertPass = "Nova contrasenya";
        $confirmPass = "Confirmar contrasenya";
        $passNotMatch = "Les contrasenyes no coincideixen";
        $passwordreseted = "La contrasenya s'ha restablert amb èxit";
    }
    echo "<form method='post' action='$self'>";
    echo "<input type='hidden' name='user' value='$user'/>";
    echo "<input type='hidden' name='language' value='$language'/>";
    echo "<input type='hidden' name='passNotMatch' value='$passNotMatch'/>";
    echo "<input type='hidden' name='passwordreseted' value='$passwordreseted'/>";
    echo "<div style='margin:50px'>";
    echo "<p style='font-size: 2em;'>$title</p>";
    echo "<p>";
    echo "<div style='display:inline-block; margin-right:20px'>$insertPass<br><input type='password' name='pass' value=''/></div>";
    echo "<div style='display:inline-block;'>$confirmPass<br><input type='password' name='passConf' value=''/></div>";
    echo "<br><br>";
    echo "<input type='submit' value='Restablecer'/>";
    echo "</p>";
    echo "</div>";
    echo "</form>";
}

?>