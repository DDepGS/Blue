<?php
include 'Conexion.php';
$mailsender=$_POST['mailSender'];
$mailreceiver=$_POST['mailReceiver'];
$mensaje= base64_encode($_POST['message']);
date_default_timezone_set('America/Mexico_City');
$fecha = date('Y-m-d H:m:s');


$registro="INSERT INTO mensaje(mailSender, mailReceiver, mensaje, fecha) VALUES
('".$mailsender."','".$mailreceiver."', '".$mensaje."','".$fecha."');"; 

echo "Mensaje enviado";
mysqli_query($conexion,$registro) or die (mysqli_error());
mysqli_close($conexion);

?>