<?php
include 'Conexion.php';
$correo=$_GET['correo'];
//$correo='correo';

$consulta = mysqli_query($conexion,"SELECT ID_paciente FROM u_paciente WHERE BINARY Correo='".$correo."'");
$result = mysqli_fetch_array($consulta);

$re_consulta=mysqli_query($conexion,"SELECT * FROM re_paciente WHERE ID_paciente=".$result['ID_paciente']."");

$dato=null;
$i=0;
while($re_result = mysqli_fetch_array($re_consulta))
  {
      $id_especialista[] = $re_result['ID_especialista'];
      $i++;
  }
  
  for($j=0; $j<count($id_especialista);$j++)
  {
    //echo $id_especialista[$j];
    $p_consulta = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE ID_especialista=".$id_especialista[$j]."");
    $p_result = mysqli_fetch_array($p_consulta);
    //echo $p_result['ID_paciente'];
    $dato[] = $p_result;
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