<?php
    
    //Metodo que dado un texto, un lenguaje de origen y otro de destino traduce el texto al lenguaje destino.
    //$q -> texto a traducir
    //$sl -> lenguaje de origen de traducción
    //$tl -> lenguaje al cuál queremos traducir
    function translate($q, $sl, $tl){
        $res= file_get_contents("https://translate.googleapis.com/translate_a/single?client=gtx&ie=UTF-8&oe=UTF-8&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&sl=".$sl."&tl=".$tl."&hl=hl&q=".urlencode($q), $_SERVER['DOCUMENT_ROOT']."/transes.html");
        $res=json_decode($res);
        return $res[0][0][0];
    }
?>