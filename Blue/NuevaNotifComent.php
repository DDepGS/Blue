<?php
include 'Conexion.php';
$id_publi=$_POST['id_publicacion'];

$publi = mysqli_query($conexion, "SELECT * FROM foro WHERE ID_publicacion= '".$id_publi."'");
$rowpubli = mysqli_fetch_array($publi);

if(empty($rowpubli['ID_paciente']))
{
    $registro="INSERT INTO notificaciones(Nombre, Descripcion, Estado, ID_especialista, Tipo) 
    VALUES ('Nuevo Comentario', 'Tienes un nuevo comentario en ".$rowpubli['Titulo']."','Pendiente','".$rowpubli['ID_especialista']."','Comentario');";

    mysqli_query($conexion,$registro) or die (mysqli_error());

}
elseif(empty($rowpubli['ID_especialista']))
{
    //echo $rowpubli['Titulo'];
    $registro="INSERT INTO notificaciones(Nombre, Descripcion, Estado, ID_paciente, Tipo) 
    VALUES ('Nuevo Comentario', 'Tienes un nuevo comentario en ".$rowpubli['Titulo']."','Pendiente','".$rowpubli['ID_paciente']."','Comentario');";

    mysqli_query($conexion,$registro) or die (mysqli_error());
}



mysqli_close($conexion);

?>