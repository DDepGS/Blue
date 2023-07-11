<?php
include 'Conexion.php';
$correo=$_POST['correo'];
$animo=(int)$_POST['animo'];

date_default_timezone_set('America/Mexico_City');

$fecha= date('Y-m-d');

$consulta = mysqli_query($conexion,"SELECT * FROM u_paciente WHERE BINARY Correo='".$correo."'");
$row = mysqli_fetch_array($consulta);
//echo $row['ID_paciente'];

$consultae = mysqli_query($conexion,"SELECT * FROM estado_animo WHERE DATE(Fecha) = DATE(NOW())AND ID_paciente=".$row['ID_paciente']." ");

//echo $rowe['Fecha'];
//echo $fecha;


if($rowe = mysqli_fetch_array($consultae))
{
    echo "Ya has registrado tu ánimo hoy";
}
else
{
    $registro ="INSERT INTO estado_animo (Animo, Fecha, ID_paciente) VALUES ('".$animo."','".$fecha."','".$row['ID_paciente']."');";
    //echo $registro;
    echo "Ánimo registrado";
    echo $rowe;
    mysqli_query($conexion,$registro) or die (mysqli_error());
}

mysqli_close($conexion)
//echo $row['ID_paciente'];

?>