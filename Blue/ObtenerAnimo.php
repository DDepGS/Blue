<?php
include 'Conexion.php';
$id=$_GET['id'];

date_default_timezone_set('America/Mexico_City');

//echo date('d');

if(date('d')==01)
{
    $consulta=mysqli_query($conexion,"SELECT * FROM estado_animo where month(Fecha)= month(CURRENT_DATE()- INTERVAL 1 month) AND ID_paciente='".$id."'; ");
}
else
{
    $consulta=mysqli_query($conexion,"SELECT * FROM estado_animo where month(Fecha)= month(CURRENT_DATE()) AND ID_paciente='".$id."'; ");
}


//echo $result['ID_paciente'];

$dato=null;
$i=0;
while($re_result = mysqli_fetch_array($consulta))
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