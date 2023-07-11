<?php
include 'Conexion.php';
$id=$_POST['id_recordatorio'];

$rec = mysqli_query($conexion, "SELECT * FROM recordatorios WHERE ID_recordatorio= '".$id."'");
$rowrec = mysqli_fetch_array($rec);

$registro="INSERT INTO notificaciones(Nombre, Descripcion, Estado, ID_paciente, Tipo) 
VALUES ('".$rowrec['NombreRec']."', '".$rowrec['Descripcion']."','Pendiente','".$rowrec['ID_paciente']."','Recordatorio');";

mysqli_query($conexion,$registro) or die (mysqli_error());

mysqli_close($conexion);

?>