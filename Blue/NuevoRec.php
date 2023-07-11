<?php
include 'Conexion.php';
$correo=$_POST['correo'];
$nombre=$_POST['nombre'];
$descripcion=$_POST['descripcion'];
$hora =$_POST['hora'];
$minuto =$_POST['minuto'];
$repeticion =$_POST['repeticion'];
$nulo=null;

//$tiempo=$hora':'$minuto;


    $usuario = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Correo= '".$correo."'");
    $rowusuario = mysqli_fetch_array($usuario);

    $registro="INSERT INTO recordatorios(NombreRec, Descripcion, ID_paciente, TiempoEj, Repeticion) VALUES
    ('".$nombre."','".$descripcion."','".$rowusuario['ID_paciente']."','".$hora.":".$minuto."','".$repeticion."');";

    echo"Se ha guardado";
    mysqli_query($conexion,$registro) or die (mysqli_error());




mysqli_close($conexion);

?>