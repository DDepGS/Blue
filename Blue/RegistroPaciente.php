<?php
include 'Conexion.php';
$nombre=$_POST['nombre'];
$apaterno=$_POST['apaterno'];
$amaterno=$_POST['amaterno'];
$diagnostico=$_POST['diagnostico'];
$contrasena=$_POST['contrasena'];
$medicamento=$_POST['medicamento'];
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
            || strlen($diagnostico)<5 || strlen($telefono)<8)
    {
        if(!filter_Var($correo, FILTER_VALIDATE_EMAIL))
        {
            if(strlen($contrasena0)<8)
            {
                if($row_u_ex_esp || $row_u_ex_paciente)
                {
                    echo "Datos incorrectos: Usuario existente";
                }
                else
                {
                    echo "Datos incorrectos: Contraseña";
                }
            }
            else
            {
                if($row_u_ex_esp || $row_u_ex_paciente)
                {
                    echo "Datos incorrectos: Usuario existente";
                }
                else
                {
                    echo "Datos incorrectos: Correo";
                }
            }
        }
        else
        {
            if($row_c_ex_esp||$row_c_ex_paciente)
            {
                if(strlen($contrasena)<8)
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Contraseña";
                    }
                }
                else
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Correo existente";
                    }
                }
            }
            else
            {
                if(strlen($contrasena)<8)
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Contraseña";
                    }
                }
                else
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
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
    }
    else
    {
        if(!filter_Var($correo, FILTER_VALIDATE_EMAIL))
        {
            if(strlen($contrasena)<8)
            {
                if($row_u_ex_esp || $row_u_ex_paciente)
                {
                    echo "Datos incorrectos: Usuario existente";
                }
                else
                {
                    echo "Datos incorrectos: Contraseña";
                }
            }
            else
            {
                if($row_u_ex_esp || $row_u_ex_paciente)
                {
                    echo "Datos incorrectos: Usuario existente";
                }
                else
                {
                    echo "Datos incorrectos: Correo";
                }
            }
        }
        else
        {
            if($row_c_ex_esp||$row_c_ex_paciente)
            {
                if(strlen($contrasena)<8)
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Contraseña";
                    }
                }
                else
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Correo existente";
                    }
                }
            }
            else
            {
                if(strlen($contrasena)<8)
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        echo "Datos incorrectos: Contraseña";
                    }
                }
                else
                {
                    if($row_u_ex_esp || $row_u_ex_paciente)
                    {
                        echo "Datos incorrectos: Usuario existente";
                    }
                    else
                    {
                        $registro="INSERT INTO u_paciente(Nombre, APaterno, AMaterno, Diagnostico, Contrasena, Medicamento, Correo, Usuario, Telefono) VALUES
                        ('".$nombre."','".$apaterno."', '".$amaterno."','".$diagnostico."','".$contrasena."', '".$medicamento."','".$correo."', '".$usuario."','".$telefono."');"; 
                        //echo $registro;
                        echo "Se ha registrado";
                        mysqli_query($conexion,$registro) or die (mysqli_error());
                        mysqli_close($conexion);
                    }
                }
            }
        }
    }

?>