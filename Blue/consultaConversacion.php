<?php
include 'Conexion.php';
$correo=$_GET['correo'];
$consulta = mysqli_query($conexion,"SELECT * FROM conversations WHERE (mailSender = '".$correo."') OR (mailReceiver = '".$correo."')");

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