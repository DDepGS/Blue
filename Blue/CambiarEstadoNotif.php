<?php
include 'Conexion.php';
$id=$_POST['id_notif'];


$registro ="UPDATE notificaciones SET Estado='Visto' WHERE ID_notificacion=".$id."";
mysqli_query($conexion,$registro) or die (mysqli_error());

mysqli_close($conexion)

?>