<?php
include 'Conexion.php';

$id=$_GET['ID_especialista'];

$f_consulta=mysqli_query($conexion,"SELECT * FROM u_especialista WHERE ID_especialista=".$id."");

$dato=null;
$i=0;
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

$f_consulta -> close();

?>