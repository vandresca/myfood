<?php

function detectSingularPlural($word) {
 $word = trim($word);
 $lastCharacter = strtolower($word[strlen($word)-1]);
  if ($lastCharacter == 's') {
    return 'plural';
  }
  else {
    return 'singular';
  }
}

function toSingular($sentence) {
  $words = explode(' ', $sentence);
  $singularsWords = '';
  foreach ($words as $word) {
    if (detectSingularPlural($word) == 'plural') {
      // Si la palabra está en plural, la convertimos en singular
      $lastCharacter = strtolower($word[strlen($word)-1]);
      if ($lastCharacter == 's') {
        $word = substr($word, 0, -1);
      }
    }
    $singularsWords .= $word.' ';
  }
  //Quitamos el espacio extra al final de la
  return rtrim($singularsWords);
}

function toPlural($sentence){
  $words = explode(' ', $sentence);
  $singularsWords = '';
  foreach ($words as $word) {
    if (detectSingularPlural($word) == 'singular') {
      // Si la palabra está en singular, la convertimos a plural
      $word = $word.'s';
    }
    $singularsWords .= $word.' ';
  }
  //Quitamos el espacio extra al final de la
  return rtrim($singularsWords);
}
?>