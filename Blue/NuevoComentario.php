<?php
include 'Conexion.php';
$correo=$_POST['correo'];
$id_publi=$_POST['id_publicacion'];
$contenido=$_POST['contenido'];
$tipo=$_POST['tipo'];
$nulo=null;

date_default_timezone_set('America/Mexico_City');

$fecha= date('Y-m-d');

if($tipo=="1")
{
    $usuario = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Correo= '".$correo."'");
    $rowusuario = mysqli_fetch_array($usuario);

    $registro="INSERT INTO comentarios(Contenido, ID_paciente, Fecha, ID_publicacion) 
    VALUES ('".$contenido."','".$rowusuario['ID_paciente']."','".$fecha."','".$id_publi."');";
    mysqli_query($conexion, $registro) or die (mysqli_error());
    echo "Se ha publicado";

}
elseif($tipo=="2")
{
    $usuario = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE Correo= '".$correo."'");
    $rowusuario = mysqli_fetch_array($usuario);

    $registro="INSERT INTO comentarios(Contenido, ID_especialista, Fecha, ID_publicacion) 
    VALUES ('".$contenido."','".$rowusuario['ID_especialista']."','".$fecha."','".$id_publi."');";

    echo"Se ha publicado";
    mysqli_query($conexion,$registro) or die (mysqli_error());
}


mysqli_close($conexion);

?>