<?php
include 'Conexion.php';
$destino=$_POST['id'];
$nombre=$_POST['Nombre'];
$desc=$_POST['Descripcion'];

$checkesp = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE Correo= '".$destino."'");
$checkpac = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Correo= '".$destino."'");
$especialista = mysqli_fetch_array($checkesp);
$paciente = mysqli_fetch_array($checkpac);

if(empty($paciente['ID_paciente']))
{
    $registro="INSERT INTO notificaciones(Nombre, Descripcion, Estado, ID_especialista, Tipo) 
    VALUES ('".$nombre."', '".$desc."','Pendiente','".$especialista['ID_especialista']."','Chat');";

    mysqli_query($conexion,$registro) or die (mysqli_error());

}
elseif(empty($especialista['ID_especialista']))
{
    //echo $rowpubli['Titulo'];
    $registro="INSERT INTO notificaciones(Nombre, Descripcion, Estado, ID_paciente, Tipo) 
    VALUES ('".$nombre."', '".$desc."','Pendiente','".$paciente['ID_paciente']."','Chat');";

    mysqli_query($conexion,$registro) or die (mysqli_error());
}



mysqli_close($conexion);

?>