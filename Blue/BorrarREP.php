<?php
include 'Conexion.php';
$correo=$_POST['correo'];
$usuariop=$_POST['usuariop'];

$consultap = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE BINARY Usuario='".$usuariop."'");
$rowp = mysqli_fetch_array($consultap);
$consultae = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE BINARY Correo='".$correo."'");
$rowe = mysqli_fetch_array($consultae);

$consultare = mysqli_query($conexion, "SELECT * FROM re_paciente WHERE ID_especialista=".$rowe['ID_especialista']." AND ID_paciente=".$rowp['ID_paciente']."");


if(!$rowre = mysqli_fetch_array($consultare))
{
  echo "El paciente ya se había eliminado";
}
else
{
    $registro ="DELETE FROM re_paciente WHERE ID_especialista=".$rowe['ID_especialista']." AND ID_paciente=".$rowp['ID_paciente']."";
    //echo $registro;
    echo "Se ha eliminado el paciente";
    mysqli_query($conexion,$registro) or die (mysqli_error());
}


mysqli_close($conexion)

?>