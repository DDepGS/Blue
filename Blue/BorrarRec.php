<?php
include 'Conexion.php';
$id=$_POST['id'];

$registro ="DELETE FROM recordatorios WHERE ID_recordatorio=".$id."";
mysqli_query($conexion,$registro) or die (mysqli_error());


mysqli_close($conexion)

?>