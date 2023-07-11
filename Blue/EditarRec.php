<?php
include 'Conexion.php';
$id=$_POST['id'];
$nombre=$_POST['nombre'];
$descripcion=$_POST['descripcion'];
$hora =$_POST['hora'];
$minuto =$_POST['minuto'];
$repeticion =$_POST['repeticion'];


$registro ="UPDATE recordatorios SET NombreRec='".$nombre."',Descripcion='".$descripcion."',
TiempoEj='".$hora.":".$minuto."',Repeticion='".$repeticion."' WHERE ID_recordatorio=".$id."";
mysqli_query($conexion,$registro) or die (mysqli_error());

mysqli_close($conexion)

?>