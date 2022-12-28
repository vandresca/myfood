<?php
require_once __DIR__.'/PHPMailer/PHPMailer.php';

include('credencialesEmail.php');

function sendEmail($subject, $body, $emailTo, $nameTo){
    $mail = new \PHPMailer\PHPMailer\PHPMailer(true);
    $isOK='KO';
        try {
            //Server settings
            $mail->SMTPDebug = CONTACTFORM_PHPMAILER_DEBUG_LEVEL;
            $mail->CharSet = 'UTF-8';
            $mail->Encoding = 'base64';
            $mail->isSMTP();
            $mail->Host = CONTACTFORM_SMTP_HOSTNAME;
            $mail->SMTPAuth = true;
            $mail->Username = CONTACTFORM_SMTP_USERNAME;
            $mail->Password = CONTACTFORM_SMTP_PASSWORD;
            $mail->SMTPSecure = CONTACTFORM_SMTP_ENCRYPTION;
            $mail->Port = CONTACTFORM_SMTP_PORT;
    
            // Recipients
            $mail->setFrom(CONTACTFORM_FROM_ADDRESS, CONTACTFORM_FROM_NAME);
            $mail->addAddress($emailTo, $nameTo);
    
            // Content
            $mail->Subject = $subject;
            $mail->Body    = $body;
            $mail->IsHTML(true);
            $mail->send();
            $isOK='OK';
        } catch (Exception $e) {
            $isOK='KO';
            //echo $mail->ErrorInfo;
        }
        return $isOK;
}
?>