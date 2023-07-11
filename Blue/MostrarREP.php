<?php
include 'Conexion.php';
$correo=$_GET['correo'];

$consulta = mysqli_query($conexion,"SELECT * FROM u_especialista WHERE BINARY Correo='".$correo."'");
$result = mysqli_fetch_array($consulta);

$re_consulta=mysqli_query($conexion,"SELECT * FROM re_paciente WHERE ID_especialista=".$result['ID_especialista']."");

$dato=null;
$i=0;
while($re_result = mysqli_fetch_array($re_consulta))
  {
      $id_paciente[] = $re_result['ID_paciente'];
      $i++;
  }
  
  for($j=0; $j<count($id_paciente);$j++)
  {
    //echo $id_paciente[$j];
    $p_consulta = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE ID_paciente=".$id_paciente[$j]."");
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