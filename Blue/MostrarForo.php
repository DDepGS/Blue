<?php
include 'Conexion.php';

$f_consulta=mysqli_query($conexion,"SELECT * FROM foro ORDER BY ID_publicacion DESC");

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