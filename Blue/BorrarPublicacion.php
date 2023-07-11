<?php
include 'Conexion.php';
$id=$_POST['id'];

$registro ="DELETE FROM foro WHERE ID_publicacion=".$id."";
mysqli_query($conexion,$registro) or die (mysqli_error());


mysqli_close($conexion)

?>