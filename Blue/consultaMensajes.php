<?php
include 'Conexion.php';
$correo=$_GET['correo'];
$correo2 =$_GET['correo2'];
$consulta = mysqli_query($conexion,"SELECT * FROM mensaje WHERE ((mailSender = '".$correo."') AND (mailReceiver = '".$correo2."')) OR ((mailSender = '".$correo2."') AND (mailReceiver = '".$correo."'))");

$dato = null;

while($result = mysqli_fetch_array($consulta)){
  $dato[]= $result;
}

  if($dato!=null)
  {
    echo json_encode($dato,JSON_UNESCAPED_UNICODE);
  }
  else
  {
    echo json_encode("error");
  }

$consulta -> close();

?>