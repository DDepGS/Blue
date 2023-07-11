<?php
include 'Conexion.php';
$correo=$_POST['correo'];
$Titulo=$_POST['titulo'];
$contenido=$_POST['contenido'];
$tipo=$_POST['tipo'];
$anonimo = $_POST['anonimo'];
$nulo=null;

date_default_timezone_set('America/Mexico_City');

$fecha= date('Y-m-d');

if(preg_match('/Maricon|maricon|Puto|puto|joto|Joto|Pendejo|pendejo|Maricón|maricón|imbecil|Imbecil/', $contenido)) {

    echo "Publicación ofensiva";
}
elseif(preg_match('/Maricon|maricon|Puto|puto|joto|Joto|Pendejo|pendejo|Maricón|maricón|imbecil|Imbecil/', $Titulo))
{
    echo "Publicación ofensiva";
}
else {
    if($tipo=="1")
{
    $usuario = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Correo= '".$correo."'");
    $rowusuario = mysqli_fetch_array($usuario);
    //echo $rowusuario['ID_paciente'];

    $registro="INSERT INTO foro(Titulo, Contenido, ID_paciente, Fecha, anonimo) VALUES ('".$Titulo."','".$contenido."','".$rowusuario['ID_paciente']."','".$fecha."','".$anonimo."');";
    mysqli_query($conexion, $registro) or die (mysqli_error());
    echo "Se ha publicado";

}
elseif($tipo=="2")
{
    $usuario = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE Correo= '".$correo."'");
    $rowusuario = mysqli_fetch_array($usuario);

    $registro="INSERT INTO foro(Titulo, Contenido, ID_especialista, Fecha, anonimo) VALUES
    ('".$Titulo."','".$contenido."','".$rowusuario['ID_especialista']."','".$fecha."', '".$anonimo."');";

    echo"Se ha publicado";
    mysqli_query($conexion,$registro) or die (mysqli_error());
}
}




mysqli_close($conexion);

?>