<?php
include 'Conexion.php';
$correo=$_GET['correo'];

$consulta = mysqli_query($conexion,"SELECT * FROM u_paciente WHERE BINARY Correo='".$correo."'");
/*$row = mysqli_fetch_array($consulta);

echo json_encode($row);

$row->close();*/

/*$consulta = "SELECT * FROM PRODUCTO WHERE Usuario='".$usuario."'";
$resultado = $conexion -> query($consulta);*/
$dato=null;

while($fila = mysqli_fetch_array($consulta))
  {
      $dato[] = $fila;
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