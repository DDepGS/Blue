<?php 
$hostname='localhost';
$database='blue';
$username='root';
$password='';

$conexion= new mysqli($hostname,$username,$password,$database);

if($conexion->connect_errno)
{
    $conexion=NULL;
}
?>