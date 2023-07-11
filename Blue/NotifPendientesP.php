<?php
include 'Conexion.php';
$correo=$_GET['correo'];

$consulta = mysqli_query($conexion,"SELECT * FROM u_paciente WHERE BINARY Correo='".$correo."'");
$result = mysqli_fetch_array($consulta);

$re_consulta=mysqli_query($conexion,"SELECT * FROM notificaciones WHERE ID_paciente=".$result['ID_paciente']." AND Estado='Pendiente'");

$dato=null;
$i=0;
while($re_result = mysqli_fetch_array($re_consulta))
  {
    $dato[] = $re_result;
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