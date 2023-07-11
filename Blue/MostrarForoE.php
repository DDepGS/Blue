<?php
include 'Conexion.php';
$correo=$_GET['correo'];

$consulta = mysqli_query($conexion,"SELECT * FROM u_especialista WHERE BINARY Correo='".$correo."'");
$result=mysqli_fetch_array($consulta);

$f_consulta=mysqli_query($conexion,"SELECT * FROM foro WHERE ID_especialista='".$result['ID_especialista']."' ORDER BY ID_publicacion DESC");


while($f_result = mysqli_fetch_array($f_consulta))
  {
      $dato[]=$f_result;
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