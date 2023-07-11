<?php
include 'Conexion.php';
$id=$_POST['id'];
$Cnueva=$_POST['Cnueva'];
$Cvieja=$_POST['Cvieja'];
$tipo=$_POST['tipo'];

if($tipo=="1")
{
    $usuario = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Contrasena= '".$Cvieja."'");
    
    if($rowusuario = mysqli_fetch_array($usuario))
    {
        if(strlen($Cnueva)>8)
        {
            $registro="UPDATE u_paciente SET Contrasena='".$Cnueva."' WHERE ID_paciente='".$id."'";
            echo "Se ha modificado";
            mysqli_query($conexion,$registro) or die (mysqli_error());
            mysqli_close($conexion);
        }
        else
        {
            echo "Nueva contraseña incorrecta";
        }
    }
    else
    {
        if(strlen($Cnueva)<8)
        {
            echo "La contraseña actual no coincide";
        }
        else
        {
            echo "Nueva contraseña incorrecta";
        }
    }
}
elseif($tipo=="2")
{
    $usuario = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE Contrasena= '".$Cvieja."'");
    
    if($rowusuario = mysqli_fetch_array($usuario))
    {
        if(strlen($Cnueva)>8)
        {
            $registro="UPDATE u_especialista SET Contrasena='".$Cnueva."' WHERE ID_especialista='".$id."'";
            echo "Se ha modificado";
            mysqli_query($conexion,$registro) or die (mysqli_error());
            mysqli_close($conexion);
        }
        else
        {
            echo "Nueva contraseña incorrecta";
        }
    }
    else
    {
        if(strlen($Cnueva)<8)
        {
            echo "La contraseña actual no coincide";
        }
        else
        {
            echo "Nueva contraseña incorrecta";
        }
    }
}

?>