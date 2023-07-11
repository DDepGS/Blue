<?php
include 'Conexion.php';
$correo=$_POST['correo'];
$password=$_POST['contrasena'];

//$usuario="usuario";
//$password="contrasena";

$consulta=$conexion->prepare("SELECT * FROM u_paciente WHERE BINARY Correo=? AND BINARY Contrasena=?");
$consulta->bind_param('ss',$correo,$password);
$consulta->execute();

$resultado = $consulta->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);   
}
$consulta->close();
$conexion->close();
?>