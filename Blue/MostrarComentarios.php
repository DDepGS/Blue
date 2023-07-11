<?php
include 'Conexion.php';

$id_publi = $_GET['id_publicacion'];

$f_consulta=mysqli_query($conexion,"SELECT * FROM comentarios WHERE ID_publicacion=".$id_publi." ORDER BY ID_comentario DESC");

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