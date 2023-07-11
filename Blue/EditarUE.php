<?php
include 'Conexion.php';
$id= (int)$_POST['id'];
$nombre=$_POST['nombre'];
$apaterno=$_POST['apaterno'];
$amaterno=$_POST['amaterno'];
$cedula=$_POST['cedula'];
$profesion=$_POST['profesion'];
$correo=$_POST['correo'];
$usuario=$_POST['usuario'];
$telefono=$_POST['telefono'];

$u_ex_paciente = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Usuario= '".$usuario."'");
$row_u_ex_paciente = mysqli_fetch_array($u_ex_paciente);

$u_ex_esp = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE Usuario= '".$usuario."'");
$row_u_ex_esp = mysqli_fetch_array($u_ex_esp);

$c_ex_paciente = mysqli_query($conexion, "SELECT * FROM u_paciente WHERE Correo= '".$correo."'");
$row_c_ex_paciente = mysqli_fetch_array($c_ex_paciente);

$c_ex_esp = mysqli_query($conexion, "SELECT * FROM u_especialista WHERE Usuario= '".$correo."'");
$row_c_ex_esp = mysqli_fetch_array($c_ex_esp);

    if(strlen($nombre)<3 || strlen($apaterno)<4
    || strlen($profesion)<5 || strlen($telefono)<8
    || strlen($cedula)<7)
    {
        if(!filter_Var($correo, FILTER_VALIDATE_EMAIL))
        {
            
                if($row_u_ex_paciente|| ($row_u_ex_esp &&$row_u_ex_esp['ID_especialista']!=$id))
                {
                    echo "Datos incorrectos: Usuario existente";
                }
                else
                {
                    echo "Datos incorrectos: Correo";
                }
            
        }
        else
        {
            if(($row_c_ex_esp&&$row_c_ex_esp['ID_especialista']!=$id)||$row_c_ex_paciente)
            {
                
                    if($row_u_ex_paciente|| ($row_u_ex_esp &&$row_u_ex_esp['ID_especialista']!=$id))
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Correo existente";
                    }
                
            }
            else
            {
                
                    if($row_u_ex_paciente|| ($row_u_ex_esp &&$row_u_ex_esp['ID_especialista']!=$id))
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos";
                    }
                
            }
        }
    }
    else
    {
        if(!filter_Var($correo, FILTER_VALIDATE_EMAIL))
        {
            
                if($row_u_ex_paciente|| ($row_u_ex_esp &&$row_u_ex_esp['ID_especialista']!=$id))
                {
                    echo "Datos incorrectos: Usuario existente";
                }
                else
                {
                    echo "Datos incorrectos: Correo";
                }
        }
        else
        {
            if(($row_c_ex_esp&&$row_c_ex_esp['ID_especialista']!=$id)||$row_c_ex_paciente)
            {
                
                    if($row_u_ex_paciente|| ($row_u_ex_esp &&$row_u_ex_esp['ID_especialista']!=$id))
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Correo existente";
                    }
                
            }
            else
            {
                
                    if($row_u_ex_paciente|| ($row_u_ex_esp &&$row_u_ex_esp['ID_especialista']!=$id))
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        $registro="UPDATE u_especialista SET Nombre='".$nombre."', APaterno='".$apaterno."',
                        AMaterno='".$amaterno."', Profesion='".$profesion."',
                        Usuario='".$usuario."',Correo='".$correo."',Telefono=".$telefono.",
                        Cedula='".$cedula."' WHERE ID_especialista=".$id."";
                        echo "Se ha modificado";
                        mysqli_query($conexion,$registro) or die (mysqli_error());
                        mysqli_close($conexion);
                    }
            }
        }
    }

?>